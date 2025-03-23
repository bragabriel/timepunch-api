# Controle de Ponto - Web API

Este projeto implementa uma API RESTful para o controle de ponto de funcionários.

## Funcionalidades

## O projeto contempla:

- [x] **Contrato Open API**: Seguindo o formato de mensagens de input, response e erros.

- [x] **Java**: Versão 21.

- [x] **Framework**: Spring 3.4.3.

- [x] **Banco de Dados em Memória**: H2.

- [x] **Docker**: A aplicação está configurada para rodar via Docker. Para rodar, veja a sessão:  "[como rodar a 
aplicação](#como-rodar-a-aplicação)"

- [x] **Testes**:
  - Unitários: jUnit5
  - xpto: 

- [x] **GitHub Actions**: A integração contínua garantindo que cada PR aberto para a branch main passe por validação 
automatizada, que roda:
  - build
  - testes
  - checkstyle

- [x] **Branch main protegida**: A branch main é bloqueada para commits diretos, garantindo que todos os commits 
 sejam feitos via pull request, que passa por validação no GitHub Actions.

## Como rodar a aplicação

1. **Via Docker (recomendado)**:

   Clone o repositório e rode o comando abaixo:

   ```bash
   docker-compose up --build
    ```
