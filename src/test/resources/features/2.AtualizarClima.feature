# language: pt
Funcionalidade: Gerenciamento de registros climáticos - Atualizar
  Como um usuário do sistema
  Eu quero poder atualizar informações climáticas
  Para que possa corrigir ou complementar os dados meteorológicos

  Cenário: Atualizar um registro climático com sucesso
    Dado que existe um registro climático com ID 1
    Quando eu enviar uma requisição PUT para "/clima/1" com os dados:
      | dsCondicao    | Chuvoso    |
      | nrTemperatura |       22.0 |
      | nrUmidade     |       90.0 |
      | dtRegistro    | 2025-05-05 |
    Então o código de status da resposta deve ser 200
    E a resposta deve conter um objeto clima com ID 1
    E a resposta deve conter um objeto clima com os dados atualizados:
        | dsCondicao    | Chuvoso    |
        | nrTemperatura |       22.0 |
        | nrUmidade     |       90.0 |
        | dtRegistro    | 2025-05-05 |
    E a resposta deve estar de acordo com o JSON Schema "clima_schema.json"

  Cenário: Tentar atualizar um registro climático inexistente
    Quando eu enviar uma requisição PUT para "/clima/999" com os dados:
      | dsCondicao    | Chuvoso    |
      | nrTemperatura |       22.0 |
      | nrUmidade     |       90.0 |
      | dtRegistro    | 2025-05-05 |
    Então o código de status da resposta deve ser 404
    E a resposta deve estar de acordo com o JSON Schema "error_schema.json"
    E a resposta deve conter uma mensagem de erro informando que o clima não foi encontrado

  Cenário: Tentar atualizar um registro climático com dados inválidos
    Dado que existe um registro climático com ID 1
    Quando eu enviar uma requisição PUT para "/clima/1" com os dados:
      | dsCondicao    |            |
      | nrTemperatura |       22.0 |
      | nrUmidade     |       90.0 |
      | dtRegistro    | 2025-05-05 |
    Então o código de status da resposta deve ser 400
    E a resposta deve estar de acordo com o JSON Schema "validation_error_schema.json"
    E a resposta deve conter erros de validação
