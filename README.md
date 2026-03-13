# LogiTrack - Sistema de Gestión y Auditoría de Inventarios

## Descripción del Proyecto

La empresa **LogiTrack S.A.** administra múltiples bodegas distribuidas en diferentes ciudades, encargadas del almacenamiento de productos y la gestión de movimientos de inventario (entradas, salidas y transferencias).

Actualmente, el control de inventarios se realiza manualmente mediante hojas de cálculo, lo que genera problemas de trazabilidad, seguridad y control de accesos.

Este proyecto consiste en el desarrollo de un **backend centralizado en Spring Boot** que permita gestionar el inventario de forma segura, auditable y eficiente mediante una API REST protegida con JWT.

---

## Objetivo General

Desarrollar un sistema de gestión y auditoría de bodegas que permita registrar transacciones de inventario y generar reportes auditables de los cambios realizados por cada usuario.

---

## Tecnologías Utilizadas

* Java 17+
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* MySQL
* JPA / Hibernate
* Maven
* Swagger / OpenAPI 3
* HTML / CSS / JavaScript (Frontend básico)

---

## Funcionalidades del Sistema

### 1. Gestión de Bodegas

Permite administrar las bodegas del sistema mediante operaciones CRUD.

Campos:

* id
* nombre
* ubicacion
* capacidad
* encargado

Operaciones:

* Crear bodegas
* Consultar bodegas
* Actualizar bodegas
* Eliminar bodegas

---

### 2. Gestión de Productos

CRUD completo de productos.

Campos:

* id
* nombre
* categoria
* stock
* precio

Operaciones:

* Crear productos
* Consultar productos
* Actualizar productos
* Eliminar productos

---

### 3. Movimientos de Inventario

Permite registrar:

* Entradas
* Salidas
* Transferencias entre bodegas

Cada movimiento almacena:

* Fecha
* Tipo de movimiento:

  * ENTRADA
  * SALIDA
  * TRANSFERENCIA
* Usuario responsable
* Bodega origen
* Bodega destino
* Productos
* Cantidades

---

### 4. Auditoría de Cambios

Se implementa una entidad **Auditoria** para registrar:

* Tipo de operación:

  * INSERT
  * UPDATE
  * DELETE
* Fecha y hora
* Usuario responsable
* Entidad afectada
* Valores anteriores
* Valores nuevos

Implementación mediante:

* EntityListeners JPA
  o
* Aspectos personalizados (AOP)

---

### 5. Autenticación y Seguridad

Implementación de seguridad mediante:

* Spring Security
* JWT

Endpoints:

```
/auth/login
/auth/register
```

Rutas protegidas:

```
/bodegas
/productos
/movimientos
/auditorias
```

Roles:

* ADMIN
* EMPLEADO

---

### 6. Consultas Avanzadas y Reportes

El sistema incluye endpoints para:

Filtros:

* Productos con stock bajo (<10)
* Movimientos por rango de fechas
* Auditorías por usuario
* Auditorías por tipo de operación

Reportes:

* Stock total por bodega
* Productos más movidos

Respuesta en formato JSON.

---

### 7. Documentación API

La API se documenta mediante:

Swagger / OpenAPI 3

Permite:

* Visualizar endpoints
* Probar requests
* Probar autenticación JWT

Acceso:

```
http://localhost:8080/swagger-ui.html
```

---

### 8. Manejo de Excepciones y Validaciones

Implementaciones:

Manejo global:

* @ControllerAdvice

Validaciones:

* @NotNull
* @Size
* @Min
* @Max

Respuestas personalizadas:

* 400 Bad Request
* 401 Unauthorized
* 404 Not Found
* 500 Internal Server Error

Formato JSON estándar para errores.

---

### 9. Despliegue

Configuración:

Base de datos MySQL en:

```
application.properties
```

Scripts incluidos:

```
schema.sql
data.sql
```

Ejecución:

Servidor:

* Tomcat embebido
  o
* Tomcat externo

Frontend básico:

Carpeta:

```
frontend/
```

Incluye:

* HTML
* CSS
* JavaScript

Para probar:

* Login
* Consultas principales

---

## Estructura del Proyecto

```
src/

 ├─ controller/
 ├─ service/
 ├─ repository/
 ├─ model/
 ├─ config/
 ├─ security/
 └─ exception/
```

---

## Instalación y Ejecución

### 1 Clonar repositorio

```
git clone https://github.com/julian123da/LogiTrack_AcevedoJulian.git
```

### 2 Configurar base de datos

Crear base de datos MySQL:

```
logitrack_db
```

Configurar:

application.properties

Ejemplo:

```
spring.datasource.url=jdbc:mysql://localhost:3306/logitrack_db
spring.datasource.username=root
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 3 Ejecutar proyecto

Desde IntelliJ o terminal:

```
mvn spring-boot:run
```

o ejecutar:

```
ProyectoApplication.java
```

---

## Ejemplos de Endpoints

Login:

```
POST /auth/login
```

Body:

```
{
"email":"admin@test.com",
"password":"123456"
}
```

Respuesta:

```
{
"token":"JWT_TOKEN"
}
```

---

Consultar productos:

```
GET /productos
```

Header:

```
Authorization: Bearer TOKEN
```

---

## Resultados Esperados

El proyecto debe incluir:

* Backend completo Spring Boot
* Scripts SQL
* Swagger documentado
* README documentado
* Frontend básico
* Repositorio GitHub

---

## Entregables

* Código fuente backend
* Scripts SQL
* Documentación Swagger
* README
* Frontend básico
* Documento explicativo
* Diagrama de clases
* Arquitectura del sistema
* Ejemplo de token JWT
* Repositorio GitHub

---

## Autor

Julian Acevedo

Proyecto académico - Sistema de Gestión LogiTrack

---

## Estado del Proyecto

Proyecto en desarrollo.
