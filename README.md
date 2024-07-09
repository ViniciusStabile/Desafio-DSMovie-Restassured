# DSMovie RestAssured API Testing Challenge

## Sobre o Projeto DSMovie

O DSMovie é um projeto que permite a visualização e avaliação de filmes. A visualização dos dados dos filmes é pública e não requer login, enquanto as operações de alteração (inserção, atualização, deleção) são restritas a usuários com perfil ADMIN. As avaliações de filmes podem ser registradas por qualquer usuário logado (CLIENT ou ADMIN). Cada avaliação é armazenada na entidade `Score`, que registra uma nota de 0 a 5. Sempre que um usuário registra uma nova nota, o sistema recalcula a média das notas e atualiza a entidade `Movie` com a nova média (`score`) e a contagem de votos (`count`).

## Desafio

O desafio proposto pelo curso era fazer todos os testes com restAssured, e para aprovação no minimo 8 de 10 testes certos.

## Requisitos dos Testes de API

### MovieControllerRA

1. `findAllShouldReturnOkWhenMovieNoArgumentsGiven`
   - Verifica se a requisição sem parâmetros para a lista de filmes retorna status 200 (OK).

2. `findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty`
   - Verifica se a requisição com parâmetro de título de filme não vazio retorna uma lista paginada de filmes.

3. `findByIdShouldReturnMovieWhenIdExists`
   - Verifica se a requisição para buscar um filme por ID existente retorna o filme correto.

4. `findByIdShouldReturnNotFoundWhenIdDoesNotExist`
   - Verifica se a requisição para buscar um filme por ID inexistente retorna status 404 (Not Found).

5. `insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle`
   - Verifica se a tentativa de inserir um filme com título em branco, por um usuário ADMIN logado, retorna status 422 (Unprocessable Entity).

6. `insertShouldReturnForbiddenWhenClientLogged`
   - Verifica se a tentativa de inserção de um filme por um usuário CLIENT logado retorna status 403 (Forbidden).

7. `insertShouldReturnUnauthorizedWhenInvalidToken`
   - Verifica se a tentativa de inserção de um filme com um token inválido retorna status 401 (Unauthorized).

### ScoreControllerRA

8. `saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist`
   - Verifica se a tentativa de salvar uma nota para um filme com ID inexistente retorna status 404 (Not Found).

9. `saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId`
   - Verifica se a tentativa de salvar uma nota sem especificar o ID do filme retorna status 422 (Unprocessable Entity).

10. `saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero`
    - Verifica se a tentativa de salvar uma nota menor que zero retorna status 422 (Unprocessable Entity).

## Como Executar os Testes

Para executar os testes, siga os passos abaixo:

1. Clone o repositório do projeto DSMovie.
2. Importe o projeto em sua IDE preferida (por exemplo, IntelliJ ou Eclipse).
3. Certifique-se de ter o Maven instalado e configurado em seu ambiente.
4. No terminal, navegue até o diretório raiz do projeto.
5. Execute o comando `mvn clean test` para compilar e rodar todos os testes.

## Estrutura do Projeto

- **Controller**: Contém os endpoints da API.
- **Service**: Contém a lógica de negócios.
- **Repository**: Interage com o banco de dados.
- **Entity**: Define as entidades do projeto.
- **DTO**: Define os Data Transfer Objects.
- **Test**: Contém os testes implementados com RestAssured.

## Conclusão

A implementação dos testes de API com RestAssured garante a qualidade e a confiabilidade das funcionalidades da API DSMovie.

---

Eu implementei este desafio como parte de meu aprendizado e prática no uso do RestAssured para testes de APIs. Os testes cobrem diversos cenários importantes, garantindo que as funcionalidades essenciais do DSMovie estejam corretas e seguras. 

