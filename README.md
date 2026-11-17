# Argos · API Java

API REST do **Argos**, o copiloto do pequeno produtor rural.
FIAP · Global Solution 2026/2 · CitizenTech · Disciplina: Domain Driven Design Using Java.

## Integrantes

| Nome | RM | Turma |
|---|---|---|
| Ana Clara Pereira de Magalhães | RM560871 | 1TDSPF |
| Diogo Guilherme de Assis Reis | RM573301 | 1TDSPF |
| Gabriel Razo Dantas | RM572244 | 1TDSPF |
| Gabriel Ricardo Gomes Lima | RM572279 | 1TDSPY |
| Pedro Henrique de Lima Reis | RM569178 | 1TDSPF |

## Links

- Repositório (GitHub): está no pdf
- API publicada no Render: está no pdf
- Vídeo (YouTube não listado): _preencher_

## A solução

O pequeno produtor rural perde safra por seca, chuva forte e pragas. Muitas vezes ele tem pouca leitura,
pouco sinal de celular e não recebe visita de técnico, então a informação não chega até ele.

O Argos ajuda esse produtor. Esta API guarda no Oracle o cadastro do produtor (com consentimento),
as propriedades dele e o que está plantado em cada uma, e não deixa plantar mais área do que a propriedade tem.
O front-end do grupo usa a API nas telas **Triagem** (cadastro) e **Hoje** (roças e plantios).

Relacionamento: **Produtor 1:N Propriedade 1:N Plantio**.

## Tecnologias

Java 21 · Spring Boot · JDBC com PreparedStatement (sem JPA) · Oracle · Bean Validation · Docker · Render

## Estrutura do projeto

```
argos-api-java/
├── database/argos_banco.sql     script das tabelas, sequences e dados
├── docs/                        documentação em PDF e coleção do Postman/Insomnia
├── src/main/java/br/com/fiap/argos/
│   ├── conexoes/     ConexaoFactory
│   ├── entities/     Produtor, Propriedade, Plantio
│   ├── dto/          ProdutorDTO, PropriedadeDTO, PlantioDTO
│   ├── dao/          ProdutorDAO, PropriedadeDAO, PlantioDAO
│   ├── bo/           ProdutorBo, PropriedadeBo, PlantioBo
│   ├── controller/   ProdutorController, PropriedadeController, PlantioController
│   ├── exception/    RecursoNaoEncontradoException, RegraNegocioException, ExcecoesConexao
│   ├── handler/      GlobalExceptionHandler, Erro
│   └── config/       CorsConfig
├── Dockerfile
└── pom.xml
```

| Pacote | O que faz |
|---|---|
| `conexoes` | `ConexaoFactory`: abre a conexão com o Oracle da FIAP (URL no código; usuário e senha das variáveis `DB_USUARIO` e `DB_SENHA`) |
| `entities` | Classes do domínio: Produtor, Propriedade e Plantio |
| `dto` | Dados que chegam no corpo da requisição, com as validações (`@NotBlank`, `@Size`, `@Pattern`, `@Positive`...) |
| `dao` | Acesso ao Oracle com JDBC e PreparedStatement. O id vem das sequences `sq_tb_...` |
| `bo` | Regras de negócio e conversão do DTO para a entidade (`converterPara...`) |
| `controller` | Rotas REST e status HTTP |
| `exception` | Exceções próprias: `RecursoNaoEncontradoException` (404), `RegraNegocioException` (409) e `ExcecoesConexao` (falha ao conectar no banco) |
| `handler` | `GlobalExceptionHandler`: devolve todo erro no mesmo JSON (classe `Erro`), sem stack trace |
| `config` | `CorsConfig`: libera o front para chamar a API |

## Como rodar

1. Rode o script `database/argos_banco.sql` no SQL Developer (F5).
2. Crie as variáveis de ambiente `DB_USUARIO` e `DB_SENHA`.
   No IntelliJ: **Run → Edit Configurations → Environment variables**: `DB_USUARIO=seu_rm;DB_SENHA=sua_senha`.
3. Rode a classe `ArgosApiApplication`.
4. Teste no Postman ou Insomnia: `GET http://localhost:8080/produtores`
   (ou importe `docs/Argos-API.postman_collection.json`, com todas as rotas na ordem).

Usuário e senha nunca ficam no código nem no GitHub.

## Deploy no Render

1. Suba o projeto para o GitHub (sem a pasta `target`).
2. Render → **New → Web Service** → escolha o repositório → Language: **Docker**.
3. Em **Environment**, crie as variáveis `DB_USUARIO` e `DB_SENHA`.
4. O Render monta a imagem pelo `Dockerfile` (multi-stage: um estágio compila com Maven, o outro só roda o .jar) e publica a API.

| Variável | Para que serve |
|---|---|
| `DB_USUARIO` | Usuário do Oracle |
| `DB_SENHA` | Senha do Oracle |
| `PORT` | Porta HTTP (o Render preenche sozinho; no computador é 8080) |

## Endpoints

| Método | Rota | Sucesso | Erros |
|---|---|---|---|
| POST | `/produtores` | 201 + Location | 400 · 409 |
| GET | `/produtores` | 200 | — |
| GET | `/produtores/{id}` | 200 | 404 |
| PUT | `/produtores/{id}` | 200 | 400 · 404 · 409 |
| DELETE | `/produtores/{id}` | 204 | 404 · 409 |
| GET | `/produtores/{id}/propriedades` | 200 | 404 |
| POST | `/produtores/{id}/propriedades` | 201 + Location | 400 · 404 |
| GET | `/propriedades` · `/propriedades/{id}` | 200 | 404 |
| PUT | `/propriedades/{id}` | 200 | 400 · 404 · 409 |
| DELETE | `/propriedades/{id}` | 204 | 404 · 409 |
| GET | `/propriedades/{id}/plantios` | 200 | 404 |
| POST | `/propriedades/{id}/plantios` | 201 + Location | 400 · 404 · 409 |
| GET | `/plantios` · `/plantios/{id}` | 200 | 404 |
| PUT | `/plantios/{id}` | 200 | 400 · 404 · 409 |
| DELETE | `/plantios/{id}` | 204 | 404 |

Método não aceito → 405. Erro inesperado → 500.

### Exemplos

`POST /produtores`

```json
{
  "nome": "Cida",
  "telefone": "87999990000",
  "municipioId": 2611101,
  "canalPreferido": "AUDIO",
  "preferenciaLeitura": "OUVIR",
  "anosExperiencia": 30,
  "recebeAter": false,
  "consentimento": true
}
```

`POST /produtores/{id}/propriedades`

```json
{
  "nome": "Sítio Boa Esperança",
  "municipioId": 2611101,
  "areaHa": 3.0,
  "fonteAgua": "CISTERNA",
  "irrigacao": "NAO"
}
```

`POST /propriedades/{id}/plantios`

```json
{
  "culturaId": 1,
  "areaHa": 2.5,
  "dataPlantio": "2026-10-20",
  "dataColheitaPrevista": "2027-02-20",
  "estagio": "CRESCIMENTO"
}
```

### Formato de erro

```json
{
  "status": 409,
  "mensagem": "A área plantada passa da área da propriedade. Área livre: 0,50 ha",
  "campos": []
}
```

## Regras de negócio

| Regra | O que diz | Status |
|---|---|---|
| RN01 | A soma das áreas plantadas (sem contar o que já foi colhido) não pode passar da área da propriedade | 409 |
| RN02 | Telefone único por produtor | 409 |
| RN03 | Sem consentimento não há cadastro | 400 |
| RN05 | Não remove produtor com propriedades nem propriedade com plantios | 409 |

## Documentação

- `docs/Argos_API_Java_Documentacao.pdf`: solução, diagrama de classes, DER, endpoints, pacotes, validações e deploy.
- `docs/Argos-API.postman_collection.json`: coleção com todas as rotas para o Postman ou Insomnia.
