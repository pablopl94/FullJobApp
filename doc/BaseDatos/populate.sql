USE vacantes_BBDD_2025_RETO;

-- ================== USUARIOS ==================
-- CONTRASEÑA PARA TODOS 123456
INSERT INTO Usuarios (email, nombre, apellidos, password, enabled, fecha_registro, rol) VALUES
('admin@admin.com', 'Pedro', 'Herrero', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-01', 'ADMON'),
('empresa@empresa.com', 'Elena', 'Cabrera', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-05', 'EMPRESA'),
('cliente@cliente.com', 'Jose', 'Benito', '$2a$12$F1iPJsRuBH0Ap9p.XYe3geIteE62QEvh4yl/NBp1VYGYyuRb84fI2', 1, '2025-01-10', 'CLIENTE'); 

-- ================== EMPRESA ==================
INSERT INTO Empresas (cif, nombre_empresa, direccion_fiscal, pais, email) VALUES
('B12345678', 'TechSoft Solutions', 'Calle Inventada 123', 'España', 'empresa@empresa.com');

-- ================== CATEGORÍAS ==================
INSERT INTO Categorias (nombre, descripcion) VALUES
('Desarrollo Web', 'Puestos relacionados con programación frontend y backend'),
('Diseño Gráfico', 'Diseño de interfaces, logos, y material visual');

-- ================== VACANTES ==================
INSERT INTO Vacantes (nombre, descripcion, fecha, salario, estatus, destacado, imagen, detalles, id_categoria, id_empresa) VALUES
('Desarrollador Frontend', 'Buscamos frontend con experiencia en Angular o React.', '2025-03-01', 27000, 'CREADA', 1, '', 'Horario flexible, equipo joven.', 1, 1),
('Diseñador UI/UX', 'Diseñador con experiencia en Figma y Adobe XD.', '2025-03-02', 25000, 'CREADA', 0, '', 'Proyecto innovador en el sector e-learning.', 2, 1);

-- ================== SOLICITUD ==================
INSERT INTO Solicitudes (fecha, archivo, comentarios, estado, curriculum, id_Vacante, email) VALUES
('2025-03-05', 'cv_jose.pdf', 'Me interesa mucho esta vacante, gracias.', 0, 'cv_jose.pdf', 1, 'cliente@cliente.com');
