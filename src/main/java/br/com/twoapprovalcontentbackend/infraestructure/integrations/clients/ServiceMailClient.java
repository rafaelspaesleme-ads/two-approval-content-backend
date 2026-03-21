package br.com.twoapprovalcontentbackend.infraestructure.integrations.clients;

import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.requests.ServiceEmailClientRequest;

public interface ServiceMailClient {
    void send(ServiceEmailClientRequest request);
}
