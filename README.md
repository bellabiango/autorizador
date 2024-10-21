# Autorizador - Caju
Projeto do processo seletivo da empresa Caju.

## Descrição
Esta aplicação é um serviço de autorização de transações financeiras desenvolvido em Java com Spring Boot. Ela utiliza MongoDB como banco de dados. 
O projeto também está configurado para ser executado no GitHub Codespaces.

A aplicação fornece endpoints REST para processar transações e gerenciar saldos de contas.

### **Endpoints Principais**

- `POST /transactions`: Processa uma nova transação.
- `GET /transactions/account/{accountId}`: Obtém as transações de uma conta específica.
- `POST /accounts`: Cria um novo saldo de conta.
- `GET /accounts`: Lista todos os saldos de contas.

## Pré-requisitos
- Java 21 ou superior
- Gradle
- MongoDB Atlas ou local
- Conta no GitHub para usar Codespaces


## Usando o GitHub Codespaces
Você pode desenvolver e executar esta aplicação diretamente no GitHub Codespaces:

No repositório GitHub, clique em "Code" e selecione "Codespaces".
Crie um novo Codespace ou abra um existente.
O ambiente já estará configurado com todas as dependências necessárias.

### Configuração de Variáveis de Ambiente
Certifique-se de que as seguintes variáveis de ambiente estão configuradas:
 - MONGODB_URI: String de conexão com o MongoDB.

### Usando o Gradle
No terminal, execute:

```bash
./gradlew bootRun
```
A aplicação estará disponível em http://localhost:8080.

### Usando o Codespaces
No Codespace, a porta será encaminhada automaticamente. Você pode acessar a aplicação clicando no link fornecido pelo Codespaces quando a aplicação estiver em execução.

A documentação da API está disponível através do Swagger UI: http://localhost:8080/swagger-ui/index.html




