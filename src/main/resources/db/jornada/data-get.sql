-- Concepto Laboral
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (1, 8, 6, true, 'Turno Normal');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (2, 6, 2, true, 'Turno Extra');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (3, null, null, false, 'Día Libre');

-- Empleados
INSERT INTO empleados (nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso)
VALUES (12345678, 'Juan', 'Perez', 'juan.perez@example.com', '1980-05-15', '2020-01-01');

INSERT INTO empleados (nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso)
VALUES (87654321, 'Maria', 'Gomez', 'maria.gomez@example.com', '1990-08-25', '2019-03-15');

-- Empleado sin jornadas
INSERT INTO empleados (nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso)
VALUES (23456789, 'Carlos', 'Fernandez', 'cfernandez@example.com', '1994-02-25', '2021-06-10');

-- Jornadas
-- Jornadas para el empleado con nro_documento = 12345678 (Turno Normal y Día Libre)
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 12345678), 1, '2024-09-01', 7);

INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 12345678), 1, '2024-09-05', 8);

INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 12345678), 3, '2024-09-07', 0);

-- Jornadas para el empleado con nro_documento = 87654321 (Turno Extra y Turno Normal)
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 87654321), 2, '2024-09-02', 4);

INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 87654321), 1, '2024-09-03', 6);

-- Jornadas adicionales para diferentes fechas y empleados
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 12345678), 2, '2024-09-10', 5);

INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 87654321), 1, '2024-09-09', 7);

-- Jornada para el empleado con nro_documento = 12345678 con horas_trabajadas igual a NULL
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 12345678), 1, '2024-09-11', NULL);

-- Jornada fuera del rango esperado (fecha antes de 2024)
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 12345678), 1, '2023-08-25', 6);

-- Jornada fuera del rango esperado (fecha posterior a 2024)
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 87654321), 1, '2025-01-15', 8);

-- Dos jornadas el mismo día para el mismo empleado con diferentes conceptos
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 12345678), 1, '2024-09-06', 5);

INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES ((SELECT id FROM empleados WHERE nro_documento = 12345678), 2, '2024-09-06', 3);
