# language: pt
Funcionalidade: Gerenciamento de registros climáticos - Criar e Consultar
  Como um usuário do sistema
  Eu quero poder cadastrar e consultar informações climáticas
  Para que possa ter um registro histórico das condições meteorológicas

  Cenário: Criar um novo registro climático com sucesso
    Quando eu enviar uma requisição POST para "/clima" com os dados:
      | dsCondicao    | Ensolarado |
      | nrTemperatura |       28.5 |
      | nrUmidade     |       65.0 |
      | dtRegistro    | 2025-05-04 |
    Então o código de status da resposta deve ser 201
    E a resposta deve conter um objeto clima com os mesmos dados enviados:
      | dsCondicao    | Ensolarado |
      | nrTemperatura |       28.5 |
      | nrUmidade     |       65.0 |
      | dtRegistro    | 2025-05-04 |
    E o objeto clima deve conter um campo "idClima" não nulo
    E a resposta deve estar de acordo com o JSON Schema "clima_schema.json"

  Cenário: Buscar todos os registros climáticos paginados
    Dado que existem registros climáticos cadastrados
    Quando eu enviar uma requisição GET para "/clima"
    Então o código de status da resposta deve ser 200
    E a resposta deve conter uma lista paginada de climas
    E a resposta deve estar de acordo com o JSON Schema "clima_page_schema.json"

  Cenário: Buscar um registro climático por ID com sucesso
    Dado que existe um registro climático com ID 1
    Quando eu enviar uma requisição GET para "/clima/1"
    Então o código de status da resposta deve ser 200
    E a resposta deve conter um objeto clima com ID 1
    E a resposta deve estar de acordo com o JSON Schema "clima_schema.json"

  Cenário: Buscar um registro climático por ID inexistente
    Quando eu enviar uma requisição GET para "/clima/9999"
    Então o código de status da resposta deve ser 404
    E a resposta deve estar de acordo com o JSON Schema "error_schema.json"
    E a resposta deve conter uma mensagem de erro informando que o clima não foi encontrado
