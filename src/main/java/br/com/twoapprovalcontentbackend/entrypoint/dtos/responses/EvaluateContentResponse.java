package br.com.twoapprovalcontentbackend.entrypoint.dtos.responses;

public record EvaluateContentResponse(
        String contentId,
        String status,
        String statusDescription,
        String justificationFailSendNotifiction
) {
    public EvaluateContentResponse(String contentId, String status, String statusDescription) {
        this(contentId, status, statusDescription, null);
    }
}
