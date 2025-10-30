-- Definición del diccionario
DROP DATABASE IF EXISTS empresa_electrica;
CREATE DATABASE empresa_electrica;
USE empresa_electrica;


CREATE TABLE usuario(
	id_usuario INT UNSIGNED NOT NULL AUTO_INCREMENT,
	direccion VARCHAR(100) NOT NULL,
	CONSTRAINT pkusuario PRIMARY KEY (id_usuario)
);

CREATE TABLE usuario_tel(
	id_usuario INT UNSIGNED NOT NULL,
	telefono VARCHAR(20) NOT NULL,
	CONSTRAINT pkusuario_tel PRIMARY KEY (id_usuario, telefono),
	CONSTRAINT fkusuario_tel FOREIGN KEY (id_usuario)
		REFERENCES usuario (id_usuario)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE TABLE empresa(
	id_usuario INT UNSIGNED NOT NULL,
	cuit VARCHAR(20) NOT NULL UNIQUE,
	razon_social VARCHAR(100) NOT NULL,
	capacidad SMALLINT UNSIGNED, -- va de 0 a 65535, alcanza para guardar la capacidad de hasta 50000 
	CONSTRAINT pkempresa PRIMARY KEY (id_usuario),
	CONSTRAINT fkempresa_id FOREIGN KEY (id_usuario)
		REFERENCES usuario (id_usuario)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT chk_capacidad_inst CHECK (capacidad <= 50000)
);

CREATE TABLE persona(
	id_usuario INT UNSIGNED NOT NULL,
	dni INT UNSIGNED NOT NULL UNIQUE, -- no puedo usar MediumInt porque llego solo hasta 16.777.215
	nombre VARCHAR(100) NOT NULL,
	apellido VARCHAR(100) NOT NULL,
	CONSTRAINT pkpersona PRIMARY KEY (id_usuario),
	CONSTRAINT fkpersona_id FOREIGN KEY (id_usuario)
		REFERENCES usuario (id_usuario)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT chk_dni_val CHECK (dni <= 1000000000)
);

CREATE TABLE empleado(
	id_usuario INT UNSIGNED NOT NULL,
	dni INT UNSIGNED NOT NULL UNIQUE, -- no puedo usar MediumInt porque llego solo hasta 16.777.215
	nombre VARCHAR(100) NOT NULL,
	apellido VARCHAR(100) NOT NULL,
	sueldo DECIMAL(10,2),
	CONSTRAINT pkempleado PRIMARY KEY (id_usuario),
	CONSTRAINT fkempleado_id FOREIGN KEY (id_usuario)
		REFERENCES usuario (id_usuario)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT chk_dni_emp_val CHECK (dni <= 1000000000),
	CONSTRAINT chk_sueldo_val CHECK (sueldo >= 0)
);

CREATE TABLE motivo(
	cod_motivo INT UNSIGNED NOT NULL AUTO_INCREMENT,
	descripcion VARCHAR(500),
	CONSTRAINT pkmotivo PRIMARY KEY (cod_motivo)
);

CREATE TABLE reclamo(
	num_reclamo INT UNSIGNED NOT NULL AUTO_INCREMENT,
	fecha_res DATE,
	id_usuario INT UNSIGNED NOT NULL,
	fecha DATE NOT NULL,
	hora TIME NOT NULL,
	cod_motivo INT UNSIGNED,
	CONSTRAINT pkreclamo PRIMARY KEY (num_reclamo),
	CONSTRAINT fkusuario_reclamo FOREIGN KEY (id_usuario)
		REFERENCES usuario (id_usuario)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT fkmotivo_reclamo FOREIGN KEY (cod_motivo)
		REFERENCES motivo (cod_motivo)
		ON DELETE SET NULL
		ON UPDATE CASCADE
);

CREATE TABLE llamado(
	num_reclamo INT UNSIGNED NOT NULL,
	num_llamado SMALLINT UNSIGNED NOT NULL, -- permite hasta 65.535 llamados
	fecha DATE NOT NULL,
	hora TIME NOT NULL,
	CONSTRAINT pkllamado PRIMARY KEY (num_reclamo, num_llamado),
	CONSTRAINT fkrec_llamado FOREIGN KEY (num_reclamo)
		REFERENCES reclamo (num_reclamo)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE TABLE material(
	cod_material INT UNSIGNED NOT NULL AUTO_INCREMENT,
	descripcion VARCHAR(500),
	CONSTRAINT pkmaterial PRIMARY KEY (cod_material)
);

CREATE TABLE atiende(
	id_empleado INT UNSIGNED NOT NULL,
	num_reclamo INT UNSIGNED NOT NULL,
	CONSTRAINT pkatiende PRIMARY KEY (id_empleado, num_reclamo),
	CONSTRAINT fkempleado_at FOREIGN KEY (id_empleado)
		REFERENCES empleado (id_usuario)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT fkreclamo_at FOREIGN KEY (num_reclamo)
		REFERENCES reclamo (num_reclamo)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE TABLE requiere(
	num_reclamo INT UNSIGNED NOT NULL,
	cod_material INT UNSIGNED NOT NULL,
	cantidad INT UNSIGNED,
	CONSTRAINT pkrequiere PRIMARY KEY (num_reclamo, cod_material),
	CONSTRAINT fkreclamo_req FOREIGN KEY (num_reclamo)
		REFERENCES reclamo (num_reclamo)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT fkmaterial_req FOREIGN KEY (cod_material)
		REFERENCES material (cod_material)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE TABLE reclamos_eliminados(
	num_reclamo INT UNSIGNED NOT NULL,
	fecha_res DATE,
	id_usuario INT UNSIGNED NOT NULL,
	fecha DATE NOT NULL,
	hora TIME NOT NULL,
	cod_motivo INT UNSIGNED,
	fecha_eliminacion DATETIME NOT NULL,
	usuario_bd VARCHAR(256) NOT NULL,
	CONSTRAINT pkreclamo_eliminado PRIMARY KEY (num_reclamo, fecha_eliminacion)
);

DELIMITER //
CREATE TRIGGER trigger_nuevo_llamado
	BEFORE INSERT ON llamado
	FOR EACH ROW
	BEGIN
		SELECT COALESCE(MAX(num_llamado), 0) + 1 
		INTO @siguiente_num
		FROM llamado
		WHERE num_reclamo = NEW.num_reclamo;

		SET NEW.num_llamado = @siguiente_num;
	END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER trigger_auditoria_eliminacion_reclamo
	AFTER DELETE ON reclamo
	FOR EACH ROW
	BEGIN
		INSERT INTO reclamos_eliminados (num_reclamo, fecha_res, id_usuario, fecha, hora, cod_motivo, fecha_eliminacion, usuario_bd)
		VALUES (OLD.num_reclamo, OLD.fecha_res, OLD.id_usuario, OLD.fecha, OLD.hora, OLD.cod_motivo, NOW(), CURRENT_USER());
	END;
//
DELIMITER ;