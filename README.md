# to-do-list-ninaja

# Criação de uma API REST con Java Spring

### Foi criado a API e o deploy dela foi feito no Heroku.

#### Para isso utilizei as seguintes dependências:

- Spring Boot Data JPA (Crud com o banco de dados)
- Spring Boot Security (Segurança Login)
- JsonWebToken (Criar Token)
- Swagger (Documentar API)
- H2 (Banco de Datos)

**A docuemntação da API fiz usando o Swagger, e podem ver a documentação entrando no link (https://apptodolistninaja.herokuapp.com/). Esta é a versão da API com JWT, ou seja, precisamos cadastrar um usuario com nome, email e senha, e depois fazer o login com email e senha para gerar o TOKEN e poder usar a API.**

**A senha deve ter no mínimo 6 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais. O formato para se cadastrar com nome, email e senha é o seguinte:**

    {
      "nome" : "João da Silva,
      "email": "joao@gmail.com",
      "senha": "Joao123!"
    }


**O formato para fazer o login com email e senha é o seguinte:**

    {
      "email": "joao@gmail.com",
      "senha": "Joao123!"
    }

O Scripts no formato SQL estão na branch Develop
