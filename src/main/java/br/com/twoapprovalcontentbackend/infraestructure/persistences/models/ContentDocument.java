package br.com.twoapprovalcontentbackend.infraestructure.persistences.models;

import br.com.twoapprovalcontentbackend.domain.AuditEntity;
import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import br.com.twoapprovalcontentbackend.infraestructure.enums.StatusEvaluationEnum;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.references.WebhookDataReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(value = "content_doc")
public class ContentDocument extends AuditEntity {

    private String fullname;

    private String title;

    private StatusEvaluationEnum status;

    private String content;

    @Indexed
    private String evaluationKey;

    private String creatorEmail;

    private WebhookDataReference webhook;

    private NichesEnum niche;

    private Boolean flagFindStatus;

    private Boolean flagNotification;

    private String justificationFailSendNotifiction;

    private String justificationEvaluation;

}
