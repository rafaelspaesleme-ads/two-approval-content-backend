package br.com.twoapprovalcontentbackend.infraestructure.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpHeadersKeyEnum {
    API_EVALUATION_KEY("apiEvaluationKey"),
    API_CREATOR_CONTENT_KEY("apiCreatorContentKey");

    private final String key;
}
