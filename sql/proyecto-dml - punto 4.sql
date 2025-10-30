-- Scrip para la carga de información en la base de datos del proyecto
INSERT INTO usuario (direccion) VALUES
('Av. Marcelo T. Alvear 704'),
('Belgrano 412'),
('Ruta 9 KM 15'),
('Rivadavia 987'),
('Lavalle 332'),
('Av. Italia 560'),
('Pellegrini 100'),  
('San Juan 200'),
('9 de Julio 50');

INSERT INTO usuario_tel (id_usuario, telefono) VALUES
(1, '3584001234'),
(2, '3584556677'),
(3, '3584122233'),
(4, '3584333444'),
(5, '3584768899'),
(6, '3584655544'),
(7, '3584770011');

INSERT INTO empresa (id_usuario, cuit, razon_social, capacidad) VALUES
(1, '30-56789012-3', 'Energia del Sur S.A.', 500),
(2, '30-12345678-9', 'Distribuidora Rio Ltda.', 350),
(3, '30-99887766-5', 'Agro El Molino S.A.', 600);

INSERT INTO persona (id_usuario, dni, nombre, apellido) VALUES
(4, 32456789, 'María', 'López'),
(5, 29876543, 'Juan', 'Pérez'),
(6, 33890211, 'Sofía', 'Mártinez');

INSERT INTO empleado (id_usuario, dni, nombre, apellido, sueldo) VALUES
(7, 27123456, 'Ana', 'Torres', 520000),
(8, 28345678, 'José', 'Fernández', 480000),
(9, 30567890, 'Laura', 'Benítez', 510000);

INSERT INTO motivo (descripcion) VALUES
('Corte de energia'),
('Medidor dañado'),
('Tension baja'),
('Cableado defectuoso');

INSERT INTO reclamo(fecha_res, id_usuario, fecha, hora, cod_motivo) VALUES
('2025-10-10', 4, '2025-10-09', '09:45:00', 1),
('2025-10-11', 1, '2025-10-10', '10:20:20', 2),
(NULL, 5, '2025-10-11', '11:00:12', 3),
('2025-10-12', 2, '2025-10-11', '16:45:40', 4),
('2025-10-13', 6, '2025-10-12', '14:10:23', 1),
('2025-10-14', 7, '2025-10-13', '09:30:12', 2),
('2025-10-15', 3, '2025-10-14', '17:00:51', 3);

INSERT INTO llamado(num_reclamo, fecha, hora) VALUES
(1, '2025-10-09', '09:40:00'),
(1, '2025-10-09', '10:00:00'),
(2, '2025-10-10', '10:15:57'),
(3, '2025-10-11', '10:55:23'),
(4, '2025-10-11', '16:30:22'),
(5, '2025-10-12', '14:05:42'),
(6, '2025-10-13', '09:25:32'),
(7, '2025-10-14', '16:50:36');

INSERT INTO material (descripcion) VALUES
('Cable trifasico 4mm'),
('Fusible10A'),
('Medidor Digital'),
('Transformador 50kVA');

INSERT INTO atiende (id_empleado, num_reclamo) VALUES
(7, 1),
(8, 1),
(9, 2),
(7, 3),
(8, 4),
(9, 5),
(7, 6);

INSERT INTO requiere (num_reclamo, cod_material, cantidad) VALUES
(1, 1, 10),
(2, 2, 4),
(3, 3, 1),
(4, 4, 2),
(5, 1, 6),
(6, 2, 3),
(7, 3, 1);

SELECT COUNT(*) AS usuarios FROM usuario;
SELECT COUNT(*) AS reclamos FROM reclamo;
SELECT * FROM usuario LIMIT 5;
SELECT * FROM reclamo LIMIT 5;
SELECT * FROM atiende LIMIT 5;
SELECT * FROM requiere LIMIT 5;

-- DELETE FROM reclamo;