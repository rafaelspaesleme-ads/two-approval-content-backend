package br.com.twoapprovalcontentbackend.infraestructure.exceptions;

public class BusinessBadRequestException extends BusinessException {

    protected BusinessBadRequestException(String message) {
        super(message);
    }

    protected BusinessBadRequestException(String message, boolean hasMetadata) {
        super(message, hasMetadata);
    }

    protected BusinessBadRequestException(String message, Throwable cause, boolean hasMetadata) {
        super(message, cause, hasMetadata);
    }
}
