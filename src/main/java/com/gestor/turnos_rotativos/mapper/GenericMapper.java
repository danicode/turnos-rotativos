package com.gestor.turnos_rotativos.mapper;

public interface GenericMapper <E, D> extends DataMapper<E, D>, ToEntity<E, D>, UpdateMapper<E, D> {
}
