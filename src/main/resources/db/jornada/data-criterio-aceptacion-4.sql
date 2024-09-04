-- Concepto Laboral
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (1, 8, 6, true, 'Turno Normal');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (2, 6, 2, true, 'Turno Extra');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (3, null, null, false, 'Día Libre');

-- Empleado
INSERT INTO empleados (id, nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso)
VALUES (1, 30415653, 'German', 'Zotella', 'gzotella@gmail.com', '1980-01-06', '2019-06-04');

-- Semana 1: 48 horas en total (máximo permitido por semana es 52)
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (29, 1, 1, '2024-08-01', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (30, 1, 1, '2024-08-02', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (31, 1, 1, '2024-08-03', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (32, 1, 1, '2024-08-04', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (33, 1, 2, '2024-08-05', 6); -- Turno Extra
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (34, 1, 2, '2024-08-06', 6); -- Turno Extra
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (35, 1, 2, '2024-08-07', 4); -- Turno Extra

-- Semana 2: 48 horas en total
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (36, 1, 1, '2024-08-08', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (37, 1, 1, '2024-08-09', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (38, 1, 1, '2024-08-10', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (39, 1, 1, '2024-08-11', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (40, 1, 2, '2024-08-12', 6); -- Turno Extra
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (41, 1, 2, '2024-08-13', 6); -- Turno Extra
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (42, 1, 2, '2024-08-14', 4); -- Turno Extra

-- Semana 3: 48 horas en total
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (43, 1, 1, '2024-08-15', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (44, 1, 1, '2024-08-16', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (45, 1, 1, '2024-08-17', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (46, 1, 1, '2024-08-18', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (47, 1, 2, '2024-08-19', 6); -- Turno Extra
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (48, 1, 2, '2024-08-20', 6); -- Turno Extra
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (49, 1, 2, '2024-08-21', 4); -- Turno Extra

-- Semana 4: 44 horas en total (para alcanzar 188 horas mensuales)
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (50, 1, 1, '2024-08-22', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (51, 1, 1, '2024-08-23', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (52, 1, 1, '2024-08-24', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (53, 1, 1, '2024-08-25', 8); -- Turno Normal
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (54, 1, 2, '2024-08-26', 6); -- Turno Extra
INSERT INTO jornadas (id, id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (55, 1, 2, '2024-08-27', 6); -- Turno Extra