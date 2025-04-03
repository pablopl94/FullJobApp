USE vacantes_BBDD_2025_RETO;

-- ================== USUARIOS ==================
-- CONTRASEÑA PARA TODOS 123456
INSERT INTO Usuarios (email, nombre, apellidos, password, enabled, fecha_registro, rol) VALUES
('admin@admin.com', 'Pedro', 'Herrero', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-01', 'ADMON'),
('empresa@empresa.com', 'Elena', 'Cabrera', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-05', 'EMPRESA'),
('cliente@cliente.com', 'Jose', 'Benito', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-10', 'CLIENTE'),
('info@innovapps.com', 'Carlos', 'López', 'passwordhash', 1, '2025-01-01', 'EMPRESA'),
('contact@datainsight.com', 'Ana', 'Martínez', 'passwordhash', 1, '2025-01-02', 'EMPRESA'),
('info@softtechpartners.com', 'Luis', 'Rodríguez', 'passwordhash', 1, '2025-01-03', 'EMPRESA'),
('support@cloudservesolutions.com', 'Sofía', 'García', 'passwordhash', 1, '2025-01-04', 'EMPRESA'),
('hello@greencode.com', 'Miguel', 'Hernández', 'passwordhash', 1, '2025-01-05', 'EMPRESA');

-- ================== EMPRESA ==================
INSERT INTO Empresas (cif, nombre_empresa, direccion_fiscal, pais, email) VALUES
('A98765432', 'InnovApps', 'Calle Creativa 50', 'España', 'info@innovapps.com'),
('Z12312345', 'DataInsight', 'Avenida de los Datos 101', 'España', 'contact@datainsight.com'),
('C23456789', 'SoftTech Partners', 'Ronda del Progreso 10', 'España', 'info@softtechpartners.com'),
('D34567890', 'CloudServe Solutions', 'Paseo de la Nube 88', 'España', 'support@cloudservesolutions.com'),
('E45678901', 'GreenCode Technologies', 'Avenida Verde 12', 'España', 'hello@greencode.com');


-- ================== CATEGORÍAS ==================
INSERT INTO Categorias (nombre, descripcion) VALUES
('Desarrollo Web', 'Puestos relacionados con programación frontend y backend'),
('Diseño Gráfico', 'Diseño de interfaces, logos, y material visual');

-- ================== VACANTES ==================
INSERT INTO Vacantes (nombre, descripcion, fecha, salario, estatus, destacado, imagen, detalles, id_categoria, id_empresa) VALUES
('Desarrollador Backend', 'Se busca programador con experiencia en Java y Spring.', '2025-04-01', 32000, 'CREADA', 1, '', 'Horario flexible, teletrabajo parcial.', 1, 1),
('Analista de Datos', 'Experto en SQL, Python y herramientas de BI.', '2025-04-02', 30000, 'CREADA', 1, '', 'Equipo multidisciplinar, proyecto a largo plazo.', 2, 2),
('Arquitecto de Software', 'Liderar la arquitectura de una plataforma SaaS.', '2025-04-03', 40000, 'CREADA', 0, '', 'Oportunidad de crecimiento y formación continua.', 1, 3),
('DevOps Engineer', 'Automatización de pipelines y mantenimiento de infraestructuras en AWS.', '2025-04-04', 35000, 'CREADA', 0, '', 'Entorno innovador y equipo internacional.', 1, 4),
('Diseñador Gráfico', 'Diseño de material publicitario, branding y material digital.', '2025-04-05', 28000, 'CREADA', 1, '', 'Entorno creativo, proyectos variados.', 2, 5);


-- ================== SOLICITUD ==================
INSERT INTO Solicitudes (fecha, archivo, comentarios, estado, curriculum, id_Vacante, email) VALUES
('2025-03-05', 'cv_jose.pdf', 'Me interesa mucho esta vacante, gracias.', 0, 'cv_jose.pdf', 1, 'cliente@cliente.com');
