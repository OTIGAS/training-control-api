# API de Controle de Treinamento de Funcionários

Este projeto é uma API REST desenvolvida em **Java + Spring Boot**, com o objetivo de gerenciar cursos, turmas e
participantes de treinamentos corporativos.

---

## Tecnologias Utilizadas

- Java 11+
- Spring Boot
- JDBC (com SQL manual)
- Apache DBUtils (ou biblioteca similar para facilitar JDBC)
- Banco de dados relacional (ex: PostgreSQL, MySQL, etc.)
- JUnit (para testes automatizados - opcional)

---

## 🎯 Objetivo

Criar uma API RESTful para controlar o ciclo de vida de cursos e turmas de treinamento de funcionários, sem uso de
ORMs (Hibernate, JPA, etc.), com todas as consultas feitas via SQL puro.

---

## Regras de Negócio

1. **Exclusão em cascata de turmas**: Ao excluir um curso, todas as turmas associadas a ele também devem ser excluídas.
2. **Curso da turma é imutável**: Após a criação da turma, não é permitido alterar o curso associado.
3. **Gestão de participantes**:
    - Não é permitido atualizar dados de um participante dentro da turma.
    - É permitido adicionar ou remover participantes da turma.

---

## Requisitos Funcionais

### Cursos

- `POST /courses` – Cadastrar novo curso
- `PUT /courses/{code}` – Atualizar curso existente
- `DELETE /courses/{code}` – Excluir curso (remove também suas turmas)
- `GET /courses` – Listar todos os cursos

### Turmas de Treinamento

- `POST /teams` – Criar nova turma
- `PUT /teams/{code}` – Atualizar turma (exceto o curso associado)
- `DELETE /teams/{code}` – Excluir turma
- `GET /teams/course-code/{courseCode}` – Buscar turmas de um curso específico, ordenadas por data de início e fim

### Participantes de Turma

- `POST /team-participants` – Adicionar participante à turma
- `DELETE team-participants` – Remover participante da turma
- `GET team-participants/team-code/{teamCode}` – Listar participantes da turma

---

## 📋 Requisitos Não Funcionais

- Aplicação baseada em **Spring Boot**
- Proibido o uso de ORMs (Hibernate, JPA)
- **Consultas SQL manuais com JDBC**
- Separação clara por camadas:
    - Controller (REST)
    - Service (Regras de negócio)
    - Domain (Acesso ao banco via SQL)
    - DTOs (Transporte de dados)
- Design RESTful com **path parameters**
- Sem autenticação/token (API pública para fins de exercício)
- Boas práticas de código e arquitetura limpa
- Testes com JUnit (desejável, porém opcional)

---

## 🛠️ Como Executar

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/api-controle-treinamento.git
cd api-controle-treinamento

# Configurar o banco de dados (application.properties ou application.yml)

# Executar a aplicação
./mvnw spring-boot:run
```

---

## 📚 Documentação da API

A documentação interativa da API está disponível via **Swagger UI**:

**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

> Acesse esse link com a aplicação em execução localmente.
