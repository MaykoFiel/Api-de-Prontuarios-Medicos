 🏥 API de Prontuários Médicos

API RESTful desenvolvida em Java e Spring Boot para gestão completa de médicos, pacientes e agendamento de consultas clínicas, com autenticação segura via JWT, controle de perfis de acesso e documentação interativa via Swagger UI.

 🚀 Tecnologias Utilizadas

- Java 21 & Spring Boot 4
- Spring Web (Arquitetura em camadas: Controller, Service, Repository e DTOs com Java Records)
- Spring Data JPA / Hibernate & MySQL
- Spring Security & Auth0 Java JWT (Autenticação stateless com criptografia BCrypt)
- Bean Validation (Validação de dados de entrada, incluindo `@CPF` e `@Future`)
- SpringDoc OpenAPI (Swagger UI)
- Gradle & Lombok

 📋 Funcionalidades e Regras de Negócio

- Autenticação e Autorização:** Login via `POST /login` com emissão de token JWT e controle de acesso baseado em perfis (`ROLE_ADMIN`, `ROLE_MEDICO`, `ROLE_PACIENTE`).
- Gestão de Médicos e Pacientes:** Cadastro com criação automática de credenciais de acesso, listagem paginada (`Pageable`), detalhamento por ID, atualização cadastral e **exclusão lógica** (`ativo = false`).
- Agendamento e Gestão de Consultas:
- Bloqueio de agendamento para médicos ou pacientes inativos.
- Verificação de disponibilidade para impedir conflitos de horário do médico ou mais de uma consulta no mesmo dia para o paciente.
- Reagendamento e cancelamento com registro de motivo (`MotivoCancelamento`) e regra de antecedência mínima de 24 horas.
- Tratamento Global de Exceções: Padronização de respostas de erro HTTP (`400 Bad Request`, `403 Forbidden`, `404 Not Found`) via `@RestControllerAdvice`.

 🔗 Endpoints Principais

| Recurso | Método | Rota | Descrição |
| :--- | :--- | :--- | :--- |
| **Auth** | `POST` | `/login` | Autentica o usuário e retorna o token JWT |
| **Médicos** | `POST`, `GET`, `PUT`, `DELETE` | `/medicos` | CRUD completo e inativação lógica de médicos |
| **Pacientes** | `POST`, `GET`, `PUT`, `DELETE` | `/pacientes` | CRUD completo e inativação lógica de pacientes |
| **Consultas** | `POST`, `GET`, `PUT`, `DELETE` | `/consultas` | Agendamento, listagem, reagendamento e cancelamento |

 ⚙️ Como Executar Localmente

1. Clone o repositório:
   ```bash
   git clone [https://github.com/MaykoFiel/Api-de-Prontuarios-Medicos.git](https://github.com/MaykoFiel/Api-de-Prontuarios-Medicos.git)
   cd Api-de-Prontuarios-Medicos
