Finances Manager v 1.0

BOAS PRÁTICAS

[x] Os Controllers devem invocar um ou mais Services.<br/>
[x] Os Services devem invocar um ou mais repositórios.<br/>
[x] Os Controllers devem receber e enviar DTOs.<br/>
[x] Os Controllers devem ser responsáveis por converter DTOs em entidades e vice-versa.<br/>
[x] Usar transação somente no Service.<br/>
[x] Enviar respostas com formato (corpo) padronizado de sucesso ou erro.<br/>
[x] Nomes de rotas com substantivos em inglês ou português.<br/>
[x] Passar o id como parâmetro em operações de editar e remover.<br/>
[x] Evitar atualização de dados com requisições POST.<br/>
[ ] Evitar que um usuário possa atualizar ou remover dados de outro usuário.<br/>
[x] Validação de DTOs com Bean Validation<br/>
[x] Buscas normais e com paginação.<br/>
[ ] Testes unitários em repositórios.<br/>
[x] Auditoria de classes de domínio.<br/>
[ ] Tratamento de genérico (centralizado) de exceptions.<br/>
[ ] Tratamento de exceptions originados no Banco de Dados.<br/>

FERRAMENTAS

[x] Gerar documentação da API com Swagger.<br/>
[x] Usar lombok em DTOs e Entidades.<br/>
[ ] Realizar integração contínua com Travis CI.<br/>
[ ] Fazer o deploy automático no Heroku a partir do GitHub.<br/>
