package br.com.twoapprovalcontentbackend.infraestructure.integrations.models.requests;

public record WebhookEventClientRequest(
        String eventId,
        String contentId,
        String status,
        String message
) {

    public String toJson() {
        return "{\"eventId\": \"%s\",\"contentId\": \"%s\",\"status\": \"%s\",\"message\": \"%s\"}"
                .formatted(this.eventId, this.contentId, this.status, this.message);
    }
}
