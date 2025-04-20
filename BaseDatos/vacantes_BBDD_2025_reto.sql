
USE vacantes_BBDD_2025_RETO;

CREATE TABLE `Categorias` (
  id_categoria INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion VARCHAR(2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Usuarios` (
  email VARCHAR(45) NOT NULL PRIMARY KEY,
  nombre VARCHAR(45) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled INT NOT NULL DEFAULT 1,
  fecha_Registro DATE,
  rol VARCHAR(15) NOT NULL,
  CHECK (rol IN ('EMPRESA', 'ADMON', 'CLIENTE'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Empresas` (
  id_empresa INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cif VARCHAR(10) NOT NULL UNIQUE,
  nombre_empresa VARCHAR(100) NOT NULL,
  direccion_fiscal VARCHAR(100),
  pais VARCHAR(45),
  email VARCHAR(45),
  FOREIGN KEY (email) REFERENCES Usuarios(email)
);

CREATE TABLE `Vacantes` (
  id_vacante INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(200) NOT NULL,
  descripcion TEXT NOT NULL,
  fecha DATE NOT NULL,
  salario DOUBLE NOT NULL,
  estatus ENUM('CREADA', 'CUBIERTA', 'CANCELADA') NOT NULL,
  destacado TINYINT NOT NULL,
  imagen VARCHAR(250) NOT NULL,
  detalles TEXT NOT NULL,
  id_categoria INT NOT NULL,
  id_empresa INT NOT NULL,
  FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria),
  FOREIGN KEY (id_empresa) REFERENCES Empresas(id_empresa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Solicitudes` (
  id_solicitud INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  fecha DATE NOT NULL,
  archivo VARCHAR(250) NOT NULL,
  comentarios VARCHAR(2000),
  estado TINYINT NOT NULL DEFAULT 0,
  curriculum VARCHAR(45),
  id_vacante INT NOT NULL,
  email VARCHAR(45) NOT NULL,
  UNIQUE(id_vacante, email),
  FOREIGN KEY (email) REFERENCES Usuarios(email),
  FOREIGN KEY (id_vacante) REFERENCES Vacantes(id_vacante)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
