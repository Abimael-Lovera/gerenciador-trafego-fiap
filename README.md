# Gerenciador de trafego FIAP
## Índice
1. [Funcionalidades](#funcionalidades)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Pré-requisitos](#pré-requisitos)
4. [Estrutura de Diretórios](#estrutura-de-diretórios)
5. [Instalação](#instalação)
6. [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
7. [Executando o Projeto](#executando-o-projeto)
8. [Acessar Documentação da API](#acessar-documentação-da-api)
9. [Uso da Aplicação com Insomnia](#uso-da-aplicação-com-insomnia)
10. [Contribuição](#contribuição)
11. [Licença](#licença)

## Funcionalidades
- Registro e consulta de acidentes
- Registro e consulta de rotas
- Registro e consulta de semáforos
- Registro e consulta de condições climáticas

## Tecnologias Utilizadas
- Java 21
- Spring Boot
- Maven
- Docker
- PostgreSQL/MySQL

## Pré-requisitos

- Java 21
- Maven
- Insomnia ou outro cliente para requisições HTTP
- Banco de dados PostgreSQL ou MySQL
- Docker e Docker Compose (opcional caso não a instalado um banco de dados)

## Estrutura de Diretórios

O projeto possui a seguinte estrutura de diretórios:
<details>
<summary> Clique para ver a estrutura de diretórios </summary>

```
├── Dockerfile (DockerFile para construir a imagem da aplicação)
├── Gerenciador-FIAP.json (Collection para o Insomnia)
├── HELP.md
├── README.md
├── docker-compose.yml (Contem os serviços de banco de dados e app)
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── fiap
│   │   │           ├── MainApplication.java
│   │   │           ├── acidente
│   │   │           │   ├── controller
│   │   │           │   │   └── AcidenteController.java
│   │   │           │   ├── dto
│   │   │           │   │   ├── AcidenteCreateDTO.java
│   │   │           │   │   ├── AcidenteUpdateDTO.java
│   │   │           │   │   └── AcidenteViewDTO.java
│   │   │           │   ├── exception
│   │   │           │   │   └── AcidenteNaoEncontradoException.java
│   │   │           │   ├── model
│   │   │           │   │   ├── Acidente.java
│   │   │           │   │   └── Gravidade.java
│   │   │           │   ├── repository
│   │   │           │   │   └── AcidenteRepository.java
│   │   │           │   └── service
│   │   │           │       └── AcidenteService.java
│   │   │           ├── clima
│   │   │           ├── exceptions
│   │   │           │   ├── AppGerenciadorTrafegoException.java
│   │   │           │   └── ExceptionHandlerController.java
│   │   │           ├── rota
│   │   │           ├── semaforo
│   │   │           └── sensorTrafego
│   │   └── resources
│   │       ├── HttpRequest.http
│   │       ├── application-mysql.yml
│   │       ├── application-postgres.yml
│   │       ├── application.properties
│   │       ├── db
│   │       │   └── migration
│   │       │       ├── mysql
│   │       │       │   ├── V1__create_table-clima.sql
│   │       │       │   └── V2__create_table-gti_semaforo.sql
│   │       │       └── postgres
│   │       │           ├── V1__create_table-clima.sql
│   │       │           ├── V2__create_table-gti_semaforo.sql
│   │       │           ├── V3__create_table-reg-acidente.sql
│   │       │           ├── V4__create_table_t_gti_rota.sql
│   │       │           └── V5__create_table_t_gti_sensor_trafego.sql
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── fiap
│                   └── CrudApplicationTests.java
└── target
```


</details>

## Instalação

### Clonando o Repositório

1. Clone o repositório:
    ```sh
    git clone https://github.com/abimas/gerenciador-trafego-fiap.git
    ```
2. Navegue até o diretório do projeto:
3. Abra o projeto na IDE de sua preferência(IntelliJ, Eclipse, VSCode).

### Configuração do Banco de Dados

1. O projeto está configurado para utilizar um banco de dados PostgreSQL por padrão, mas pode ser alterado para MySQL.
   Para isso, é importante alterar o arquivo `application.properties` onde estão configurados os profiles para
   PostgreSQL e MySQL.

   Para PostgreSQL:
   ```properties
   spring.profiles.active=postgres
   ```

   Para MySQL:
   ```properties
   spring.profiles.active=mysql
   ```

2. Lembre-se de alterar o url, username e password do banco de dados no arquivo `application-<postgres.yml` ou
   `application-<mysql.yml`.

   Exemplo do arquivo `application-postgres.yml`:

   ```yml
    spring:
      config:
        activate:
          on-profile: postgres
      datasource:
        url: jdbc:postgresql://localhost:5432/database # url do banco de dados
        username: fiap # nome de usuário do banco de dados
        password: senhaFiap # senha do usuário do banco de dados
        driver-class-name: org.postgresql.Driver
    ```
3. (Opcional) Existe no projeto um arquivo docker-compose.yml configurado com o banco de dados PostgreSQL. Para
   utilizá-lo, basta executar o comando abaixo:
    ```sh
    docker-compose -f docker-compose.yml up postgres-db
    ```
   para MySQL:
   ```sh
   docker-compose -f docker-compose.yml up mysql-db
   ```

## Executando o Projeto

Para executar o projeto, basta seguir um dos passos abaixo:

1. Ir até a classe `MainApplication` e rodar no IntelliJ ou Eclipse.

2. (Opcional)Para executar o projeto com Docker Compose, basta executar o comando abaixo:
    ```sh
    docker-compose -f docker-compose.yml up app
    ```

### O projeto subirá no http://localhost:8080/api/v1

## Acessar documentação da API

A documentação da API está disponível em http://localhost:8080/doc/swagger-ui.html
![img.png](img-doc-swagger.png)
## Uso da aplicação com Insomnia
Dentro do projeto, há um arquivo Gerenciador-FIAP.json que contém a coleção de requisições HTTP que podem ser utilizadas.

## 1. Clima
### Criar Clima
```http
POST http://localhost:8080/api/v1/clima
Content-Type: application/json

{
    "dsCondicao": "Ensolarado",
    "nrTemperatura": 29.0,
    "nrUmidade": 65.0,
    "dtRegistro": "2024-10-30"
}
```
### Buscar todos os Climas
```http
GET http://localhost:8080/api/v1/clima
```

### Buscar Clima por Id
```http
GET http://localhost:8080/api/v1/clima/1
```
### Atualizar Clima
```http
PUT http://localhost:8080/api/v1/clima/1
Content-Type: application/json

{
    "dsCondicao": "Chuvoso",
    "nrTemperatura": 22.0,
    "nrUmidade": 85.0,
    "dtRegistro": "2024-10-25"
}
```
## 2. Semaforos

Buscar Todos
```http
GET http://localhost:8080/api/v1/semaforos
```

Buscar por Id
```http
GET http://localhost:8080/api/v1/semaforos/1
```

Criar
```http
POST http://localhost:8080/api/v1/semaforos
Content-Type: application/json

{
    "dsLocalizacao": "Avenida Paulista, SP",
    "dsEstado": "verde",
    "dtUltAtualizacao": "2024-10-31",
    "climaId": 3
}
```

Atualizar
```http
PUT http://localhost:8080/api/v1/semaforos/1
Content-Type: application/json

{
    "dsLocalizacao": "Avenida Paulista, SP - Atualizando",
    "dsEstado": "amarelo",
    "dtUltAtualizacao": "2024-11-10",
    "climaId": 3
}
```

Excluir
```http
DELETE http://localhost:8080/api/v1/semaforos/1
```

## 3. Acidentes
### Buscar Todos
```http
GET http://localhost:8080/api/v1/acidentes
```

### Buscar por Id
```http
GET http://localhost:8080/api/v1/acidentes/1
```

### Criar
```http
POST http://localhost:8080/api/v1/acidentes
Content-Type: application/json

{
    "localAcidente": "Avenida Paulista, São Paulo",
    "dataAcidente": "2024-10-31",
    "gravidade": "LEVE",
    "nrFluxoImpactado": 200,
    "semaforoId": 1
}
```

### Atualizar
```http
PUT http://localhost:8080/api/v1/acidentes/1
Content-Type: application/json

{
    "localAcidente": "Avenida Paulista, São Paulo - Teste",
    "dataAcidente": "2024-10-31",
    "gravidade": "MODERADO",
    "nrFluxoImpactado": 200,
    "semaforoId": 1
}
```

### Excluir
```http
DELETE http://localhost:8080/api/v1/acidentes/1
```

## 4. Rotas

### Buscar Todos
```http
GET http://localhost:8080/api/v1/rotas
```

### Buscar por Id
```http
GET http://localhost:8080/api/v1/rotas/8
```

### Criar
```http
POST http://localhost:8080/api/v1/rotas
Content-Type: application/json

{
    "descricaoRota": "Avenida Paulista - Teste remover",
    "status": "FECHADA",
    "acidenteId": 3
}
```

### Atualizar
```http
PUT http://localhost:8080/api/v1/rotas/8
Content-Type: application/json

{
    "descricaoRota": "Avenida Paulista - Teste remover",
    "status": "FECHADA",
    "acidenteId": 2
}
```

### Excluir
```http
DELETE http://localhost:8080/api/v1/rotas/7
```

## 5. SensorTrafego
### Buscar Todos
```http
GET http://localhost:8080/api/v1/sensorTrafego
```

### Buscar por Id
```http
GET http://localhost:8080/api/v1/sensorTrafego/9
```

### Criar
```http
POST http://localhost:8080/api/v1/sensorTrafego
Content-Type: application/json

{
    "qtFluxoVeiculos": 24,
    "nrVisibilidade": 50,
    "dtDeteccao": "2024-11-12",
    "semaforoId": 1
}
```

### Atualizar
```http
PUT http://localhost:8080/api/v1/sensorTrafego/9
Content-Type: application/json

{
    "qtFluxoVeiculos": 30,
    "nrVisibilidade": 15,
    "dtDeteccao": "2024-10-31",
    "semaforoId": 1 
}
```

### Excluir
```http
DELETE http://localhost:8080/api/v1/sensorTrafego/10
```

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.

1. Faça um fork do projeto.
2. Crie uma nova branch (`git checkout -b feature/nova-funcionalidade`).
3. Faça suas alterações e commit (`git commit -m 'Adiciona nova funcionalidade'`).
4. Envie para o repositório remoto (`git push origin feature/nova-funcionalidade`).
5. Abra um pull request.