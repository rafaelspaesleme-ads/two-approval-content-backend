package br.com.twoapprovalcontentbackend.infraestructure.exceptions;

public class BusinessUnauthorizedException extends BusinessException {

    public BusinessUnauthorizedException(String message) {
        super(message);
    }

    protected BusinessUnauthorizedException(String message, boolean hasMetadata) {
        super(message, hasMetadata);
    }

    protected BusinessUnauthorizedException(String message, Throwable cause, boolean hasMetadata) {
        super(message, cause, hasMetadata);
    }
}
