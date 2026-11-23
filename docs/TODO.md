# TODO

Anotações das decisões, pendências e recomendações levantadas durante o upgrade para Spring Boot 4.2.0 / Java 25 e a revisão de arquitetura e testes.

## Segurança

### Concluído
- [x] Parar de concatenar `username + senha` antes do hash — agora apenas a senha é hasheada (`UserPasswordBuilder`).
- [x] Trocar BCrypt por **Argon2id** (19 MiB, 2 iterações, 1 lane) usando `DelegatingPasswordEncoder` (`SecurityConfiguration`).
- [x] Declarar `org.bouncycastle:bcprov-jdk18on` explicitamente no `build.gradle`.
- [x] Remover o registro legado do BCrypt — não há usuários antigos, então não há hashes antigos para validar.
- [x] Cobrir o encoder com `PasswordEncoderTest` (prefixo `{argon2id}`, salt aleatório, match da senha correta).

### Pendente
- [ ] Opcional no futuro: usar `upgradeEncoding()` do `DelegatingPasswordEncoder` para re-hashear após um login bem-sucedido, caso os parâmetros do Argon2 ou o algoritmo mudem.
- [ ] Adicionar rate limit no `POST /user/login`.
- [ ] Limitar o tamanho da senha recebida (o Argon2 lida bem com entradas longas, mas ainda vale validar).
- [ ] Nunca logar a senha — conferir que nenhum log/debug exponha a credencial.
- [ ] Implementar token de reset de senha de uso único com expiração.
- [ ] Considerar MFA no futuro.

## Testes

### Ferramentas já em uso
- [x] JUnit 5 + `@DisplayName` em português.
- [x] MockMvc / RestTestClient para testes rápidos de controller e segurança (já no classpath).

### Pendente — ferramentas sugeridas (ainda não implementado)
- [ ] Adicionar **REST Assured** + `@SpringBootTest(webEnvironment = RANDOM_PORT)` para alguns fluxos ponta a ponta (ex.: `POST /user` → `POST /user/login` → rota protegida).
- [ ] Adicionar **WireMock** para simular a Scryfall e isolar o client Feign de API externa lenta/frágil.
- [ ] Trocar H2 por **Testcontainers (Postgres)** nos testes de integração, para validar dialeto, JSON e constraints reais.
- [ ] Considerar **Cucumber** (`cucumber-junit-platform-engine`) ou **Karate** somente se houver pedido explícito de aceitação legível por negócio. Hoje o custo de manutenção dos *step definitions* não se justifica.

## Correções de comportamento / bugs

- [ ] `UserCollectionService.getCollectionById` usa `findById(id)` e **não valida o dono** da coleção — qualquer usuário autenticado poderia ler a coleção de outro. Existe `findByIdAndUserId` no repositório; usá-la.
- [ ] `ControllerExceptionHandler` cobre apenas `UserCollectionNotFoundException`, `UserAlreadyExistsException` e `UserNotAuthorizedException` — avaliar um handler global mais completo.
- [ ] Linha de tipo com espaço duplo gera token vazio (`Type` → token). Normalizar a entrada.
- [ ] DTO sem o campo `language` chega `null` em `CardService` e causa `NullPointerException` — validar/default.

## Arquitetura / Clean Code

Contexto: o projeto hoje é **layered-style, framework-coupled**, não Clean Architecture plena.

- [ ] Criar DTO de saída para `CardEntity` em vez de expor a entidade JPA.
- [ ] Explicitar portas e casos de uso.
- [ ] Separar domínio de JPA.
- [ ] Unificar `TypeService` / `SubtypeService` e os builders duplicados.
- [ ] Trocar `e.printStackTrace()` por logger (`CardEntityBuilder`, linha 54).
- [ ] Remover `TODO`s e código morto:
  - `LoginService` (linha 31): verificar sessão já logada.
  - `UserService` (linha 31): cadastrar perfil do usuário.
  - `UserCollectionService` (linhas 47 e 51): adicionar tag à carta/coleção.
  - `UserPasswordEntity` (linha 70): adicionar roles.
  - `UserCardService` (linhas 69 e 73): adicionar extras e tags.
  - `UserBuilder` (linha 20): ajustar roles.

## Upgrade (concluído — referência)

- [x] Spring Boot 4.2.0, Java 25 via toolchain, wrapper Gradle 9.9.2.
- [x] Starters modulares do Boot 4 (`webmvc`, `-test`); MVC mantido.
- [x] Remoção do Guava (`Strings.isNullOrEmpty` → `StringUtils.hasLength`) e do `HashTool` órfão.
- [x] Remoção da dependência direta de `commons-lang3` (`ToStringBuilder` → `@ToString` do Lombok).
- [x] Correção do `UserController.login` para passar a senha recebida.
- [x] Workflow de deploy no Heroku restrito a push na `main` via GitHub Actions.
