package br.com.twoapprovalcontentbackend.application.services;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.EvaluateContentRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.EvaluateContentResponse;

public interface AuthorService {
    EvaluateContentResponse evaluate(EvaluateContentRequest request);
}
