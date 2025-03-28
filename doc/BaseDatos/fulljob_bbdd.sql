CREATE DATABASE IF NOT EXISTS fulljob_bbdd;
USE fulljob_bbdd;

-- Tabla de Usuarios 
CREATE TABLE usuarios (
    email VARCHAR(45) PRIMARY KEY,
    nombre VARCHAR(45) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled INT DEFAULT 1,  -- 1 activo, 0 inactivo
    fecha_registro DATE NOT NULL,
    rol VARCHAR(15) NOT NULL,
    CHECK(ROL IN ('EMPRESA', 'ADMON', 'CANDIDATO'))
);

-- Tabla de Empresas 
CREATE TABLE empresas (
    id_empresa INT AUTO_INCREMENT PRIMARY KEY,
    cif VARCHAR(10) UNIQUE NOT NULL,
    nombre_empresa VARCHAR(100) NOT NULL,
    direccion_fiscal VARCHAR(100),
    pais VARCHAR(45),
    email VARCHAR(45) UNIQUE NOT NULL,
    FOREIGN KEY (email) REFERENCES usuarios(email) ON DELETE CASCADE
);

-- Tabla de Categorías
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(2000)
);

-- Tabla de Vacantes 
CREATE TABLE vacantes (
    id_vacante INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    salario DOUBLE,
    estatus ENUM('CREADA', 'ASIGNADA', 'CANCELADA') NOT NULL DEFAULT 'CREADA',
    destacado TINYINT NOT NULL DEFAULT 0,
    imagen VARCHAR(250),
    detalles TEXT,
    id_categoria INT NOT NULL,
    id_empresa INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON DELETE CASCADE,
    FOREIGN KEY (id_empresa) REFERENCES empresas(id_empresa) ON DELETE CASCADE
);

-- Tabla de Solicitudes 
CREATE TABLE solicitudes (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    archivo VARCHAR(250),
    comentarios VARCHAR(2000),
    estado TINYINT DEFAULT 0,  -- 0: Pendiente, 1: Aceptada
    curriculum VARCHAR(45),
    id_vacante INT NOT NULL,
    email VARCHAR(45) NOT NULL,
    UNIQUE(id_Vacante,email), -- restriccion: Impide que un usuario se inscriba más de una vez a la misma vacante
    FOREIGN KEY (id_vacante) REFERENCES vacantes(id_vacante) ON DELETE CASCADE,
    FOREIGN KEY (email) REFERENCES usuarios(email) ON DELETE CASCADE
);


