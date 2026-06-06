package br.com.twoapprovalcontentbackend.infraestructure.schedules;

import br.com.twoapprovalcontentbackend.infraestructure.enums.StatusEvaluationEnum;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.impl.PersistGatewayImpl;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpireContentSchedule extends PersistGatewayImpl {

    private final ContentRepository repository;

    //@Scheduled(cron = "0 0 5 * * *") //--> todos os dias as 5h da manhã
    @Scheduled(cron = "0 */2 * * * *") //--> a cada 2 minutos
    public void runTask() {
        List<ContentDocument> contents = this.repository.findAllByFlagFindStatusTrueAndFlagNotificationTrue();

        if (!CollectionUtils.isEmpty(contents)) {
            if (contents.stream().anyMatch(content -> isContinue(content) && isNotDelete(content))) {
                contents.forEach(content -> content.setFlagFindStatus(false));
                this.repository.saveAll(super.update(contents));
            }

            if (contents.stream().anyMatch(content -> isContinue(content) && isDelete(content))) {
                this.repository.deleteAll(contents);
            }
        }

    }

    private static boolean isContinue(ContentDocument content) {
        return StatusEvaluationEnum.APPROVED.equals(content.getStatus())
                || StatusEvaluationEnum.REJECTED.equals(content.getStatus());
    }

    private static boolean isDelete(ContentDocument content) {
        return !isNotDelete(content);
    }
    private static boolean isNotDelete(ContentDocument content) {
        return StringUtils.isNotEmpty(content.getJustificationFailSendNotifiction()) && !Boolean.TRUE.equals(content.getFlagViewStattusJustificationFailSendNotification());
    }
}
