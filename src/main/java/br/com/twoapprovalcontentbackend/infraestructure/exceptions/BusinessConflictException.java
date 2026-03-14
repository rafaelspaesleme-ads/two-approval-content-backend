package br.com.twoapprovalcontentbackend.infraestructure.exceptions;

public class BusinessConflictException extends BusinessException {

    protected BusinessConflictException(String message) {
        super(message);
    }

    protected BusinessConflictException(String message, boolean hasMetadata) {
        super(message, hasMetadata);
    }

    protected BusinessConflictException(String message, Throwable cause, boolean hasMetadata) {
        super(message, cause, hasMetadata);
    }
}
