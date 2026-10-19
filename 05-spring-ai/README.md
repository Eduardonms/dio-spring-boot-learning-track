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

## API REST

A API fica disponível em `http://localhost:8080`.

### Swagger UI e OpenAPI

Com a aplicação em execução, acesse [Swagger UI](http://localhost:8080/swagger-ui/index.html) para explorar e testar os endpoints. A especificação OpenAPI em JSON fica disponível em [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).

No Swagger UI, use **Try it out** para enviar requisições. Para `POST /transactions/ai`, selecione um arquivo de áudio no campo `file`; essa operação requer uma chave válida da OpenAI.

### Criar uma transação

`POST /transactions` recebe a descrição, a categoria e o valor em centavos. As categorias aceitas são `GROCERIES`, `PHARMA` e `AUTO`.

```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"description":"Compras do mercado","category":"GROCERIES","amount":1234}'
```

O endpoint responde com `201 Created`. O valor da resposta é apresentado em reais (por exemplo, `12.34`). A descrição não pode ficar em branco e o valor deve ser maior que zero.

### Consultar transações por categoria

`GET /transactions/{categoria}` retorna as transações da categoria informada:

```bash
curl http://localhost:8080/transactions/GROCERIES
```

### Enviar um comando de voz

`POST /transactions/ai` recebe um arquivo de áudio no campo multipart `file` e retorna um arquivo MP3:

```bash
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@comando.m4a" \
  --output resposta.mp3
```

Esse fluxo requer uma chave válida da OpenAI. Requisições com dados de transação inválidos recebem `400 Bad Request`, com uma mensagem e os campos que precisam ser corrigidos.

Exemplo de resposta para uma requisição inválida:

```json
{
  "message": "A requisição contém dados inválidos.",
  "errors": {
    "description": "A descrição é obrigatória.",
    "amount": "O valor deve ser maior que zero centavos."
  }
}
```

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
