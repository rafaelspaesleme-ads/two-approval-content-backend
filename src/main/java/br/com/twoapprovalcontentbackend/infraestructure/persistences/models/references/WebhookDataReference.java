package br.com.twoapprovalcontentbackend.infraestructure.persistences.models.references;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookDataReference {

    private String url;
    private String method;

}
