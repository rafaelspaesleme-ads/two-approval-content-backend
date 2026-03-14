package br.com.twoapprovalcontentbackend.infraestructure.exceptions;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

    protected final boolean hasMetadata;

    protected BusinessException(String message) {
        super(message);
        this.hasMetadata = false;
    }

    protected BusinessException(String message, boolean hasMetadata) {
        super(message);
        this.hasMetadata = hasMetadata;
    }

    protected BusinessException(String message, Throwable cause, boolean hasMetadata) {
        super(message, cause);
        this.hasMetadata = hasMetadata;
    }
}
