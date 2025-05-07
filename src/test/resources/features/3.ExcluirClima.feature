# language: pt
Funcionalidade: Gerenciamento de registros climáticos - Excluir
  Como um usuário do sistema
  Eu quero poder excluir informações climáticas
  Para que possa remover registros desnecessários ou incorretos

  Cenário: Excluir um registro climático com sucesso
    Quando eu enviar uma requisição POST para "/clima" com os dados:
      | dsCondicao    | Clima para exclusão |
      | nrTemperatura |                22.5 |
      | nrUmidade     |                75.0 |
      | dtRegistro    |          2025-05-06 |
    E eu enviar uma requisição DELETE para "/clima/{idClima}"
    Então o código de status da resposta deve ser 200
    E a resposta deve conter o objeto clima que foi excluído
    E a resposta deve estar de acordo com o JSON Schema "clima_schema.json"

  Cenário: Verificar que o registro foi excluído
    Quando eu enviar uma requisição POST para "/clima" com os dados:
      | dsCondicao    | Clima para exclusão |
      | nrTemperatura |                22.5 |
      | nrUmidade     |                75.0 |
      | dtRegistro    |          2025-05-06 |
    E eu enviar uma requisição DELETE para "/clima/{idClima}"
    E eu enviar uma requisição GET para "/clima/{idClima}"
    Então o código de status da resposta deve ser 404
    E a resposta deve estar de acordo com o JSON Schema "error_schema.json"
    E a resposta deve conter uma mensagem de erro informando que o clima não foi encontrado

  Cenário: Tentar excluir um registro climático inexistente
    Quando eu enviar uma requisição DELETE para "/clima/999999"
    Então o código de status da resposta deve ser 404
    E a resposta deve estar de acordo com o JSON Schema "error_schema.json"
    E a resposta deve conter uma mensagem de erro informando que o clima não foi encontrado
