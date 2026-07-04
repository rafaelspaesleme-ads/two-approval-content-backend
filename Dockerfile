# =========================
# STAGE 1 - BUILD DO MAVEN
# =========================

# build da imagem do maven
FROM maven:3.9.9-eclipse-temurin-21 AS build

# criando diretorio de trabalho
WORKDIR /app

# add pom ao diretorio de trabalho
COPY pom.xml .

# criando cache de dependencias do ambiente local
RUN mvn dependency:go-offline

# add projeto ao diretorio de trabalho
COPY src ./src

# build do projeto
RUN mvn clean package


# =========================
# STAGE 2 - BUILD DO RUNTIME DO PROJETO
# =========================
# build da imagem do JDK do projeto
FROM eclipse-temurin:21-jdk-jammy

LABEL author="Rafael S. Paes Leme"
LABEL version="1.0.0"
LABEL title="Two Approval Content API"
LABEL description="API de aprovação de conteúdo em 2 etapas (IA + Humano)."
LABEL source="https://github.com/rafaelspaesleme-ads/two-approval-content-backend"

ARG BASE_URL
ENV BASE_URL=${BASE_URL}

ARG HOST_MONGO_DB
ENV HOST_MONGO_DB=${HOST_MONGO_DB}

ARG PORT_MONGO_DB
ENV PORT_MONGO_DB=${PORT_MONGO_DB}

ARG USERNAME_MONGO_DB
ENV USERNAME_MONGO_DB=${USERNAME_MONGO_DB}

ARG PASSWORD_MONGO_DB
ENV PASSWORD_MONGO_DB=${PASSWORD_MONGO_DB}

ARG DB_MONGO_DB
ENV DB_MONGO_DB=${DB_MONGO_DB}

ARG AUTH_DB_MONGO_DB
ENV AUTH_DB_MONGO_DB=${AUTH_DB_MONGO_DB}

ARG DEEP_SEEK_API_KEY
ENV DEEP_SEEK_API_KEY=${DEEP_SEEK_API_KEY}

ARG HOST_SRV_MAIL
ENV HOST_SRV_MAIL=${HOST_SRV_MAIL}

ARG PORT_SRV_MAIL
ENV PORT_SRV_MAIL=${PORT_SRV_MAIL}

ARG USERNAME_SRV_MAIL
ENV USERNAME_SRV_MAIL=${USERNAME_SRV_MAIL}

ARG PASSWORD_SRV_MAIL
ENV PASSWORD_SRV_MAIL=${PASSWORD_SRV_MAIL}

ARG SECRET_JWT
ENV SECRET_JWT=${SECRET_JWT}

ARG API_EVALUATION_KEY
ENV API_EVALUATION_KEY=${API_EVALUATION_KEY}

ARG API_CREATOR_CONTENT_KEY
ENV API_CREATOR_CONTENT_KEY=${API_CREATOR_CONTENT_KEY}

ARG CONTEXTS_SUPPORT_EMAIL
ENV CONTEXTS_SUPPORT_EMAIL=${CONTEXTS_SUPPORT_EMAIL}

ARG CONTEXTS_SUPPORT_WHATSAPP
ENV CONTEXTS_SUPPORT_WHATSAPP=${CONTEXTS_SUPPORT_WHATSAPP}

ARG CONTEXTS_SUPPORT_DOC
ENV CONTEXTS_SUPPORT_DOC=${CONTEXTS_SUPPORT_DOC}

# criando diretorio de trabalho
WORKDIR /app

# copiando imagem maven para a imagem do runtime do projeto.
COPY --from=build /app/target/*.jar app.jar

# expondo a porta
EXPOSE 8080

# configuração de limites de uso de memoria ram
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# executando projeto
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]


