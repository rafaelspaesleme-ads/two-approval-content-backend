package br.com.twoapprovalcontentbackend.infraestructure.configs.swagger;

import br.com.twoapprovalcontentbackend.infraestructure.enums.HttpHeadersKeyEnum;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;

@Slf4j
@Configuration
public class SwaggerConfig {

    @Value(value = "${spring.application.base-url}")
    private String baseUrl;

    @Value(value = "${spring.application.name}")
    private String title;

    @Value(value = "${spring.application.version}")
    private String version;

    @Value(value = "${spring.application.description}")
    private String description;

    @Value(value = "${springdoc.contacts.site}")
    private String url;

    @Value(value = "${springdoc.contacts.name}")
    private String name;

    @Value(value = "${springdoc.contacts.email}")
    private String email;

    @Value(value = "${springdoc.docs.doc}")
    private String doc;

    @Value(value = "${springdoc.docs.doc-name}")
    private String docName;

    @Value(value = "${springdoc.docs.license}")
    private String license;

    @Value(value = "${springdoc.docs.license-name}")
    private String licenseName;

    @Value(value = "${springdoc.docs.term}")
    private String term;

    @Bean
    public OpenAPI configDocs() {

        HashMap<String, Object> mapDoc = new HashMap<>();
        mapDoc.put(this.docName, "%s/%s".formatted(this.baseUrl, this.doc));

        return new OpenAPI()
                .addServersItem(new Server().url(this.baseUrl))
                .info(new Info()
                        .title(this.title)
                        .version(this.version)
                        .description(this.description)
                        .contact(new Contact()
                                .name(this.name)
                                .url(this.url)
                                .email(this.email)
                        )
                        .license(new License()
                                .name(this.licenseName)
                                .url("%s/%s".formatted(this.baseUrl, this.license))
                        )
                        .termsOfService("%s/%s".formatted(this.baseUrl, this.term))
                        .extensions(mapDoc)
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Estudo de Caso da API")
                        .url("https://github.com/rafaelspaesleme-ads/two-approval-content-backend/blob/main/README.md")
                )
                .components(new Components()
                        .addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(HttpHeaders.AUTHORIZATION)
                        )
                        .addSecuritySchemes(HttpHeadersKeyEnum.API_EVALUATION_KEY.getKey(), new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(HttpHeadersKeyEnum.API_EVALUATION_KEY.getKey())
                                .description("Chave de API do avaliador humano.")
                        )
                        .addSecuritySchemes(HttpHeadersKeyEnum.API_CREATOR_CONTENT_KEY.getKey(), new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(HttpHeadersKeyEnum.API_CREATOR_CONTENT_KEY.getKey())
                                .description("Chave de API do criador de conteúdo.")
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList(HttpHeaders.AUTHORIZATION)
                        .addList(HttpHeadersKeyEnum.API_EVALUATION_KEY.getKey())
                        .addList(HttpHeadersKeyEnum.API_CREATOR_CONTENT_KEY.getKey())
                );
    }

}
