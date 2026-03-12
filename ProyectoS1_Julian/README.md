## Proyecto Spring Boot
````mysql
CREATE DATABASE IF NOT EXISTS logitrack_db;

USE logitrack_db;

CREATE TABLE usuario(
id INT PRIMARY KEY NOT NULL,
nombre VARCHAR(50) NOT NULL,
documento VARCHAR(20) NOT NULL,
rol ENUM("admin","empleado") NOT NULL,
usuario VARCHAR(50) NOT NULL,
contrasena VARCHAR(50) NOT NULL
);

CREATE TABLE producto(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
nombre VARCHAR(120) NOT NULL,
categoria VARCHAR(100),
precio DECIMAL(10,2) NOT NULL,
stock_total INT DEFAULT 0
);

CREATE TABLE bodega (
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
nombre VARCHAR(50) NOT NULL,
ubicacion VARCHAR(50) NOT NULL,
capacidad INT NOT NULL,
idEncargado INT NOT NULL,
FOREIGN KEY (idEncargado) REFERENCES usuario(id)
);

CREATE TABLE inventario(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
idBodega INT NOT NULL,
idProducto INT NOT NULL,
cantidad INT NOT NULL,
FOREIGN KEY (idBodega) REFERENCES bodega(id),
FOREIGN KEY (idProducto) REFERENCES producto(id)
);

CREATE TABLE movimiento(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
fecha DATE NOT NULL,
tipoMovimiento ENUM("entrada","salida","transferencia") NOT NULL,
idUsuario INT NOT NULL,
idBodegaOrigen INT NOT NULL,
idBodegaDestino INT NOT NULL,
FOREIGN KEY (idUsuario) REFERENCES usuario(id),
FOREIGN KEY (idBodegaOrigen) REFERENCES bodega(id),
FOREIGN KEY (idBodegaDestino) REFERENCES bodega(id)
);

CREATE TABLE detalleMovimiento(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
idMovimiento INT NOT NULL,
idProducto INT NOT NULL,
cantidad INT NOT NULL,
FOREIGN KEY (idMovimiento) REFERENCES movimiento(id),
FOREIGN KEY (idProducto) REFERENCES producto(id)
);

CREATE TABLE auditoria(
id INT PRIMARY KEY AUTO_INCREMENT,
entidad VARCHAR(50),
operacion ENUM("INSERT","UPDATE","DELETE"),
fecha DATETIME,
idUsuario INT,
valorAnterior TEXT,
valorNuevo TEXT,
FOREIGN KEY (idUsuario) REFERENCES usuario(id)
);
````