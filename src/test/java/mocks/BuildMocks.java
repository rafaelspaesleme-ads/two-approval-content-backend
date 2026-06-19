package mocks;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.EvaluateContentRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.LoginRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.SendEvaluationRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.UserRegisterRequest;
import br.com.twoapprovalcontentbackend.infraestructure.enums.AIStatusEvaluationEnum;
import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import br.com.twoapprovalcontentbackend.infraestructure.enums.StatusEvaluationEnum;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.AIResponseDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.references.WebhookDataReference;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildMocks {


    public static EvaluateContentRequest buildEvaluateContentRequest() {
        return new EvaluateContentRequest(
                "Teste da Silva",
                "Titulo do Teste",
                "A Copa do Mundo 2026 entra hoje (18/06) na segunda rodada da fase de grupos. Quatro jogos movimentam o dia: Tchéquia x África do Sul (13h), Suíça x Bósnia (16h), Canadá x Catar (19h) e México x Coreia do Sul (22h).",
                "teste@teste.com",
                "TECHNOLOGY",
                new EvaluateContentRequest.Notification(
                        "https://webhook.site/bb568ced-a487-4fd0-af99-4115e16fd01c",
                        "POST"
                )
        );
    }

    public static UserEvaluatorDocument buildUserEvaluatorDocument() {
        return UserEvaluatorDocument.builder()
                .id("123456789")
                .name("Avaliador Teste")
                .email("avaliador@teste.com")
                .secret("tac_live_v1_%s".formatted(UUID.randomUUID().toString()))
                .niche(NichesEnum.TECHNOLOGY)
                .initialLoginAt(LocalDateTime.now())
                .token("token teste")
                .expiresLoginAt(LocalDateTime.now().plusMinutes(60))
                .build();
    }

    public static ContentDocument buildContentDocument() {
        return buildContentDocument(buildEvaluateContentRequest(), buildUserEvaluatorDocument());
    }
    public static ContentDocument buildContentDocument(EvaluateContentRequest request, UserEvaluatorDocument evaluatorDocument) {

        WebhookDataReference webhook = Optional.ofNullable(request.notification())
                .map(notification -> WebhookDataReference.builder()
                        .url(notification.url())
                        .method(notification.httpMethod())
                        .build())
                .orElse(null);

        return ContentDocument.builder()
                .id("1111111")
                .fullname(request.fullname())
                .title(request.title())
                .status(StatusEvaluationEnum.IN_REVIEW)
                .content(request.content())
                .evaluationKey(evaluatorDocument.getId())
                .creatorEmail(request.email())
                .niche(evaluatorDocument.getNiche())
                .webhook(webhook)
                .createdAt(Instant.now())
                .active(true)
                .build();
    }

    public static AIResponseDocument buildAIResponseDocument() {
        return AIResponseDocument.builder()
                .status(AIStatusEvaluationEnum.AI_APPROVED)
                .justification("APROVADO, ta tudo certo.")
                .externalIdAI("7777777")
                .evaluationKey(buildUserEvaluatorDocument().getId())
                .contentId(buildContentDocument().getId())
                .createdAt(Instant.now())
                .active(true)
                .build();
    }

    public static SendEvaluationRequest buildSendEvaluationRequest() {
        return new SendEvaluationRequest(
                buildContentDocument().getEvaluationKey(),
                buildContentDocument().getId(),
                StatusEvaluationEnum.APPROVED.name(),
                "Aprovado, ta tudo ok!"
        );
    }

    public static SendEvaluationRequest buildSendEvaluationRequestInReview() {
        return new SendEvaluationRequest(
                buildContentDocument().getEvaluationKey(),
                buildContentDocument().getId(),
                StatusEvaluationEnum.IN_REVIEW.name(),
                "Em revisão ainda"
        );
    }

    public static UserRegisterRequest buildUserRegisterRequest() {
        return new UserRegisterRequest(
                buildUserEvaluatorDocument().getName(),
                buildUserEvaluatorDocument().getEmail(),
                buildUserEvaluatorDocument().getNiche().name(),
                "teste"
        );
    }

    public static UserRegisterRequest buildUserRegisterRequestNotFountNiche() {
        return new UserRegisterRequest(
                buildUserEvaluatorDocument().getName(),
                buildUserEvaluatorDocument().getEmail(),
                "TESTE_hauuh",
                "teste"
        );
    }

    public static LoginRequest buildLoginRequest() {
        return new LoginRequest(
                buildUserEvaluatorDocument().getId(),
                buildUserEvaluatorDocument().getSecret()
        );
    }

}
