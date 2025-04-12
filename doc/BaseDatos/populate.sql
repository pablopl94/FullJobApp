USE vacantes_BBDD_2025_RETO;

-- ================== USUARIOS ==================
-- CONTRASEÑA PARA TODOS 123456
INSERT INTO Usuarios (email, nombre, apellidos, password, enabled, fecha_registro, rol) VALUES
('admin@admin.com', 'Pedro', 'Herrero', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-01', 'ADMON'),
('empresa@empresa.com', 'Elena', 'Cabrera', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-05', 'EMPRESA'),
('cliente@cliente.com', 'Jose', 'Benito', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-10', 'CLIENTE'),
('pacoporras@cliente.com', 'Paco', 'Porras', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-10', 'CLIENTE'),
('info@innovapps.com', 'Carlos', 'López', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-01', 'EMPRESA'),
('contact@datainsight.com', 'Ana', 'Martínez', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-02', 'EMPRESA'),
('info@softtechpartners.com', 'Luis', 'Rodríguez', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-03', 'EMPRESA'),
('support@cloudservesolutions.com', 'Sofía', 'García', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-04', 'EMPRESA'),
('hello@greencode.com', 'Miguel', 'Hernández', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-05', 'EMPRESA');

-- ================== EMPRESA ==================
INSERT INTO Empresas (cif, nombre_empresa, direccion_fiscal, pais, email) VALUES
('A98765432', 'InnovApps', 'Calle Creativa 50', 'España', 'empresa@empresa.com'),
('Z12312345', 'DataInsight', 'Avenida de los Datos 101', 'España', 'contact@datainsight.com'),
('C23456789', 'SoftTech Partners', 'Ronda del Progreso 10', 'España', 'info@softtechpartners.com'),
('D34567890', 'CloudServe Solutions', 'Paseo de la Nube 88', 'España', 'support@cloudservesolutions.com'),
('E45678901', 'GreenCode Technologies', 'Avenida Verde 12', 'España', 'hello@greencode.com');


-- ================== CATEGORÍAS ==================
INSERT INTO Categorias (nombre, descripcion) VALUES
('Desarrollo Web', 'Puestos relacionados con programación frontend y backend'),
('Diseño Gráfico', 'Diseño de interfaces, logos, y material visual'),
('Administración de Sistemas', 'Gestión de redes, servidores y sistemas operativos'),
('Inteligencia Artificial', 'Modelos de ML, Deep Learning, procesamiento de lenguaje natural'),
('Ciberseguridad', 'Análisis de vulnerabilidades y defensa de sistemas'),
('Soporte Técnico', 'Atención a usuarios y solución de incidencias'),
('Marketing Digital', 'SEO, SEM, redes sociales y analítica web'),
('Gestión de Proyectos', 'Scrum Master, Product Owner, planificación y gestión ágil'),
('Base de Datos', 'Administración, diseño y optimización de bases de datos'),
('Testing QA', 'Automatización de pruebas y control de calidad del software');


-- ================== VACANTES ==================
INSERT INTO Vacantes (nombre, descripcion, fecha, salario, estatus, destacado, imagen, detalles, id_categoria, id_empresa) VALUES
('Desarrollador Backend', 'Se busca programador con experiencia en Java y Spring.', '2025-04-01', 32000, 'CREADA', 1, '', 'INDEFINIDO', 1, 1),
('Analista de Datos', 'Experto en SQL, Python y herramientas de BI.', '2025-04-02', 30000, 'CREADA', 1, '', 'TEMPORAL', 2, 2),
('Arquitecto de Software', 'Liderar la arquitectura de una plataforma SaaS.', '2025-04-03', 40000, 'CREADA', 0, '', 'INDEFINIDO', 1, 3),
('DevOps Engineer', 'Automatización de pipelines y mantenimiento de infraestructuras en AWS.', '2025-04-04', 35000, 'CREADA', 0, '', 'AUTONOMO', 3, 4),
('Diseñador Gráfico', 'Diseño de material publicitario, branding y material digital.', '2025-04-05', 28000, 'CREADA', 1, '', 'PRACTICAS', 2, 5),
('Frontend Developer', 'Experiencia en Angular, React o Vue.', '2025-04-06', 31000, 'CREADA', 0, '', 'INDEFINIDO', 1, 1),
('Especialista en IA', 'Implementar modelos de machine learning.', '2025-04-07', 42000, 'CREADA', 1, '', 'INDEFINIDO', 4, 2),
('Técnico de Soporte', 'Atención a usuarios y resolución de problemas.', '2025-04-08', 25000, 'CREADA', 0, '', 'TEMPORAL', 6, 3),
('Administrador de Sistemas', 'Mantenimiento de servidores Linux y Windows.', '2025-04-09', 33000, 'CREADA', 0, '', 'INDEFINIDO', 3, 4),
('Consultor SEO', 'Estrategias de posicionamiento web.', '2025-04-10', 29000, 'CREADA', 1, '', 'INDEFINIDO', 7, 5),
('Tester QA', 'Pruebas automatizadas y manuales.', '2025-04-11', 27000, 'CREADA', 0, '', 'INDEFINIDO', 10, 1),
('Scrum Master', 'Facilitador en equipos ágiles.', '2025-04-12', 36000, 'CREADA', 1, '', 'INDEFINIDO', 8, 2),
('Diseñador UX', 'Prototipado y experiencia de usuario.', '2025-04-13', 29500, 'CREADA', 0, '', 'PRACTICAS', 2, 3),
('DBA MySQL', 'Gestión avanzada de bases de datos MySQL.', '2025-04-14', 34000, 'CREADA', 0, '', 'INDEFINIDO', 9, 4),
('Especialista en Seguridad', 'Auditoría y hardening de sistemas.', '2025-04-15', 38000, 'CREADA', 1, '', 'INDEFINIDO', 5, 5);

-- ================== SOLICITUD ==================
INSERT INTO Solicitudes (fecha, archivo, comentarios, estado, curriculum, id_Vacante, email) VALUES
('2025-03-05', 'cv_jose.pdf', 'Me interesa mucho esta vacante, gracias.', 0, 'cv_jose.pdf', 1, 'cliente@cliente.com'),
('2025-03-06', 'cv_jose.pdf', 'Tengo experiencia previa en el sector.', 0, 'cv_jose.pdf', 2, 'cliente@cliente.com'),
('2025-03-11', 'cv_jose.pdf', 'Puedo incorporarme inmediatamente.', 0, 'cv_jose.pdf', 7, 'cliente@cliente.com'),
('2025-03-12', 'cv_jose.pdf', 'Tengo experiencia liderando equipos.', 0, 'cv_jose.pdf', 8, 'cliente@cliente.com'),
('2025-03-13', 'cv_jose.pdf', 'Fuerte interés en el desarrollo profesional.', 0, 'cv_jose.pdf', 9, 'cliente@cliente.com'),
('2025-03-14', 'cv_jose.pdf', 'Soy una persona proactiva y resolutiva.', 0, 'cv_jose.pdf', 10, 'cliente@cliente.com');
