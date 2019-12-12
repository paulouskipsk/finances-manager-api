[![Build Status](https://travis-ci.org/paulouskipsk/finances-manager-api.svg?branch=master)](https://travis-ci.org/paulouskipsk/finances-manager-api)

# Finances Manager v 1.0

## Boas Práticas

- [x] Os Controllers devem invocar um ou mais Services.
- [x] Os Services devem invocar um ou mais repositórios.
- [x] Os Controllers devem receber e enviar DTOs.
- [x] Os Controllers devem ser responsáveis por converter DTOs em entidades e vice-versa.
- [x] Usar transação somente no Service.
- [x] Enviar respostas com formato (corpo) padronizado de sucesso ou erro.
- [x] Nomes de rotas com substantivos em inglês ou português.
- [x] Passar o id como parâmetro em operações de editar e remover.
- [x] Evitar atualização de dados com requisições POST.
- [ ] Evitar que um usuário possa atualizar ou remover dados de outro usuário.
- [x] Validação de DTOs com Bean Validation
- [x] Buscas normais e com paginação.
- [x] Testes unitários em repositórios.
- [x] Auditoria de classes de domínio.
- [x] Tratamento genérico (centralizado) de exceptions.
- [x] Tratamento de exceptions originados no Banco de Dados.

## Ferramentas
- [x] Gerar documentação da API com Swagger.
- [x] Usar lombok em DTOs e Entidades.
- [x] Realizar integração contínua com Travis CI.
- [x] Fazer o deploy automático no Heroku a partir do GitHub.
