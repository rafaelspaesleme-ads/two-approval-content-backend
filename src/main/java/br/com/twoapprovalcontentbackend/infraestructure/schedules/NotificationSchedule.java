package br.com.twoapprovalcontentbackend.infraestructure.schedules;

import br.com.twoapprovalcontentbackend.infraestructure.enums.StatusEvaluationEnum;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessInternalServerErrorException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessNotFoundException;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.impl.PersistGatewayImpl;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.clients.ServiceMailClient;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.clients.WebhookClient;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.requests.ServiceEmailClientRequest;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.requests.WebhookEventClientRequest;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.references.WebhookDataReference;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories.ContentRepository;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories.UserEvaluatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSchedule extends PersistGatewayImpl {

    private final ContentRepository repository;

    private final UserEvaluatorRepository userEvaluatorRepository;

    private final WebhookClient webhookClient;

    private final ServiceMailClient serviceMailClient;

    //@Scheduled(cron = "0 0 3 * * *") //--> todos os dias as 3h da manhã
    @Scheduled(cron = "0 */2 * * * *") //--> a cada 2 minutos
    public void runTask() {

        List<ContentDocument> contents = repository.findAllByFlagNotificationFalseOrFlagNotificationNull()
                .stream()
                .filter(content -> !StatusEvaluationEnum.IN_REVIEW.equals(content.getStatus()))
                .filter(content -> StringUtils.isEmpty(content.getJustificationFailSendNotifiction()))
                .toList();

        if (!CollectionUtils.isEmpty(contents)) {

            contents.forEach(content -> {
                StringBuilder errors = new StringBuilder();
                AtomicInteger count = new AtomicInteger(0);
                if (ObjectUtils.isNotEmpty(content.getWebhook())) {
                    this.sendWebhook(content, errors, count);
                }
                this.sendEmail(content, errors, count, errors.isEmpty());
            });

            this.repository.saveAll(super.update(contents));

        }
    }

    private void sendEmail(ContentDocument content, StringBuilder errors, AtomicInteger count, boolean isSendWebhookSuccess) {

        try {

            log.info("Enviando conteúdo [ID:{}] por e-mail.", content.getId());

            LocalDateTime evaluationInitialDate = content.getCreatedAt().atOffset(ZoneOffset.UTC).toLocalDateTime();

            LocalDateTime evaluationFinalDate = LocalDateTime.now();

            long days = Duration.between(evaluationInitialDate, evaluationFinalDate).toDays();

            UserEvaluatorDocument userEvaluator = this.userEvaluatorRepository.findById(content.getEvaluationKey())
                    .orElseThrow(() -> new BusinessNotFoundException("Conteúdo sem avaliador humano."));

            Map<String, Object> variables = new HashMap<>();
            variables.put("fullname", content.getFullname());
            variables.put("niche", content.getNiche());
            variables.put("title", content.getTitle());
            variables.put("status", content.getStatus().name());
            variables.put("contentId", content.getId());
            variables.put("evaluationDate", evaluationFinalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            variables.put("evaluationName", userEvaluator.getName());
            variables.put("timeAnalysis", String.valueOf(days).concat(days > 1 ? "dias" : "dia"));
            variables.put("logs", !isSendWebhookSuccess ? errors.toString() : "");
            variables.put("feedback", content.getJustificationEvaluation());

            this.serviceMailClient.send(new ServiceEmailClientRequest(
                    content.getCreatorEmail(),
                    "Two Approval Content API - O conteúdo %s foi analisado pela nossa equipe.".formatted(content.getTitle()),
                    "status-evaluation-content-creator-template",
                    variables
            ));

            log.info("Conteúdo [ID:{}] enviado com sucesso por e-mail!", content.getId());
            content.setFlagNotification(true);

        } catch (Exception e) {
            String msg = "Falha ao enviar e-mail. Causa: %s.\n".formatted(e.getMessage());
            errors.append(msg);
            log.error(msg);
            count.set(count.get() + 1);
            if (count.get() <= 10) {
                this.retry(() -> this.sendEmail(content, errors, count, errors.isEmpty()), 3000);
                return;
            }
            if (!isSendWebhookSuccess || ObjectUtils.isEmpty(content.getWebhook())) {
                content.setJustificationFailSendNotifiction(errors.toString());
            }
        }
    }

    private void sendWebhook(ContentDocument content, StringBuilder errors, AtomicInteger count) {
        String eventId = UUID.randomUUID().toString();
        try {
            log.info("Enviando conteúdo [ID:{}] via webhook, pelo evento {}", content.getId(), eventId);
            WebhookDataReference webhook = content.getWebhook();

            HttpStatus httpStatus = this.webhookClient.execute(webhook.getUrl(), HttpMethod.valueOf(webhook.getMethod()), new WebhookEventClientRequest(
                    eventId,
                    content.getId(),
                    content.getStatus().name(),
                    content.getJustificationEvaluation()
            ));

            if (!httpStatus.is2xxSuccessful()) {
                String msg = "Envio de webhook não retornou com sucesso. Código Http: %s:%s".formatted(httpStatus.name(), httpStatus.value());
                log.error(msg);
                throw new BusinessInternalServerErrorException(msg);
            }

            log.info("Conteúdo [ID:{}] enviado com sucesso via webhook! Evento: {}.", content.getId(), eventId);
            errors.delete(0, errors.length());

        } catch (Exception e) {
            String msg = "Evento: %s. Falha ao enviar webhook. Causa: %s.\n".formatted(eventId, e.getMessage());
            errors.append(msg);
            log.error(msg);
            count.set(count.get() + 1);
            if (count.get() <= 5) {
                this.retry(() -> this.sendWebhook(content, errors, count), 2000);
            }
        }
    }

    private void retry(Runnable runnable, long sleep) {
        try {
            Thread.sleep(sleep);
            runnable.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
