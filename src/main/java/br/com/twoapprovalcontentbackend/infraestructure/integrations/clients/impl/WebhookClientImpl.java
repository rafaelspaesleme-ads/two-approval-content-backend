package br.com.twoapprovalcontentbackend.infraestructure.integrations.clients.impl;

import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessInternalServerErrorException;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.clients.WebhookClient;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.requests.WebhookEventClientRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebhookClientImpl implements WebhookClient {

    private static final Duration TIME_OUT = Duration.ofSeconds(10L);

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(TIME_OUT)
            .build();

    @Override
    public HttpStatus execute(String url, HttpMethod method, WebhookEventClientRequest request) {

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method(method.name(), HttpRequest.BodyPublishers.ofString(request.toJson()))
                    .timeout(TIME_OUT)
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return HttpStatus.valueOf(response.statusCode());

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BusinessInternalServerErrorException("Erro ao enviar requisição para serviço de webhook.", e, true);
        }

    }
}
