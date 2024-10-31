# Gerenciador de trafego FIAP

## Execeção do projeto

### Pré-requisitos
- Java 21
- Maven
- Docker

### Executando o projeto

1. Clone o repositório
```bash
git clone https://github.com/abimas/gerenciador-trafego-fiap.git
```
2. O projeto tem a seguinte estrutura de diretórios:

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
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── fiap
│   │   │           ├── MainApplication.java
│   │   └── resources
│   │       ├── application-mysql.yml
│   │       ├── application-postgres.yml
│   │       ├── application.properties
│   │       ├── db
│   │       │   └── migration
│   │       │       ├── mysql
│   │       │           ├── V1__create_table-clima.sql
│   │       │           └── V2__create_table-gti_semaforo.sql
│   │       │       └── postgres
│   │       │           ├── V1__create_table-clima.sql
│   │       │           └── V2__create_table-gti_semaforo.sql

```

3. Iniciar o banco de dados com docker compose:
```sh
docker-compose -f docker-compose.yml up postgres-db
```
4. Iniciar o projeto com docker compose:
```sh
docker-compose -f docker-compose.yml up app
```
OBS: O projeto está configurado que utilizar um banco de dado PostgreSQL, mas pode ser alterado para MySQL. Para isso é importante alterar o arquivo application.properties. onde está configurado os profiles para postgres e mysql.

```properties
spring.profiles.active=postgres
```
ou 
```properties
spring.profiles.active=mysql
```

## Outra forma de executar o projeto
1. Dependendo de qual banco de dados vc for utilizar, deverá de alterar o arquivo application-<db>.yml
Alterar o campo url username e password apontando paa o banco de dados que você está utilizando.
```yml
spring:
  config:
    activate:
      on-profile: postgres
  datasource:
    url: jdbc:postgresql://localhost:5432/database
    username: fiap
    password: senhaFiap
    driver-class-name: org.postgresql.Driver
```
2.Ir atá classe MainApplication e rodar no intellij ou eclipse

## Para testar o projeto utilize a collection Gerenciador-FIAP.json
1. Criar clima exemplo
```json
{
    "dsCondicao": "Ensolarado",
    "nrTemperatura": 29.0,
    "nrUmidade": 65.0,
    "dtRegistro": "2024-10-30"
}
```

```json
{
    "dsCondicao": "Chuvoso",
    "nrTemperatura": 22.0,
    "nrUmidade": 85.0,
    "dtRegistro": "2024-10-25"
}
```
```json
{
    "dsCondicao": "Nublado",
    "nrTemperatura": 18.0,
    "nrUmidade": 70.0,
    "dtRegistro": "2024-10-24"
}
```
2. Exemplo para criar semaforo
```json
{
    "dsLocalizacao": "Avenida Paulista, SP",
    "dsEstado": "verde",
    "dtUltAtualizacao": "2024-10-31",
    "climaId": 3
}
```
