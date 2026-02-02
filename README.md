# Documentação do Projeto Task

Este projeto é uma aplicação Spring Boot configurada para rodar em contêineres Docker, facilitando o desenvolvimento e a implantação.

## 🛠️ Correções Realizadas

Recentemente, corrigimos um erro de `NoClassDefFoundError` que impedia a execução da aplicação. As seguintes alterações foram feitas:
1.  **Ajuste no `pom.xml`**: Adicionamos o `spring-boot-starter-parent` e o `spring-boot-maven-plugin` para garantir que o Maven gere um "Fat JAR" (um arquivo JAR contendo todas as dependências necessárias).
2.  **Dockerfile Otimizado**: O Dockerfile agora realiza o build completo da aplicação usando Maven dentro de um contêiner temporário, garantindo que o ambiente de build seja idêntico para todos.

## 🚀 Como Executar

### Pré-requisitos
*   Docker instalado
*   Docker Compose instalado

### Passo a Passo

1.  **Configure o Ambiente**:
    O arquivo `.env` na raiz contém as configurações de banco de dados e portas. Você pode editá-lo se necessário.

2.  **Suba os Contêineres**:
    No terminal, dentro da pasta do projeto, execute:
    ```bash
    docker compose up --build -d
    ```

3.  **Acesse os Serviços**:
    *   **Aplicação**: `http://localhost:8080`
    *   **phpMyAdmin**: `http://localhost:8081` (Para gerenciar o banco de dados visualmente)

## 📁 Estrutura de Configuração

*   `.env`: Variáveis de ambiente (senhas, usuários, portas).
*   `docker-compose.yml`: Definição dos serviços (App, MySQL, phpMyAdmin).
*   `application.properties`: Configurações do Spring Boot integradas com o Docker.
*   `Dockerfile`: Instruções para criar a imagem da aplicação.

## 🗄️ Dados de Acesso (Padrão)

| Serviço | Usuário | Senha | Porta |
| :--- | :--- | :--- | :--- |
| **MySQL** | `admin` | `admin@123` | `3306` |
| **phpMyAdmin** | - | `root@123` (Root) | `8081` |
| **App API** | - | - | `8080` |

---
**Autor**: Caio Querino
**Data**: 01 de Fevereiro de 2026