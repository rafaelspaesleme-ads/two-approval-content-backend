package br.com.twoapprovalcontentbackend.infraestructure.integrations.models.requests;

import javax.validation.constraints.NotNull;
import java.util.Map;

public record ServiceEmailClientRequest(
        @NotNull(message = "E-mail destinatário não pode ser nulo.")
        String emailTo,
        @NotNull(message = "Assunto não pode ser nulo.")
        String subject,
        @NotNull(message = "Identificador do template de envio de e-mail não pode ser nulo.")
        String templateName,
        @NotNull(message = "Variaveis do template de envio de e-mail não pode ser nulo.")
        Map<String, Object> variables
) {}
