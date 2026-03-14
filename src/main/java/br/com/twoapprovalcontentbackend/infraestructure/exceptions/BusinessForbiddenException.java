package br.com.twoapprovalcontentbackend.infraestructure.exceptions;

public class BusinessForbiddenException extends BusinessException {

    public BusinessForbiddenException(String message) {
        super(message);
    }

    protected BusinessForbiddenException(String message, boolean hasMetadata) {
        super(message, hasMetadata);
    }

    protected BusinessForbiddenException(String message, Throwable cause, boolean hasMetadata) {
        super(message, cause, hasMetadata);
    }
}
