package br.com.twoapprovalcontentbackend.infraestructure.persistences.models;

import br.com.twoapprovalcontentbackend.domain.AuditEntity;
import br.com.twoapprovalcontentbackend.infraestructure.enums.AIStatusEvaluationEnum;
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
@Document(value = "ai_response_doc")
public class AIResponseDocument extends AuditEntity {

    private AIStatusEvaluationEnum status;

    private String justification;

    private String externalIdAI;

    @Indexed
    private String evaluationKey;

    @Indexed
    private String contentId;

}
