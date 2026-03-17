<h1 align="center">:file_cabinet: Backend ASTRAL</h1>

## :memo: Descrição

O objetivo deste projeto é promover a agricultura familiar/orgânica do município de Santa Teresa, tornando-a mais acessível à população. Para isso, vamos desenvolver um site que funcionará como uma feira online, permitindo que os produtores locais divulguem seus produtos de forma prática e eficiente. Para tal propósito faremos uma parceria com a Associação Santa Teresa de Agroecologia (ASTRAL)

## :wrench: Tecnologias utilizadas

- Java;
- Spring Boot;
- JPA;
- AWS;

## :handshake: Colaboradores

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/RickGutler">
        <img src="https://avatars.githubusercontent.com/u/109148639?v=4" width="100px;"/><br>
        <sub>
          <b>Henrique Gutler</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/JoaoPauloRocon">
        <img src="https://avatars.githubusercontent.com/u/104106279?v=4" width="100px;"/><br>
        <sub>
          <b>João Paulo Rocon</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

# Guia de Configuração - Backend ASTRAL

Este documento contém o passo a passo completo para configurar e executar o projeto em ambiente local.

---

## Índice

1. [Requisitos](#1-requisitos)
2. [Instalação do Java](#2-instalação-do-java)
3. [Instalação do Maven](#3-instalação-do-maven)
4. [Instalação do MySQL (XAMPP)](#4-instalação-do-mysql-xampp)
5. [Configuração do Banco de Dados](#5-configuração-do-banco-de-dados)
6. [Variáveis de Ambiente](#6-variáveis-de-ambiente)
7. [Executando o Projeto](#7-executando-o-projeto)
8. [Armazenamento de Imagens](#8-armazenamento-de-imagens)
9. [Cadastros e Autenticação](#9-cadastros-e-autenticação)
10. [URLs e Endpoints](#10-urls-e-endpoints)
11. [Solução de Problemas](#11-solução-de-problemas)

---

## 1. Requisitos

| Requisito   | Versão/Detalhe                             |
| ----------- | ------------------------------------------ |
| **Java**    | 17 ou superior (Java 21 ou 25 recomendado) |
| **Maven**   | 3.6+                                       |
| **MySQL**   | 8.x                                        |
| **Sistema** | Windows, Linux ou macOS                    |

---

## 2. Instalação do Java

### 2.1. Baixar o Java

- **Oracle JDK:** https://www.oracle.com/java/technologies/downloads/#java17
- **Eclipse Temurin (OpenJDK):** https://adoptium.net/temurin/releases/

Escolha a versão **17** ou superior. Para Java 21 ou 25, certifique-se de que o Lombok está na versão 1.18.40+ (o projeto já usa 1.18.42).

### 2.2. Configurar Variáveis de Ambiente (Java)

**Windows:**

1. Pressione `Win + R`, digite `sysdm.cpl` e pressione Enter
2. Aba **Avançado** → **Variáveis de Ambiente**
3. Em **Variáveis do sistema**, clique em **Novo**:
   - **Nome:** `JAVA_HOME`
   - **Valor:** `C:\Program Files\Java\jdk-17` (ajuste conforme o caminho da sua instalação)
4. Edite a variável **Path** e adicione: `%JAVA_HOME%\bin`
5. Feche e abra o terminal para aplicar as alterações

**Verificar instalação:**

```bash
java -version
javac -version
```

---

## 3. Instalação do Maven

### 3.1. Baixar o Maven

⚠️ **Importante:** Baixe o **Apache Maven** (ferramenta de build), **não** o Maven Daemon.

- **Site oficial:** https://maven.apache.org/download.cgi
- Baixe o **Binary zip archive** (ex.: `apache-maven-3.9.6-bin.zip`)

### 3.2. Instalação Manual

1. Extraia o arquivo para uma pasta (ex.: `C:\Program Files\Apache\maven`)
2. O caminho final deve ser algo como: `C:\Program Files\Apache\maven\apache-maven-3.9.6`

### 3.3. Configurar Variáveis de Ambiente (Maven)

**Windows:**

1. Pressione `Win + R`, digite `sysdm.cpl` e pressione Enter
2. Aba **Avançado** → **Variáveis de Ambiente**
3. Crie a variável `MAVEN_HOME`:
   - **Nome:** `MAVEN_HOME`
   - **Valor:** `C:\Program Files\Apache\maven\apache-maven-3.9.6` (ajuste conforme o caminho)
4. Edite a variável **Path** e adicione: `%MAVEN_HOME%\bin`
5. Feche e abra o terminal para aplicar as alterações

**Verificar instalação:**

```bash
mvn -version
```

### 3.4. Alternativa: Instalação via Winget (Windows)

```powershell
winget install Apache.Maven
```

---

## 4. Instalação do MySQL (XAMPP)

O XAMPP inclui o MySQL e o phpMyAdmin, facilitando o gerenciamento do banco.

### 4.1. Baixar e Instalar

- **Site:** https://www.apachefriends.org/
- Durante a instalação, marque **MySQL** e **phpMyAdmin**

### 4.2. Iniciar o MySQL

1. Abra o **XAMPP Control Panel**
2. Clique em **Start** no MySQL
3. Aguarde até o status ficar verde

---

## 5. Configuração do Banco de Dados

### 5.1. Criar o Banco

1. Abra o phpMyAdmin: http://localhost/phpmyadmin
2. Ou clique em **Admin** ao lado do MySQL no XAMPP
3. Na aba **SQL**, execute:

```sql
CREATE DATABASE astral;
```

### 5.2. Configuração Padrão

O projeto espera:

- **Host:** localhost
- **Porta:** 3306
- **Banco:** astral
- **Usuário:** root
- **Senha:** (vazia)

Se o seu MySQL tiver senha configurada, edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.password=sua_senha
```

### 5.3. Migrations

O projeto usa **Flyway** para as migrations. As tabelas são criadas automaticamente na primeira execução. Não é necessário executar SQL manualmente para criar as tabelas.

---

## 6. Variáveis de Ambiente

### 6.1. Variáveis Necessárias (Produção)

| Variável           | Descrição                                            |
| ------------------ | ---------------------------------------------------- |
| `MAIL_USERNAME`    | E-mail para envio (ex.: Gmail)                       |
| `MAIL_SECRET`      | Senha de app do e-mail                               |
| `JWT_TOKEN_SECRET` | Chave secreta para JWT (mínimo 32 caracteres)        |
| `AWS_ACCESS_KEY`   | Chave de acesso AWS (se usar S3)                     |
| `AWS_SECRET_KEY`   | Chave secreta AWS (se usar S3)                       |
| `STORAGE_LOCAL`    | `true` para armazenamento local, `false` para AWS S3 |

### 6.2. Valores Padrão (Desenvolvimento)

O projeto possui **valores padrão** para desenvolvimento local. Você pode rodar sem configurar nenhuma variável:

- **E-mail:** dev@localhost / dev-password
- **JWT:** chave-secreta-para-desenvolvimento-local-minimo-32-chars
- **AWS:** placeholder-key / placeholder-secret (usa armazenamento local)
- **Storage:** local (padrão)

### 6.3. Configurar no PowerShell (Windows)

Antes de executar o JAR:

```powershell
$env:MAIL_USERNAME = "seu-email@gmail.com"
$env:MAIL_SECRET = "sua-senha-app"
$env:JWT_TOKEN_SECRET = "sua-chave-jwt-secreta"
$env:STORAGE_LOCAL = "false"
$env:AWS_ACCESS_KEY = "sua-chave-aws"
$env:AWS_SECRET_KEY = "sua-chave-secreta-aws"
```

---

## 7. Executando o Projeto

### 7.1. Compilar e Executar

⚠️ **Importante:** No Windows, use `java -jar` em vez de `mvn spring-boot:run` para evitar o erro de "comando muito longo" (linha de comando excede o limite do Windows).

```powershell
# Navegar até a pasta do projeto
cd c:\repositorios\astral-backend

# Compilar e gerar o JAR
mvn clean package -DskipTests

# Executar
java -jar target/astral-backend-1.0.0.jar
```

### 7.2. Porta

A aplicação roda na **porta 8080**.

---

## 8. Armazenamento de Imagens

### 8.1. Modo Local (Padrão)

Quando `STORAGE_LOCAL=true` ou não configurado:

- As imagens são salvas na pasta `uploads/` no diretório do projeto
- A pasta é **criada automaticamente** no primeiro upload
- **Não é necessário criar a pasta manualmente**

### 8.2. URL das Imagens

No frontend, use:

```
http://localhost:8080${produto.imagem}
```

Exemplo: `http://localhost:8080/uploads/produtor_2/Alface.jpg_1773698225633`

As imagens em `/uploads/**` são **públicas** (não requerem autenticação).

### 8.3. Modo AWS S3

Para usar a AWS:

1. Configure `STORAGE_LOCAL=false`
2. Configure `AWS_ACCESS_KEY` e `AWS_SECRET_KEY`

---

## 9. Cadastros e Autenticação

### 9.1. Criptografia de Senhas

O projeto usa **BCrypt** para hash de senhas.

### 9.2. Cadastrar Usuário Diretamente no Banco

Para criar um usuário manualmente (ex.: para testes):

1. **Gerar hash BCrypt** da senha desejada:
   - https://bcrypt-generator.com/
   - https://www.bcrypt.online/

2. **Inserir na tabela `usuario`:**

```sql
INSERT INTO usuario (email, senha)
VALUES ('produtor@email.com', '$2a$10$SEU_HASH_BCRYPT_AQUI');
```

3. **Para Produtor:**

```sql
INSERT INTO produtor (id, nome, telefone, disponivel, ativo)
VALUES (LAST_INSERT_ID(), 'Nome do Produtor', '11999999999', 1, 1);
```

4. **Para Administrador:**

```sql
INSERT INTO administrador (id)
VALUES (LAST_INSERT_ID());
```

---

## 10. URLs e Endpoints

| Recurso        | URL                                   |
| -------------- | ------------------------------------- |
| **API Base**   | http://localhost:8080                 |
| **Swagger UI** | http://localhost:8080/swagger-ui.html |
| **Imagens**    | http://localhost:8080/uploads/...     |

---

## 11. Solução de Problemas

### Erro: "java não é reconhecido"

- Java não está instalado ou não está no PATH
- Verifique com `java -version` e configure as variáveis de ambiente

### Erro: "mvn não é reconhecido"

- Maven não está instalado ou não está no PATH
- Certifique-se de baixar o **Apache Maven** (não o Maven Daemon)

### Erro: "CreateProcess error=206 - O nome do arquivo ou a extensão é muito grande"

- Use `java -jar target/astral-backend-1.0.0.jar` em vez de `mvn spring-boot:run`

### Erro: "Could not resolve placeholder 'AWS_ACCESS_KEY'"

- O projeto já possui valores padrão. Se ainda ocorrer, verifique se as variáveis estão configuradas ou se os valores padrão foram mantidos no `application.yml` e `application.properties`

### Erro: "403 Forbidden" ao acessar imagens

- As rotas `/uploads/**` estão configuradas como públicas no `SecurityConfigurations`. Verifique se a alteração foi aplicada.

### XAMPP não instala Maven

- XAMPP inclui Apache, MySQL, PHP. O Maven é uma ferramenta separada e deve ser instalado independentemente.

### Lombok não gera getters/setters (Java 23+)

- Java 23+ requer configuração explícita do annotation processor. O projeto já usa Lombok 1.18.42 e configurou o `maven-compiler-plugin` para isso.

---

## Resumo Rápido

```powershell
# 1. Instalar: Java 17+, Maven, XAMPP (MySQL)
# 2. Criar banco: CREATE DATABASE astral;
# 3. Iniciar MySQL no XAMPP
# 4. Executar:

cd c:\repositorios\astral-backend
mvn clean package -DskipTests
java -jar target/astral-backend-1.0.0.jar

# 5. Acessar: http://localhost:8080/swagger-ui.html
```
