Contribuindo para o Projeto Task Application 🚀

Obrigado pelo seu interesse em contribuir com o projeto Task Application! Este é um projeto de código aberto e apreciamos todas as contribuições, sejam elas grandes ou pequenas.

📋 Antes de Começar

Pré-requisitos

· Conhecimento básico de Git e GitHub
· Docker e Docker Compose instalados (para testar localmente)
· Java 17+ e Maven (opcional, mas recomendado para desenvolvimento)
· Um editor de código (VS Code, IntelliJ IDEA, Eclipse, etc.)

Código de Conduta

Ao participar deste projeto, você concorda em seguir nosso Código de Conduta. Por favor, leia-o antes de contribuir.

🛠️ Processo de Contribuição

1. Fork do Repositório

1. Acesse o repositório principal
2. Clique no botão "Fork" no canto superior direito
3. Isso criará uma cópia do projeto na sua conta GitHub

2. Clone Localmente

```bash
git clone https://github.com/seu-usuario/task-application.git
cd task-application
```

3. Configurar Remote Original

```bash
git remote add upstream https://github.com/original-owner/task-application.git
```

4. Criar um Branch

NUNCA trabalhe diretamente na branch main. Sempre crie uma nova branch:

```bash
# Padrão recomendado: username-feature-description
git checkout -b seu-usuario-adicionar-endpoint-usuario
# ou
git checkout -b seu-usuario-correcao-bug-autenticacao
# ou
git checkout -b seu-usuario-melhoria-documentacao
```

Padrão de nomenclatura:

· usernameGitHub

5. Ambiente de Desenvolvimento

Configurar Ambiente Local

```bash
# 1. Copie o arquivo de ambiente
cp .env.example .env

# 2. Suba os containers
docker-compose up -d --build

# 3. Verifique se tudo está funcionando
docker-compose ps
```

Executar Testes

```bash
# Testes unitários
mvn test

# Testes de integração
mvn verify

# Executar aplicação localmente
mvn spring-boot:run
```

6. Faça Suas Modificações

· Siga as Diretrizes de Código
· Escreva testes para novas funcionalidades
· Atualize a documentação quando necessário

7. Commit das Alterações

```bash
# Adicione as mudanças
git add .

# Faça commit com mensagem descritiva
git commit -m "feat: adiciona endpoint de busca de usuários por email

- Adiciona novo endpoint GET /api/users/search
- Implementa validação de email
- Adiciona testes unitários
- Atualiza documentação da API

Closes #123"
```

Padrão de Mensagens de Commit

Use Conventional Commits:

· feat: Nova funcionalidade
· fix: Correção de bug
· docs: Documentação
· style: Formatação, ponto e vírgula, etc. (não altera código)
· refactor: Refatoração de código
· test: Adição ou correção de testes
· chore: Atualização de tarefas, configurações, etc.

8. Sincronize com o Repositório Original

```bash
# Atualize sua branch com as últimas mudanças do upstream
git fetch upstream
git rebase upstream/main

# Resolva conflitos se necessário
# ... resolva os conflitos ...
git add .
git rebase --continue
```

9. Push para Seu Fork

```bash
git push origin seu-branch
```

10. Criar Pull Request

1. Vá para o seu fork no GitHub
2. Clique em "Compare & pull request"
3. Preencha o template do PR:
   · Descreva as mudanças
   · Link para issues relacionadas
   · Screenshots (se aplicável)
   · Checklist de verificação

✅ Checklist do Pull Request

Antes de submeter seu PR, verifique:

· Meu código segue as diretrizes de estilo do projeto
· Executei os testes localmente e todos passaram
· Adicionei testes para as novas funcionalidades
· Atualizei a documentação correspondente
· Minhas mudanças não geram novos warnings
· Adicionei exemplos de uso se for uma nova funcionalidade
· Verifiquei se não há conflitos com a branch main
· Meu commit segue o padrão Conventional Commits

📝 Diretrizes de Código

Estrutura do Código

```
src/main/java/com/r2m/praticar/taskapplication/
├── configs/           # Configurações
├── controllers/       # Controladores REST
├── dto/              # Data Transfer Objects
├── enums/            # Enumeradores
├── exceptions/       # Exceções customizadas
├── models/           # Entidades JPA
├── repositories/     # Repositórios Spring Data
├── services/         # Serviços de negócio
└── TaskApplication.java
```

Convenções de Código

1. Nomenclatura:
   · Classes: PascalCase (UserController, TaskService)
   · Métodos: camelCase (getUserById, createTask)
   · Variáveis: camelCase (userName, taskList)
   · Constantes: UPPER_SNAKE_CASE (MAX_RETRY_COUNT, DEFAULT_TIMEOUT)
2. Spring Boot:
   · Use @RestController para endpoints REST
   · Use @Service para classes de serviço
   · Use @Repository para interfaces de repositório
   · Use @Entity para classes de modelo
3. Tratamento de Exceções:
   ```java
   // Boa prática
   try {
       // código
   } catch (SpecificException e) {
       log.error("Mensagem descritiva", e);
       throw new BusinessException("Mensagem amigável", HttpStatus.BAD_REQUEST);
   }
   ```

Documentação

· Documente endpoints da API com comentários JavaDoc
· Atualize o README.md se necessário
· Adicione exemplos de uso
· Documente variáveis de ambiente novas

Testes

· Cubra novos códigos com testes unitários
· Use JUnit 5 e Mockito
· Nomeie testes descritivamente:
  ```java
  @Test
  void shouldReturnUser_WhenValidIdIsProvided() { ... }
  
  @Test
  void shouldThrowException_WhenEmailAlreadyExists() { ... }
  ```

🐛 Reportando Bugs

1. Verifique se o bug já foi reportado
2. Use o template de issue
3. Inclua:
   · Passos para reproduzir
   · Comportamento esperado vs atual
   · Screenshots (se aplicável)
   · Ambiente (SO, versão do Java, Docker, etc.)

💡 Sugerindo Melhorias

1. Verifique se já existe uma discussão sobre o tema
2. Descreva claramente a melhoria proposta
3. Explique o benefício
4. Sugira uma possível implementação (opcional)

🔧 Configuração do Ambiente Desenvolvimento

Com Docker (Recomendado)

```bash
# Build e execução
docker-compose up -d --build

# Ver logs
docker-compose logs -f app

# Executar testes
docker-compose exec app mvn test

# Acessar container
docker-compose exec app sh
```

Sem Docker

```bash
# Configurar banco de dados MySQL local
# Instalar Java 17+ e Maven

mvn clean install
mvn spring-boot:run
```

📊 Tipos de Contribuição Aceitas

1. Código

· Novas funcionalidades
· Correções de bugs
· Refatorações
· Melhorias de performance

2. Documentação

· Correções no README
· Traduções
· Tutoriais
· Exemplos de uso

3. Testes

· Novos testes unitários
· Testes de integração
· Melhoria da cobertura

4. Infraestrutura

· Melhorias no Docker
· CI/CD
· Scripts de automação

🚫 O Que Não Fazer

· NUNCA fazer push direto para a branch main
· NUNCA commit de credenciais ou dados sensíveis
· NUNCA remover testes existentes sem justificativa
· EVITE mudanças que quebram compatibilidade (breaking changes)
· EVITE grandes PRs sem discussão prévia

🤝 Revisão de Código

Como Revisar

1. Verifique se o código segue as diretrizes
2. Teste as mudanças localmente se possível
3.# Forneça feedback construtivo
4. Sugira melhorias específicas

Como Receber Revisões

1. Esteja aberto a feedback
2. Responda a todos os comentários
3. Faça as correções sugeridas
4. Aprenda com as sugestões

🏆 Reconhecimento

Todas as contribuições válidas serão reconhecidas:

· Menção no README.md
· Badge de contribuidor no perfil GitHub
· Agradecimento nas release notes
. Crie um markdown com seu nome de usuário e fale sobre você e como entrar em contato.

---

Obrigado por contribuir! 💙
Sua contribuição ajuda a fazer deste projeto algo melhor para todos.