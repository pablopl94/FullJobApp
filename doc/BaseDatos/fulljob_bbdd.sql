CREATE DATABASE IF NOT EXISTS fulljob_bbdd;
USE fulljob_bbdd;

-- Tabla de Usuarios (base principal para login, roles, etc.)
CREATE TABLE usuarios (
    email VARCHAR(45) PRIMARY KEY,
    nombre VARCHAR(45) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled INT DEFAULT 1,  -- 1 activo, 0 inactivo
    fecha_registro DATE NOT NULL,
    rol VARCHAR(15) NOT NULL  -- ADMIN, CANDIDATO, EMPRESA
);

-- Tabla de Empresas (solo si el usuario tiene rol EMPRESA)
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

-- Tabla de Vacantes (cada vacante está asociada a una empresa y una categoría)
CREATE TABLE vacantes (
    id_vacante INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    salario DOUBLE,
    estatus ENUM('CREADA', 'ASIGNADA', 'CANCELADA') DEFAULT 'CREADA',
    destacado TINYINT DEFAULT 0,
    imagen VARCHAR(250),
    detalles TEXT,
    id_categoria INT NOT NULL,
    id_empresa INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON DELETE CASCADE,
    FOREIGN KEY (id_empresa) REFERENCES empresas(id_empresa) ON DELETE CASCADE
);

-- Tabla de Solicitudes (cada solicitud la hace un usuario a una vacante)
CREATE TABLE solicitudes (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    archivo VARCHAR(250),
    comentarios VARCHAR(2000),
    estado TINYINT DEFAULT 0,  -- 0: Pendiente, 1: Aceptada, 2: Rechazada
    curriculum VARCHAR(45),
    id_vacante INT NOT NULL,
    email VARCHAR(45) NOT NULL,
    FOREIGN KEY (id_vacante) REFERENCES vacantes(id_vacante) ON DELETE CASCADE,
    FOREIGN KEY (email) REFERENCES usuarios(email) ON DELETE CASCADE
);

-- Usuarios 
INSERT INTO usuarios VALUES
('admin@fulljob.com', 'Admin', 'Principal', '{noop}admin123', 1, '2024-01-01', 'ADMIN'),
('juan@correo.com', 'Juan', 'Pérez', '{noop}1234', 1, '2024-02-01', 'CANDIDATO'),
('ana@correo.com', 'Ana', 'López', '{noop}abcd', 1, '2024-02-03', 'CANDIDATO'),
('carlos@tech.com', 'Carlos', 'Martínez', '{noop}pass123', 1, '2024-03-01', 'EMPRESA'),
('marta@softdev.com', 'Marta', 'García', '{noop}qwerty', 1, '2024-03-02', 'EMPRESA'),
('lucia@correo.com', 'Lucía', 'Rodríguez', '{noop}lucia456', 1, '2024-02-10', 'CANDIDATO'),
('david@cloudify.com', 'David', 'Ruiz', '{noop}davidpwd', 1, '2024-03-10', 'EMPRESA'),
('andrea@correo.com', 'Andrea', 'Sánchez', '{noop}andrea89', 1, '2024-02-15', 'CANDIDATO'),
('pablo@netcorp.com', 'Pablo', 'Moreno', '{noop}pm123', 1, '2024-03-12', 'EMPRESA'),
('natalia@correo.com', 'Natalia', 'Ortega', '{noop}natpass', 1, '2024-02-20', 'CANDIDATO');

-- Empresas 
INSERT INTO empresas (cif, nombre_empresa, direccion_fiscal, pais, email) VALUES
('B12345678', 'Tech Solutions', 'Calle Falsa 123', 'España', 'carlos@tech.com'),
('B87654321', 'SoftDev SL', 'Avenida Siempre Viva 742', 'España', 'marta@softdev.com'),
('C11223344', 'Cloudify', 'Calle Luna 10', 'España', 'david@cloudify.com'),
('D99887766', 'NetCorp', 'Calle Sol 45', 'España', 'pablo@netcorp.com');

-- Categorías
INSERT INTO categorias (nombre, descripcion) VALUES
('Desarrollo Web', 'Frontend y Backend'),
('Marketing', 'Publicidad, redes sociales, SEO'),
('Diseño', 'Diseño gráfico y UI/UX'),
('Administración', 'Oficina y tareas administrativas'),
('Ventas', 'Comercial y atención al cliente'),
('Recursos Humanos', 'Selección de personal'),
('Ciberseguridad', 'Seguridad informática'),
('DevOps', 'Infraestructura y despliegue'),
('Datos', 'Análisis y ciencia de datos'),
('Soporte', 'Atención técnica');

-- Vacantes
INSERT INTO vacantes (nombre, descripcion, fecha, salario, estatus, destacado, imagen, detalles, id_categoria, id_empresa) VALUES
('Frontend Developer', 'HTML, CSS y Angular', '2024-03-01', 25000, 'CREADA', 1, NULL, '', 1, 1),
('Especialista SEO', 'Optimización web', '2024-03-02', 23000, 'CREADA', 0, NULL, '', 2, 2),
('Diseñador UI', 'Diseño de interfaces', '2024-03-03', 24000, 'ASIGNADA', 1, NULL, '', 3, 3),
('Auxiliar Administrativo', 'Gestión de archivos', '2024-03-04', 20000, 'CREADA', 0, NULL, '', 4, 4),
('Comercial', 'Venta de productos', '2024-03-05', 18000, 'CREADA', 0, NULL, '', 5, 1),
('Técnico RRHH', 'Selección de candidatos', '2024-03-06', 22000, 'CREADA', 1, NULL, '', 6, 2),
('Analista Seguridad', 'Auditoría de sistemas', '2024-03-07', 27000, 'CANCELADA', 0, NULL, '', 7, 3),
('DevOps Engineer', 'CI/CD pipelines', '2024-03-08', 28000, 'CREADA', 1, NULL, '', 8, 4),
('Data Analyst', 'SQL y Power BI', '2024-03-09', 26000, 'ASIGNADA', 0, NULL, '', 9, 1),
('Soporte Técnico', 'Resolución de problemas', '2024-03-10', 21000, 'CREADA', 0, NULL, '', 10, 2);

-- Solicitudes (solo candidatos)
INSERT INTO solicitudes (fecha, archivo, comentarios, estado, curriculum, id_vacante, email) VALUES
('2024-03-11', 'cv_juan.pdf', 'Estoy interesado.', 0, 'cv_juan.pdf', 1, 'juan@correo.com'),
('2024-03-11', 'cv_ana.pdf', 'Buena oportunidad.', 1, 'cv_ana.pdf', 2, 'ana@correo.com'),
('2024-03-11', 'cv_lucia.pdf', 'Disponible inmediatamente.', 0, 'cv_lucia.pdf', 3, 'lucia@correo.com'),
('2024-03-11', 'cv_andrea.pdf', 'Gracias por considerar.', 2, 'cv_andrea.pdf', 4, 'andrea@correo.com'),
('2024-03-11', 'cv_nat.pdf', 'Experiencia en el sector.', 0, 'cv_nat.pdf', 5, 'natalia@correo.com'),
('2024-03-11', 'cv_juan2.pdf', 'Me interesa el reto.', 0, 'cv_juan2.pdf', 6, 'juan@correo.com'),
('2024-03-11', 'cv_ana2.pdf', 'Tengo formación específica.', 1, 'cv_ana2.pdf', 7, 'ana@correo.com'),
('2024-03-11', 'cv_lucia2.pdf', 'Gran oportunidad.', 2, 'cv_lucia2.pdf', 8, 'lucia@correo.com'),
('2024-03-11', 'cv_andrea2.pdf', 'Experiencia previa.', 0, 'cv_andrea2.pdf', 9, 'andrea@correo.com'),
('2024-03-11', 'cv_nat2.pdf', 'Adjunto mi CV.', 0, 'cv_nat2.pdf', 10, 'natalia@correo.com');