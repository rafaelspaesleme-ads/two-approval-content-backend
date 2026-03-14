package br.com.twoapprovalcontentbackend.infraestructure.exceptions;

public class BusinessUnprocessableException extends BusinessException {

    protected BusinessUnprocessableException(String message) {
        super(message);
    }

    protected BusinessUnprocessableException(String message, boolean hasMetadata) {
        super(message, hasMetadata);
    }

    protected BusinessUnprocessableException(String message, Throwable cause, boolean hasMetadata) {
        super(message, cause, hasMetadata);
    }
}
