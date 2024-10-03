-- Concepto Laboral
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (1, 8, 6, true, 'Turno Normal');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (2, 6, 2, true, 'Turno Extra');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (3, null, null, false, 'Día Libre');

-- Empleado
INSERT INTO empleados (id, nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso)
VALUES (1, 30415653, 'German', 'Zotella', 'gzotella@gmail.com', '1980-01-06', '2019-06-04');

-- Semana
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (1, 1, '2024-08-26', 8); -- Turno Normal
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (1, 1, '2024-08-27', 8); -- Turno Normal
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (1, 1, '2024-08-28', 8); -- Turno Normal
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (1, 1, '2024-08-29', 8); -- Turno Normal
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (1, 1, '2024-08-30', 8); -- Turno Normal
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (1, 2, '2024-08-26', 6); -- Turno Extra
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas) VALUES (1, 2, '2024-08-27', 6); -- Turno Extra
