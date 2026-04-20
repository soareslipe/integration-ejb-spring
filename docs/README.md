# 📦 Projeto Multi-Módulo (EJB + Spring Boot + Angular)

Este projeto é uma aplicação full stack composta por três módulos integrados:

- **EJB Module**: responsável pela lógica de negócio e acesso ao banco de dados (executado no WildFly)
- **Backend Module (Spring Boot)**: responsável por expor APIs REST e integrar com o EJB
- **Frontend (Angular)**: interface web para consumo das APIs

---

## 🧱 Arquitetura do sistema


Frontend (Angular)
↓
Backend (Spring Boot)
↓
EJB Module (WildFly)
↓
Banco de Dados (H2)


---

## ⚙️ Tecnologias utilizadas

- Java 17
- Jakarta EE (EJB)
- WildFly
- Spring Boot
- Maven (multi-module)
- H2 Database
- Angular
- Angular Material
- Swagger (OpenAPI)

---

## 🚀 Como executar o projeto

### 1. EJB Module (WildFly)

O módulo EJB deve ser executado dentro do **WildFly**.

#### Deploy:
- Gere o build do módulo:
```bash
mvn clean install
Faça deploy do artefato no WildFly:
Copie o .jar/.war para standalone/deployments
Ou utilize o CLI do WildFly
Iniciar servidor:
./standalone.sh
2. Backend Module (Spring Boot)

O backend é executado com Spring Boot.

Rodar aplicação:
mvn spring-boot:run
URL padrão:
http://localhost:8080
📄 Swagger (API Docs)

A documentação da API está disponível via Swagger:

http://localhost:8080/swagger-ui/index.html
3. Frontend (Angular)
Instalar dependências:
npm install
Rodar aplicação:
ng serve
Acesso:
http://localhost:4200
🔗 Fluxo de comunicação
Angular consome a API do Spring Boot
Spring Boot consome os serviços do EJB Module
EJB Module executa a lógica de negócio e acesso ao banco de dados
🗄️ Banco de dados (H2)

O projeto utiliza H2 Database em ambiente de desenvolvimento.

Inicialização automática:
schema.sql → estrutura das tabelas
data.sql → dados iniciais

Esses arquivos são carregados automaticamente na inicialização do EJB Module.

🔐 Configurações importantes
CORS (Backend)

O backend possui configuração de CORS para permitir comunicação com o frontend Angular.

EJB Remote

O módulo EJB expõe interfaces remotas para comunicação com o backend Spring Boot.

📌 Funcionalidades implementadas
CRUD completo de Benefício
Transferência de valores entre entidades
Controle de versão de registros
Integração entre EJB e Spring Boot
Interface Angular com Angular Material
Swagger para documentação da API
Testes manuais via Postman
Testes unitários com Mockito no EJB Module
🧪 Testes
Testes unitários no EJB Module utilizando Mockito
Testes de integração via Postman
Validação de endpoints REST
Verificação de consistência de versionamento
📌 Observações
O projeto foi estruturado em arquitetura multi-módulo
O WildFly é obrigatório para execução do EJB Module
O Spring Boot atua como camada intermediária de API
O Angular consome exclusivamente o backend Spring
O H2 é utilizado apenas para ambiente de desenvolvimento