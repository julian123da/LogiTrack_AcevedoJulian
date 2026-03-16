# LogiTrack - Sistema de Gestión y Auditoría de Bodegas

## Descripción del Proyecto

LogiTrack S.A. es una empresa que administra múltiples bodegas distribuidas en distintas ciudades, encargadas de almacenar productos y gestionar movimientos de inventario como entradas, salidas y transferencias.

Anteriormente, el control de inventarios y auditorías se realizaba manualmente mediante hojas de cálculo, lo que generaba múltiples problemas operativos, de seguridad y de control de la información.

Por esta razón, se desarrolló un sistema backend centralizado utilizando Spring Boot, que permite gestionar el inventario de forma segura, automatizada y auditable mediante una API REST.

## Problemática

Antes de la implementación del sistema existían los siguientes problemas:

- No existía trazabilidad de los movimientos de inventario.
- El control manual generaba errores humanos.
- No había control de acceso por usuarios.
- No existía auditoría de cambios.
- La información podía ser modificada sin registro.
- No existían reportes confiables.
- No había seguridad en el acceso a los datos.
- No existía control de roles.
- Difícil escalabilidad del sistema manual.

## Solución Implementada

Se desarrolló un sistema backend con Spring Boot que permite:

- Gestionar bodegas y productos mediante endpoints REST.
- Registrar movimientos de inventario automáticamente.
- Implementar auditoría automática de cambios.
- Proteger la información mediante autenticación JWT.
- Implementar control de roles (ADMIN y EMPLEADO).
- Generar reportes mediante consultas avanzadas.
- Documentar la API mediante Swagger OpenAPI.
- Validar datos mediante anotaciones de Spring Validation.
- Manejar errores globalmente.
- Centralizar la lógica de negocio.

El sistema sigue una arquitectura por capas basada en buenas prácticas de desarrollo backend.

## Arquitectura del Sistema

El sistema implementa una arquitectura en capas:

Controller → Service → Repository → Database

Capas adicionales:

DTO → Transporte de datos
Mapper → Conversión de datos
Security → Seguridad JWT
Config → Configuración global
Exception → Manejo de errores

Esta arquitectura permite mantener separación de responsabilidades y facilitar el mantenimiento del sistema.

## Estructura del Proyecto

src/main/java/com/logitrack/

├── auth  
├── config  
├── controller  
├── dto  
├── mapper  
├── model  
├── repository  
├── service  
├── security  
└── exception  

## Explicación de Carpetas

## auth

Esta carpeta contiene toda la lógica relacionada con la autenticación de usuarios.

Responsabilidades:

- Registro de usuarios
- Login de usuarios
- Generación de tokens JWT
- Validación de credenciales
- Respuestas de autenticación

Ejemplos de clases:

AuthController:
Controla los endpoints:

POST /auth/login  
POST /auth/register  

AuthService:
Contiene la lógica de autenticación.

AuthRequest:
DTO del login.

AuthResponse:
DTO de respuesta con el token JWT.

Usuario:
Entidad del usuario autenticado.

El objetivo de esta capa es permitir el acceso seguro al sistema.

## config

Esta carpeta contiene las configuraciones globales del sistema.

Responsabilidades:

- Configuración de Swagger.
- Configuración de CORS.
- Beans globales.
- Configuración de ModelMapper.
- Configuración de auditoría.
- Configuración de propiedades.

Ejemplos:

SwaggerConfig  
CorsConfig  
ModelMapperConfig  

Su función es centralizar la configuración del proyecto.

## controller

Esta carpeta contiene los controladores REST.

Responsabilidades:

- Recibir solicitudes HTTP.
- Validar datos de entrada.
- Llamar los services.
- Retornar respuestas JSON.
- Manejar endpoints REST.

Ejemplos:

BodegaController  
ProductoController  
MovimientoController  
AuditoriaController  

Ejemplo de endpoints:

GET /productos  
POST /productos  
PUT /productos/{id}  
DELETE /productos/{id}  

GET /bodegas  
POST /bodegas  

Los controllers no contienen lógica de negocio.

## dto

Los DTO (Data Transfer Objects) permiten transportar información entre el backend y el cliente sin exponer directamente las entidades.

Ventajas:

- Seguridad.
- Control de datos enviados.
- Separación de responsabilidades.
- Evita problemas de serialización.
- Mejora el diseño de la API.

Ejemplos:

ProductoRequestDTO  
ProductoResponseDTO  
MovimientoDTO  
UsuarioDTO  
AuthRequest  
AuthResponse  

Tipos comunes:

Request DTO:
Datos que envía el cliente.

Response DTO:
Datos que responde el backend.

## mapper

Esta carpeta contiene las clases que convierten entidades en DTO y DTO en entidades.

Responsabilidades:

- Convertir entidades a Response DTO.
- Convertir Request DTO a entidades.
- Evitar lógica de conversión en controllers.
- Mantener código limpio.

Se puede implementar con:

ModelMapper  
MapStruct  
Mappers manuales  

Ejemplo:

ProductoMapper:

Convierte:

Producto → ProductoResponseDTO

ProductoRequestDTO → Producto

Beneficios:

- Código más mantenible.
- Separación clara de capas.
- Facilita cambios futuros.

## model

Contiene las entidades JPA que representan las tablas de la base de datos.

Responsabilidades:

- Representar las tablas.
- Definir relaciones.
- Definir columnas.
- Definir llaves primarias.

Ejemplos:

Bodega  
Producto  
Movimiento  
Usuario  
Auditoria  

Anotaciones usadas:

@Entity  
@Table  
@Id  
@GeneratedValue  
@Column  
@ManyToOne  
@OneToMany  
@JoinColumn  

Estas clases representan la estructura de la base de datos.

## repository

Esta carpeta contiene las interfaces que permiten acceder a la base de datos mediante Spring Data JPA.

Responsabilidades:

- CRUD automático.
- Queries personalizadas.
- Consultas por filtros.
- Acceso a datos.

Ejemplo:

public interface ProductoRepository extends JpaRepository<Producto, Long>

Ejemplos de consultas:

findByStockLessThan(int stock)

findByFechaBetween(LocalDate inicio, LocalDate fin)

findByUsuarioId(Long id)

El repository solo accede a datos, no contiene lógica de negocio.

## service

Esta carpeta contiene la lógica de negocio del sistema.

Responsabilidades:

- Procesar datos.
- Aplicar reglas de negocio.
- Validar operaciones.
- Coordinar repositories.
- Manejar transacciones.
- Aplicar reglas de inventario.

Ejemplos:

ProductoService  
BodegaService  
MovimientoService  
AuditoriaService  

Ejemplo de responsabilidades:

Validar stock antes de salida.

Registrar auditoría después de cambios.

Controlar transferencias entre bodegas.

El controller siempre debe llamar al service.

Nunca al repository directamente.

## security

Esta carpeta contiene la configuración de seguridad.

Responsabilidades:

- Validar tokens JWT.
- Configurar Spring Security.
- Controlar autenticación.
- Controlar autorización.
- Definir roles.
- Proteger endpoints.

Ejemplos:

SecurityConfig  
JwtFilter  
JwtUtils  
UserDetailsServiceImpl  

Funciones:

Validar tokens.

Agregar filtros JWT.

Configurar rutas públicas y privadas.

Configurar BCrypt.

## exception

Contiene el manejo global de excepciones.

Responsabilidades:

- Manejo global de errores.
- Respuestas personalizadas.
- Centralizar excepciones.
- Manejo de errores HTTP.

Ejemplos:

GlobalExceptionHandler  
ResourceNotFoundException  
BadRequestException  
UnauthorizedException  

Implementado con:

@ControllerAdvice  
@ExceptionHandler  

Ejemplo de respuesta:

{
 "timestamp":"2026-03-10",
 "message":"Producto no encontrado",
 "status":404
}

Esto permite respuestas consistentes.

## Seguridad Implementada

Se implementó:

Spring Security  
JWT Authentication  
BCrypt Password Encoder  

Roles:

ADMIN:

Puede:

CRUD completo  
Ver auditorías  
Gestionar usuarios  

EMPLEADO:

Puede:

Registrar movimientos  
Consultar productos  
Consultar bodegas  

## Funcionalidades Implementadas

CRUD de Bodegas.

CRUD de Productos.

Registro de movimientos.

Transferencias entre bodegas.

Auditoría automática.

Seguridad JWT.

Control de roles.

Consultas avanzadas.

Validaciones.

Manejo de errores.

Documentación Swagger.

## Tecnologías Utilizadas

Java 17

Spring Boot

Spring Security

JWT

MySQL

Hibernate

JPA

Swagger OpenAPI

Maven

HTML

CSS

JavaScript

## Base de Datos

Configurada en application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/logitrack

spring.datasource.username=root

spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

## Ejecución del Proyecto

1 Clonar repositorio:

git clone https://github.com/tuusuario/logitrack.git

2 Crear base de datos:

CREATE DATABASE logitrack;

3 Ejecutar proyecto:

mvn spring-boot:run

4 Acceder a Swagger:

http://localhost:8080/swagger-ui.html

## Ejemplo de Login

Endpoint:

POST /auth/login

Request:

{
 "username":"admin",
 "password":"123456"
}

Response:

{
 "token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
}

Uso del token:

Authorization: Bearer TOKEN

## Ejemplos de Endpoints

Productos:

GET /productos

POST /productos

PUT /productos/{id}

DELETE /productos/{id}

Movimientos:

POST /movimientos

GET /movimientos

GET /movimientos/fecha

Auditoría:

GET /auditoria

GET /auditoria/usuario

## Resultado Final

El sistema LogiTrack permite:

Control completo del inventario.

Seguridad de acceso.

Auditoría de cambios.

API documentada.

Arquitectura limpia.

Escalabilidad del sistema.

Separación de responsabilidades.

Buenas prácticas backend.

## Autor

Proyecto desarrollado como sistema académico de gestión empresarial aplicando buenas prácticas de desarrollo backend con Spring Boot.
