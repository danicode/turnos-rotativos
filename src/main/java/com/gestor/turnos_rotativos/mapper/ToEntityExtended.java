package com.gestor.turnos_rotativos.mapper;

public interface ToEntityExtended<E, D, P1, P2> {

    public E toEntity(D dto, P1 param1, P2 param2);
}
