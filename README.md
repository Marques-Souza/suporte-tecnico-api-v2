# Suporte Técnico API

Esta API organiza o atendimento de uma assistência técnica: nela é possível cadastrar clientes, técnicos e usuários, abrir ordens de serviço e acompanhar o andamento de cada ordem.

O projeto foi feito com Java e Spring Boot. A aplicação usa PostgreSQL para guardar os dados e JWT para proteger as rotas. Também há testes automatizados e documentação interativa dos endpoints.

## O que dá para fazer

- Cadastrar, consultar e atualizar clientes e técnicos.
- Criar usuários com os perfis `ADMIN` ou `ATTENDANT`.
- Abrir ordens de serviço associadas a um cliente e a um técnico.
- Consultar ordens e avançar seu status: `OPEN`, `IN_PROGRESS` e `CLOSED`.
- Fazer login e usar um token JWT nas chamadas protegidas.

O status de uma ordem segue esta sequência: `OPEN` → `IN_PROGRESS` → `CLOSED`.

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Data JPA e Hibernate
- Spring Security e JWT
- PostgreSQL
- Bean Validation
- Springdoc OpenAPI / Swagger
- Maven
- Docker e Docker Compose
- JUnit

## Executar com Docker

É necessário ter Docker e Docker Compose instalados. Na pasta do projeto, rode:

```bash
docker compose up --build
```

O Compose inicia o PostgreSQL e, depois que o banco estiver pronto, a API. Ela fica disponível em `http://localhost:8080`.

Para parar os containers, use `Ctrl+C` e depois:

```bash
docker compose down
```

Os dados do PostgreSQL ficam em um volume do Docker e continuam lá mesmo depois que os containers são removidos.

## Primeiro acesso

No ambiente Docker local, o Compose cria o usuário administrador abaixo:

```text
E-mail: admin@demo.com
Senha:  demo-admin-123
Perfil: ADMIN
```

Esses dados são apenas para demonstração local. Troque as credenciais e os segredos antes de usar a aplicação em qualquer ambiente compartilhado ou de produção.

Faça login para receber o token:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"admin@demo.com\",\"password\":\"demo-admin-123\"}"
```

A resposta contém o campo `token`. Nas chamadas protegidas, envie esse valor no cabeçalho:

```text
Authorization: Bearer SEU_TOKEN
```

O token expira em 30 minutos.

## Rotas disponíveis

Com exceção do login e da documentação, as rotas exigem um token válido. As rotas de `/users` são restritas ao perfil `ADMIN`.

| Método | Rota | Ação |
| --- | --- | --- |
| `POST` | `/auth/login` | Entrar e receber um token |
| `POST` | `/clients` | Cadastrar cliente |
| `GET` | `/clients` | Listar clientes |
| `GET` | `/clients/{id}` | Consultar cliente |
| `PUT` | `/clients/{id}` | Atualizar cliente |
| `POST` | `/technicians` | Cadastrar técnico |
| `GET` | `/technicians` | Listar técnicos |
| `GET` | `/technicians/{id}` | Consultar técnico |
| `PUT` | `/technicians/{id}` | Atualizar técnico |
| `POST` | `/orders` | Abrir ordem de serviço |
| `GET` | `/orders` | Listar ordens |
| `GET` | `/orders/{id}` | Consultar ordem |
| `PATCH` | `/orders/{id}/status` | Atualizar o status da ordem |
| `POST` | `/users` | Cadastrar usuário (`ADMIN`) |
| `GET` | `/users` | Listar usuários (`ADMIN`) |
| `GET` | `/users/{id}` | Consultar usuário (`ADMIN`) |
| `PUT` | `/users/{id}` | Atualizar usuário (`ADMIN`) |
| `PATCH` | `/users/{id}/password` | Alterar senha (`ADMIN`) |

## Documentação interativa

Com a aplicação rodando, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

O Swagger mostra os parâmetros e os formatos esperados por cada rota. Para testar as rotas protegidas por ali, use **Authorize** e cole o token recebido no login.

## Configuração

O arquivo `docker-compose.yml` já define os valores usados no ambiente local. A aplicação lê as configurações abaixo por variáveis de ambiente:

| Variável | Para que serve |
| --- | --- |
| `DB_URL` | Endereço JDBC do PostgreSQL |
| `DB_USERNAME` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |
| `JWT_SECRET` | Segredo usado para assinar os tokens; deve ter ao menos 32 caracteres |
| `ADMIN_EMAIL` | E-mail do administrador criado na inicialização |
| `ADMIN_PASSWORD` | Senha desse administrador |

Se o usuário administrador ainda não existir, a aplicação o cria ao iniciar. Se já existir, não cria outro.

## Testes

Os testes usam o banco `suporte_api_test` em `localhost:5432`. Configure um PostgreSQL local com esse banco e defina `DB_PASSWORD` antes de executá-los.

No Windows:

```powershell
$env:DB_PASSWORD = "sua-senha-do-postgres"
.\mvnw.cmd test
```

No Linux ou macOS:

```bash
DB_PASSWORD="sua-senha-do-postgres" ./mvnw test
```

## Organização do código

O código está separado por responsabilidade:

- `domain`: modelos, regras de negócio e contratos dos repositórios.
- `application`: casos de uso e objetos usados nas entradas e respostas da API.
- `infrastructure`: configurações, segurança e persistência com JPA.
- `presentation`: controllers HTTP e tratamento de erros.
