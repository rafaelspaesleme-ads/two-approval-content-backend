package br.com.twoapprovalcontentbackend.infraestructure.integrations.clients.impl;

import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessConflictException;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.clients.ServiceMailClient;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.requests.ServiceEmailClientRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceMailClientImpl implements ServiceMailClient {

    @Value(value = "${spring.mail.username}")
    private String from;

    @Value(value = "${spring.contexts.support.email}")
    private String apiSupportMail;

    @Value(value = "${spring.contexts.support.whatsapp}")
    private String apiSupportWhatsApp;

    @Value(value = "${spring.contexts.support.linkDoc}")
    private String apiDoc;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void send(ServiceEmailClientRequest request) {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Map<String, Object> variables = new HashMap<>(request.variables());

            variables.put("apiSupportMail", this.apiSupportMail);
            variables.put("apiSupportWhatsApp", this.apiSupportWhatsApp);
            variables.put("apiDoc", this.apiDoc);

            Context context = new Context();
            context.setVariables(variables);

            String bodyHtml = templateEngine.process(request.templateName(), context);

            helper.setFrom(this.from);
            helper.setTo(request.emailTo());
            helper.setSubject(request.subject());
            helper.setText(bodyHtml, true);

            log.info("Enviando e-mail...");
            javaMailSender.send(message);
            log.info("E-mail enviado com sucesso!");

        } catch (MessagingException e) {
            throw new BusinessConflictException("Erro ao enviar e-mail. Causa: %s".formatted(e.getNextException().getMessage()));
        }

    }

}
