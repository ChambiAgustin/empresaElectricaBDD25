-- Script para resolver las siguientes consultas:

-- Devolver  por  cada  reclamo,  el  detalle  de  materiales  utilizados  para solucionarlo, si un reclamo no uso materiales, listarlo también.
SELECT
	r.num_reclamo,
	r.fecha AS fecha_reclamo,
	m.descripcion AS material_utilizado,
	req.cantidad AS cantidad_requerida
FROM
	reclamo r
LEFT JOIN
	requiere req ON r.num_reclamo = req.num_reclamo -- Incluye reclamos AUNQUE no hayan requerido materiales
LEFT JOIN
	material m ON req.cod_material = m.cod_material
ORDER BY
	r.num_reclamo DESC, material_utilizado;

-- Devolver los usuarios que tienen más de un reclamo. 
SELECT
	u.id_usuario,
	u.direccion,
	COUNT(r.num_reclamo) AS total_reclamos
FROM
	usuario u
JOIN
	reclamo r ON u.id_usuario = r.id_usuario
GROUP BY
	u.id_usuario
HAVING
	total_reclamos > 1
ORDER BY
	total_reclamos DESC;

-- Listado  de  reclamos  que  fueron  asignados  a  más  de  un  empleado  de mantenimiento.
SELECT
	r.num_reclamo,
	r.fecha AS fecha_reclamo,
	COUNT(a.id_empleado) AS empleados_asignados
FROM
	reclamo r
JOIN
	atiende a ON r.num_reclamo = a.num_reclamo
GROUP BY
	r.num_reclamo
HAVING
	empleados_asignados > 1
ORDER BY
	empleados_asignados DESC;