# NutriFit Backend - API RESTful en Spring Boot

## 🥗 Descripción

**NutriFit** es una aplicación web que permite a los usuarios planificar comidas saludables según su presupuesto. Este repositorio contiene el backend desarrollado en **Spring Boot**, encargado de gestionar la lógica de negocio, autenticación, almacenamiento de datos, generación de planes alimentarios y manejo del historial de presupuestos.

## 🚀 Funcionalidades Principales

- Autenticación de usuarios (JWT).
- Registro y validación de presupuesto semanal o mensual.
- Generación de planes de comida personalizados basados en presupuesto y datos del usuario.
- Gestión de recetas saludables.
- Seguimiento del gasto real vs. presupuesto planificado.
- Exportación de historial de planes y gastos en formato PDF.

## 🛠️ Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security (JWT)**
- **Spring Data JPA (Hibernate)**
- **MySQL / PostgreSQL**
- **Lombok**
- **MapStruct** (opcional)
- **Swagger/OpenAPI** (opcional)
- **PDF export:** iText / Apache PDFBox (si implementado)

## ⚙️ Instalación

Configura tu base de datos y crea el archivo .env o edita application.properties:

properties
Copiar
Editar
spring.datasource.url=jdbc:mysql://localhost:3306/nutrifit_db
spring.datasource.username=root
spring.datasource.password=tu_clave

spring.jpa.hibernate.ddl-auto=update
jwt.secret=clave-secreta

Construye y ejecuta el proyecto:

bash
Copiar
Editar
./mvnw spring-boot:run

📌 Endpoints Destacados
Método	Ruta	Descripción /n
POST	/api/auth/register	Registro de nuevo usuario
POST	/api/auth/login	Inicio de sesión con JWT
GET	/api/plans	Obtener planes de comida
POST	/api/plans/generate	Generar nuevo plan
GET	/api/recipes	Obtener lista de recetas
POST	/api/budget	Registrar presupuesto
GET	/api/history	Ver historial de presupuestos
GET	/api/history/pdf	Descargar historial en formato PDF

📦 Dependencias Importantes
spring-boot-starter-web

spring-boot-starter-security

spring-boot-starter-data-jpa

jjwt para manejo de JWT

mysql-connector-java

lombok
