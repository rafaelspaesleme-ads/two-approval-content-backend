package br.com.twoapprovalcontentbackend.infraestructure.exceptions;

public class BusinessInternalServerErrorException extends BusinessException {

    public BusinessInternalServerErrorException(String message) {
        super(message);
    }

    protected BusinessInternalServerErrorException(String message, boolean hasMetadata) {
        super(message, hasMetadata);
    }

    protected BusinessInternalServerErrorException(String message, Throwable cause, boolean hasMetadata) {
        super(message, cause, hasMetadata);
    }
}
