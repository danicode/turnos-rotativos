-- Concepto Laboral
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (1, 8, 6, true, 'Turno Normal');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (2, 6, 2, true, 'Turno Extra');
INSERT INTO concepto_laboral(id, hs_maximo, hs_minimo, laborable, nombre) VALUES (3, null, null, false, 'Día Libre');

-- Empleado 1
INSERT INTO empleados (id, nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso)
VALUES (1, 30415653, 'German', 'Zotella', 'gzotella@gmail.com', '1980-01-06', '2019-06-04');

-- Empleado 2
INSERT INTO empleados (id, nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso)
VALUES (2, 29415653, 'Pepe', 'Argento', 'pargento@gmail.com', '1982-05-04', '2018-03-06');

-- Empleado 3
INSERT INTO empleados (id, nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso)
VALUES (3, 28415653, 'Sebastian', 'Fernandez', 'sfernandez@gmail.com', '1981-05-04', '2020-03-06');

-- Insertar jornada 1
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES (1, 1, '2024-08-12', 8);

-- Insertar jornada 2
INSERT INTO jornadas (id_empleado, id_concepto, fecha, horas_trabajadas)
VALUES (2, 1, '2024-08-12', 8);
