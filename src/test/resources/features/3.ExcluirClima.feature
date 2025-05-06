# language: pt
Funcionalidade: Gerenciamento de registros climáticos - Excluir
  Como um usuário do sistema
  Eu quero poder excluir informações climáticas
  Para que possa remover registros desnecessários ou incorretos

  Cenário: Excluir um registro climático com sucesso
    Dado que existe um registro climático com ID 1
    Quando eu enviar uma requisição DELETE para "/clima/1"
    Então o código de status da resposta deve ser 200
    E a resposta deve conter o objeto clima que foi excluído
    E a resposta deve estar de acordo com o JSON Schema "clima_schema.json"

  Cenário: Verificar que o registro foi excluído
    Dado que o registro climático com ID 1 foi excluído
    Quando eu enviar uma requisição GET para "/clima/1"
    Então o código de status da resposta deve ser 404
    E a resposta deve estar de acordo com o JSON Schema "error_schema.json"
    E a resposta deve conter uma mensagem de erro informando que o clima não foi encontrado

  Cenário: Tentar excluir um registro climático inexistente
    Quando eu enviar uma requisição DELETE para "/clima/999"
    Então o código de status da resposta deve ser 404
    E a resposta deve estar de acordo com o JSON Schema "error_schema.json"
    E a resposta deve conter uma mensagem de erro informando que o clima não foi encontrado
