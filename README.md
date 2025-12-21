
# Carneitarefas

Backend para gerenciamento de tarefas com visualização no estilo **Kanban**, desenvolvido com **Spring Boot + Kotlin**, utilizando **MySQL em Docker** e **Makefile** para padronizar o fluxo de desenvolvimento local.

---

## 🚀 Tecnologias Utilizadas

- Kotlin
- Spring Boot 3.4.1
    - Web
    - Data JPA
    - Security
    - Validation
- Hibernate / JPA
- MySQL 8
- Flyway
- JWT
- Swagger UI
- JUnit
- MockK

---

## 📋 Requisitos

### Gerais
- Java 21 (JDK)
- Docker
- Make

### Por sistema operacional

#### 🪟 Windows
- Docker Desktop
- Git Bash ou WSL2

#### 🐧 Linux
- Docker (Engine + Compose v2)
- Make

#### 🍎 macOS
- Docker (Colima ou Docker Desktop)
- Xcode Command Line Tools

---

## 🔐 Configuração do Banco de Dados

A aplicação utiliza um banco de dados **MySQL**, configurado para o ambiente Docker.

### Credenciais padrão (ambiente local)

- **Host:** `localhost`
- **Porta:** `3306`
- **Database:** `tarefas`
- **Usuário:** `usuario`
- **Senha:** `senha`

---


## Rodar a aplicação:

```bash
make up
```

## Parar a aplicação:

```bash
make down
```

---


## 🧪 Collection

O projeto possui uma collection do **Bruno** para testes da API, disponível na pasta `collection`.
