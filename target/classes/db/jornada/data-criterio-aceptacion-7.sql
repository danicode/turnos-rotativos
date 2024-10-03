-- Concepto Laboral
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (1, 8, 6, true, 'Turno Normal');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (2, 6, 2, true, 'Turno Extra');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (3, null, null, false, 'Día Libre');

-- Empleado
INSERT INTO empleados (id, nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso)
VALUES (1, 30415653, 'German', 'Zotella', 'gzotella@gmail.com', '1980-01-06', '2019-06-04');

-- Insertar 5 turnos normales en semana
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES (1, 1, '2024-08-12', 8);

INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES (1, 1, '2024-08-13', 8);

INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES (1, 1, '2024-08-14', 8);

INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES (1, 1, '2024-08-15', 8);

INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES (1, 1, '2024-08-15', 8);

