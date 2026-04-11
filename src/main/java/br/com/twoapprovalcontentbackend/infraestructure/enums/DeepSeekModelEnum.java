package br.com.twoapprovalcontentbackend.infraestructure.enums;

import lombok.Getter;

@Getter
public enum DeepSeekModelEnum {

    DEEPSEEK_REASONER("deepseek-reasoner"),
    DEEPSEEK_CHAT("deepseek-chat");

    private final String model;

    DeepSeekModelEnum(String model) {
        this.model = model;
    }
}
