package com.gestor.turnos_rotativos.mapper;

public interface ToEntity<E, D> {

    public E toEntity(D dto);
}
