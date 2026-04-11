package br.com.twoapprovalcontentbackend.infraestructure.integrations.clients.impl;

import br.com.twoapprovalcontentbackend.infraestructure.enums.DeepSeekModelEnum;
import br.com.twoapprovalcontentbackend.infraestructure.enums.DeepSeekRoleSystemEnum;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessConflictException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessInternalServerErrorException;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.clients.DeepSeekClient;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses.DeepSeekEvaluationClientChoicesMsgResponse;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses.DeepSeekEvaluationClientChoicesResponse;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses.DeepSeekEvaluationClientRateLimitResponse;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses.DeepSeekEvaluationClientResponse;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses.DeepSeekEvaluationClientUsageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.RateLimit;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeepSeekClientImpl implements DeepSeekClient {

    private final ChatClient client;

    @Override
    public DeepSeekEvaluationClientResponse evaluationContent(String content, DeepSeekModelEnum model) {

        try {
            DeepSeekChatOptions options = DeepSeekChatOptions.builder()
                    .model(model.getModel())
                    .build();

            ChatResponse response = this.client.prompt()
                    .options(options)
                    .messages(
                            new SystemMessage(DeepSeekRoleSystemEnum.ROLE_SYSTEM_DEFAULT.getContent()),
                            new UserMessage(content)
                    )
                    .call()
                    .chatResponse();

            String responseEvaluation = Optional.of(response).map(ChatResponse::getResult).map(Generation::getOutput).map(AbstractMessage::getText)
                    .orElseThrow(() -> new BusinessConflictException("Avaliação da IA não teve uma resposta adequada, tente novamente."));


            return buildResponse(model, response, responseEvaluation);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessInternalServerErrorException("Não foi possivel realizar avaliação com a IA.");
        }

    }

    private static DeepSeekEvaluationClientResponse buildResponse(DeepSeekModelEnum model, ChatResponse response, String responseEvaluation) {
        return new DeepSeekEvaluationClientResponse(
                Optional.ofNullable(response.getMetadata()).map(ChatResponseMetadata::getId).orElse(UUID.randomUUID().toString()),
                "chat.completion",
                Instant.now().getEpochSecond(),
                model,
                new DeepSeekEvaluationClientChoicesResponse(
                        new DeepSeekEvaluationClientChoicesMsgResponse(
                                Optional.ofNullable(response.getResult()).map(Generation::getOutput).map(AbstractMessage::getMessageType).map(MessageType::getValue).orElse(MessageType.ASSISTANT.getValue()),
                                responseEvaluation
                        ),
                        Optional.ofNullable(response.getResult()).map(Generation::getMetadata).map(ChatGenerationMetadata::getFinishReason).orElse("")
                ),
                new DeepSeekEvaluationClientUsageResponse(
                        Optional.ofNullable(response.getMetadata()).map(ChatResponseMetadata::getUsage).map(Usage::getPromptTokens).orElse(0),
                        Optional.ofNullable(response.getMetadata()).map(ChatResponseMetadata::getUsage).map(Usage::getCompletionTokens).orElse(0),
                        Optional.ofNullable(response.getMetadata()).map(ChatResponseMetadata::getUsage).map(Usage::getTotalTokens).orElse(0)
                ),
                new DeepSeekEvaluationClientRateLimitResponse(
                        Optional.ofNullable(response.getMetadata()).map(ChatResponseMetadata::getRateLimit).map(RateLimit::getRequestsLimit).orElse(0L),
                        Optional.ofNullable(response.getMetadata()).map(ChatResponseMetadata::getRateLimit).map(RateLimit::getTokensLimit).orElse(0L)
                )
        );
    }

}
