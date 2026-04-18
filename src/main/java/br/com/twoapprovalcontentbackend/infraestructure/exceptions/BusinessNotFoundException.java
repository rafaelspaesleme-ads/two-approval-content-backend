package br.com.twoapprovalcontentbackend.infraestructure.exceptions;

public class BusinessNotFoundException extends BusinessException {

    public BusinessNotFoundException(String message) {
        super(message);
    }

    protected BusinessNotFoundException(String message, boolean hasMetadata) {
        super(message, hasMetadata);
    }

    protected BusinessNotFoundException(String message, Throwable cause, boolean hasMetadata) {
        super(message, cause, hasMetadata);
    }
}
