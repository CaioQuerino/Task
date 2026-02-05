# Documentação do Projeto Task

> *Documentação atualizada em: 05/02/2026 14:38*

## 📋 Descrição do Projeto
Este projeto é uma aplicação Spring Boot configurada para rodar em contêineres Docker, facilitando o desenvolvimento e a implantação.

### 👤 Autor
**Nome:** Caio Querino

**Data da Documentação:** 01 de Fevereiro de 2026

## 🔧 Correções Realizadas
Recentemente, corrigimos um erro de 'NoClassDefFoundError' que impedia a execução da aplicação.

1. **Ajuste no 'pom.xml'**: Adicionamos o 'spring-boot-starter-parent' e o 'spring-boot-maven-plugin'.
2. **Dockerfile Otimizado**: O Dockerfile agora realiza o build completo.

## 🚀 Como Executar o Projeto
### ✅ Pré-requisitos
* Docker instalado
* Docker Compose instalado

### 📝 Passo a Passo
1. **Configure o Ambiente**: Edite o arquivo '.env' se necessário.
2. **Suba os Contêineres**: Execute: docker compose up --build -d
3. **Acesse os Serviços**:
- Aplicação: http://localhost:8080
- phpMyAdmin: http://localhost:8081

## 📁 Estrutura de Configuração

## 🔐 Dados de Acesso (Padrão)
| Serviço | Usuário | Senha | Porta |
|---------|---------|-------|-------|
| Serviço | Usuário | Senha | Porta |
| MySQL | admin | admin@123 | 3306 |
| phpMyAdmin | - | root@123 | 8081 |
| App API | - | - | 8080 |

## 🗄️ Configurações do Banco de Dados (MySQL)
```properties
DB_HOST = localhost
DB_PORT = 3306
DB_NAME = task_db
DB_USER = admin
DB_PASSWORD = admin@123
DB_ROOT_PASSWORD = root@123
PHPMYADMIN_PORT = 8081
APP_PORT = 8080
SPRING_PROFILES_ACTIVE = prod
```

## ⚙️ Arquivos de Configuração
### application.properties
```properties
# Configurações do Servidor
server.port = ${APP_PORT}
server.ssl.enabled = false

# Configurações do Banco de Dados
spring.datasource.url = jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC        
spring.datasource.username = ${DB_USER}
spring.datasource.password = ${DB_PASSWORD}
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver

# Configurações do JPA / Hibernate
spring.jpa.hibernate.ddl-auto = update
spring.jpa.show-sql = true
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
spring.jpa.open-in-view = false

# Configurações de JSON
spring.jackson.serialization.fail-on-empty-beans = false

# Desativa SSL/HTTPS forçado
server.http2.enabled = false
```

### docker-compose.yml
```yaml
version: '3.8'
services:
  db:
    image: mysql:8.4.0
    container_name: task-mysql
    restart: always
    environment:
      MYSQL_DATABASE: ${DB_NAME}
      MYSQL_USER: ${DB_USER}
      MYSQL_PASSWORD: ${DB_PASSWORD}
      MYSQL_ROOT_PASSWORD: ${DB_ROOT_PASSWORD}
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  phpmyadmin:
    image: phpmyadmin/phpmyadmin
    container_name: task-phpmyadmin
    restart: always
    environment:
      PMA_HOST: db
      PMA_PORT: 3306
    ports:
      - "${PHPMYADMIN_PORT}:80"
    depends_on:
      - db

  app:
    build: .
    container_name: task-app
    restart: always
    environment:
      DB_HOST: db
      DB_PORT: 3306
      DB_NAME: ${DB_NAME}
      DB_USER: ${DB_USER}
      DB_PASSWORD: ${DB_PASSWORD}
    ports:
      - "${APP_PORT}:8080"
    depends_on:
      - db

volumes:
  mysql_data:
```

### Dockerfile
```dockerfile
# Build stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 🌳 Estrutura do Projeto
```
task-application/
├── src/main/java/com/r2m/praticar/taskapplication/
│   ├── configs/           # Configurações (Segurança, CORS, etc.)
│   ├── controllers/       # Controladores REST
│   ├── dto/              # Data Transfer Objects
│   ├── enums/            # Enumeradores
│   ├── exceptions/       # Exceções customizadas
│   ├── models/           # Entidades JPA
│   ├── repositories/     # Repositórios Spring Data JPA
│   ├── services/         # Lógica de negócio
│   └── TaskApplication.java
├── src/main/resources/
│   └── application.properties
├── .env                  # Variáveis de ambiente
├── .gitignore
├── docker-compose.yml    # Orquestração Docker
├── Dockerfile            # Build da aplicação
├── pom.xml               # Dependências Maven
└── README.md
```

## 🔗 Endpoints da API
### 👥 Clientes
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/clients/register` | Registrar novo cliente |
| DELETE | `/api/clients/delete/{id}` | Excluir cliente |
| PUT | `/api/clients/{id}/activate` | Ativar cliente |
| GET | `/api/clients/{id}` | Buscar cliente por ID |
| GET | `/api/clients/health` | Health check do serviço |

### ✅ Tarefas
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/tasks/add` | Adicionar nova tarefa |
| GET | `/api/tasks/list` | Listar todas as tarefas |
| PUT | `/api/tasks/update/{id}` | Atualizar tarefa |
| DELETE | `/api/tasks/delete/{id}` | Excluir tarefa |
| GET | `/api/tasks/{id}` | Buscar tarefa por ID |

### 🧪 Testes
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/test/hello` | Teste básico da API |
| GET | `/test/check-db` | Verificar conexão com banco |

## 💻 Comandos Úteis
```bash
# Build e execução com Docker Compose
docker-compose up --build -d

# Parar containers
docker-compose down

# Ver logs da aplicação
docker logs task-app -f

# Ver logs do MySQL
docker logs task-mysql -f

# Acessar shell do container da aplicação
docker exec -it task-app sh

# Acessar MySQL via linha de comando
docker exec -it task-mysql mysql -u admin -padmin@123 task_db

# Rebuild da aplicação apenas
docker-compose build app

# Limpar recursos Docker não utilizados
docker system prune -a
```

## 🌐 Links de Acesso
| Serviço | URL | Descrição |
|---------|-----|-----------|
| Aplicação | http://localhost:8080 | API Spring Boot |
| phpMyAdmin | http://localhost:8081 | Interface web do MySQL |
| Health Check | http://localhost:8080/api/clients/health | Verificar saúde da API |
| Teste API | http://localhost:8080/test/hello | Endpoint de teste |

## 🔧 Solução de Problemas Comuns
### ❌ Erro: 'NoClassDefFoundError'
**Causa:** Dependências não estão sendo empacotadas corretamente no JAR.
**Solução:** Verifique se o `spring-boot-maven-plugin` está configurado no `pom.xml` para gerar um Fat JAR.

### ❌ Erro: Conexão recusada com o MySQL
**Causa:** O container MySQL ainda não está pronto ou as credenciais estão incorretas.
**Solução:** Aguarde alguns segundos após iniciar os containers. Verifique as credenciais no arquivo `.env`.

### ❌ Erro: Porta já em uso
**Causa:** Outro serviço está usando as portas 8080 ou 8081.
**Solução:** Altere as portas no arquivo `.env` ou pare o serviço conflitante.

### ❌ Erro: Aplicação não inicia
**Causa:** Dependências faltando ou configuração incorreta.
**Solução:** Execute `docker-compose build --no-cache` para rebuild sem cache.