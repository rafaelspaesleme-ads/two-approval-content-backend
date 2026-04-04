package br.com.twoapprovalcontentbackend.infraestructure.integrations.clients;

import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.requests.WebhookEventClientRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public interface WebhookClient {

    HttpStatus execute(String url, HttpMethod method, WebhookEventClientRequest request);
}
