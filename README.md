# API de aprovação de conteúdo em 2 etapas.

# 📖 Estudo de Caso – API de Aprovação de Conteúdo com IA

## 🎯 Contexto

Plataformas modernas de conteúdo precisam garantir qualidade, segurança e conformidade antes da publicação.

Com o avanço da IA, tornou-se possível **automatizar parte do processo de revisão**, reduzindo esforço humano e aumentando a eficiência — **sem remover o controle humano final**.

Este estudo de caso propõe uma **API de Aprovação de Conteúdo em duas etapas**:

1. **Análise automatizada por IA**
2. **Revisão final por um usuário humano**

---

## 🧩 Problema

O processo atual de aprovação apresenta desafios:

- Revisores humanos sobrecarregados
- Conteúdos inadequados chegando à revisão final
- Falta de padronização na avaliação
- Processo lento e pouco escalável

Além disso:

- A IA existe, mas não está integrada ao fluxo
- Aprovações são totalmente manuais

---

## 💡 Solução Proposta

Criar uma **API de Aprovação de Conteúdo** que implemente um **fluxo híbrido**:

### 🔍 Etapa 1 – Avaliação por IA

- Todo conteúdo enviado para revisão passa primeiro por uma **IA (DeepSeek API)**
- A IA analisa:
    - Linguagem imprópria
    - Conteúdo sensível
    - Qualidade mínima do texto
- A IA **não publica nem rejeita definitivamente**
- Ela apenas **recomenda uma decisão**

### 👤 Etapa 2 – Aprovação Humana

- Um revisor humano analisa:
    - O conteúdo
    - A recomendação da IA
- A decisão final **sempre é humana**

> 🎯 A IA apoia o processo, o humano controla o resultado.
>

---

## 👤 Usuário Protagonista

**Ricardo**, desenvolvedor backend júnior, recebeu a missão de:

- Criar um fluxo de aprovação moderno
- Integrar IA sem perder controle humano
- Garantir rastreabilidade e auditoria
- Manter o sistema simples e evolutivo

---

## 🏗️ Escopo da API

### Tipos de Conteúdo

- Artigos
- Postagens
- Conteúdos institucionais

---

### Papéis do Sistema

- **AUTHOR** – cria e edita conteúdo
- **AI_REVIEWER** – avalia automaticamente (DeepSeek)
- **HUMAN_REVIEWER** – avalia automaticamente e em seguida, aprova ou rejeita
- **ADMIN** – publica conteúdo

---

## 🔄 Ciclo de Vida do Conteúdo (Estados)

```
DRAFT
  ↓
AI_REVIEW
  ↓
AI_APPROVED OR AI_REJECTED → **IN_REVIEW** → HUMAN_REVIEW → APPROVED
								                         ↘
														               REJECTED

```

### Estados

- **DRAFT** – rascunho
- **AI_REVIEW** – em análise pela IA
- **AI_APPROVED** – IA recomenda aprovação
- **AI_REJECTED** – IA recomenda rejeição
- **IN_REVIEW** – aguardando revisor humano
- **APPROVED** – aprovado por humano
- **REJECTED** – rejeitado por humano

---

## ⚙️ Funcionalidades Principais

### 1️⃣ Criação de Conteúdo

- Criado como **DRAFT**
- Editável pelo autor

---

### 2️⃣ Submissão para Avaliação

- Autor submete conteúdo
- Status muda para **AI_REVIEW**

---

### 3️⃣ Avaliação por IA (DeepSeek)

- API envia conteúdo para a DeepSeek
- IA retorna:
    - Score de qualidade
    - Flags (conteúdo sensível, linguagem imprópria)
    - Recomendação: APPROVE / REJECT
- Resultado armazenado

---

### 4️⃣ Revisão Humana

- Revisor humano:
    - Analisa conteúdo
    - Analisa parecer da IA
    - Aprova ou rejeita

---

### 5️⃣ Publicação

- Apenas conteúdos **APPROVED**

---

## 🧠 Regras de Negócio

- Nenhum conteúdo vai para humano sem passar pela IA
- A IA **não toma decisões finais**
- Toda resposta da IA vai para **IN_REVIEW**
- Conteúdo em **IN_REVIEW** não pode ser editado
- Toda decisão (IA ou humana) deve ser registrada
- Justificativa é obrigatória para rejeição humana

---

## 🧱 Entidades Principais

### Content

- id
- title
- body
- status
- authorId
- createdAt
- publishedAt

---

### AIReview

- id
- contentId
- score
- recommendation
- flags
- reviewedAt

---

### HumanReview

- id
- contentId
- reviewerId
- decision
- reason
- reviewedAt

---

### ContentHistory (opcional)

- id
- contentId
- oldStatus
- newStatus
- changedBy
- changedAt

---

## 🧪 Casos de Uso Principais

### 🔹 Criar Conteúdo

Autor cria conteúdo → **DRAFT**

---

### 🔹 Submeter Conteúdo

Autor envia → **AI_REVIEW**

---

### 🔹 Avaliação por IA

Sistema chama DeepSeek

Resultado → **AI_APPROVED** ou **AI_REJECTED**

---

### 🔹 Revisão Humana

Revisor analisa conteúdo + parecer da IA

Resultado → **APPROVED** ou **REJECTED**

---

### 🔹 Publicação

Após **APPROVED** humano → **PUBLISHED**

---

## 🏛️ Arquitetura Conceitual

- API REST
- Camada de serviço controlando estados
- Cliente HTTP para DeepSeek API
- Persistência do parecer da IA
- Regras claras de transição

> 🎯 IA é dependência externa, não regra de negócio central.

## 🔗 Links uteis:

### 🌐 [Arquitetura do projeto](https://drive.google.com/file/d/1dxgkLCqjlx8CZeB91uziATsVNMvY9ido/view?usp=drive_link)
### 🌐 [Artigo de tratamento global de erros (EP.10)](https://www.linkedin.com/pulse/tratamento-global-de-erros-em-apis-rest-rafael-serdeiro-paes-leme-i1hpe/)
### 🌐 [Meu Linkedin](https://www.linkedin.com/in/rafaelspaeslemeads/)
### 🌐 [Meu Canal](https://www.youtube.com/@rafaelpaesleme-dev)
### 🌐 [Instagram](https://www.instagram.com/devandcontainers/)
### 🌐 [Grupo do WhatsApp](https://chat.whatsapp.com/IUHaVq7HangFhcyIgwVyAn)
