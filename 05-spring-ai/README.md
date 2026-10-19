# DIO Spring Boot - Projeto Final 05: Spring AI (orçamento)

## Introdução

Este módulo final aplica Spring AI a uma API de orçamento, preservando a mesma arquitetura em camadas utilizada ao longo da trilha.

O objetivo é integrar recursos de IA sem ignorar os limites do domínio e dos casos de uso.

## Contexto do código

O projeto processa comandos de voz para criar e consultar transações financeiras.

Fluxo principal:

1. O cliente envia um arquivo de áudio.
2. O áudio é transcrito para texto.
3. O modelo seleciona uma ferramenta/caso de uso da aplicação.
4. O caso de uso persiste ou consulta os dados das transações.
5. A resposta final é convertida em áudio.

## Estrutura do projeto

- `src/main/java/dio/budgeting/domain`
  - Modelo de domínio e contrato do repositório.
- `src/main/java/dio/budgeting/application`
  - Casos de uso utilizados tanto pela API REST quanto pela chamada de ferramentas de IA.
- `src/main/java/dio/budgeting/infrastructure`
  - Adaptadores HTTP, adaptadores JPA e componentes de integração.

## Tópicos específicos do módulo

### Conversão de fala em texto

- Utiliza `TranscriptionModel` para transcrever o áudio.
- As configurações do modelo são definidas em `application.properties`.

### Chamada de ferramentas

- `ChatClient` registra as ferramentas dos casos de uso.
- Os métodos anotados com `@Tool` disponibilizam funcionalidades de negócio para o modelo.

### Conversão de texto em fala

- `TextToSpeechModel` gera um arquivo MP3 a partir do texto final.
- O endpoint de IA retorna o áudio gerado.

## Documentação do Spring AI

- Referência do Spring AI: https://docs.spring.io/spring-ai/reference/index.html
- API do ChatModel: https://docs.spring.io/spring-ai/reference/api/chatmodel.html
- API do ChatClient: https://docs.spring.io/spring-ai/reference/api/chatclient.html
- API de ferramentas: https://docs.spring.io/spring-ai/reference/api/tools.html
- API de transcrição de áudio: https://docs.spring.io/spring-ai/reference/api/audio/transcriptions.html
- API de síntese de fala: https://docs.spring.io/spring-ai/reference/api/audio/speech.html

## Referências compartilhadas de arquitetura

Os conceitos comuns de arquitetura estão documentados no README da raiz:

- [Camadas DDD](../README.md#ddd-layered-architecture)
- [Classe vs. record](../README.md#java-class-vs-java-record-in-domain-modeling)
- [Identificadores fortemente tipados](../README.md#strong-typed-identifiers)
- [Padrão Repository](../README.md#repository-pattern)
- [Casos de uso e Clean Architecture](../README.md#use-cases-and-clean-architecture)
- [Suporte ao Docker Compose](../README.md#docker-compose-support-in-development)

## Como executar

Defina sua chave de API da OpenAI:

```bash
export OPENAI_API_KEY="your_api_key_here"
```

Execute a aplicação e os testes:

```bash
./gradlew bootRun
./gradlew test
```

## Observações

- Projeto educacional final com foco em IA e disciplina arquitetural.
- Os testes de integração com provedores externos podem exigir credenciais válidas.
