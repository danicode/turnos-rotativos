package com.gestor.turnos_rotativos.mapper;

public interface GenericExtendedMapper<E, D, F, P1, P2> extends DataMapper<E, D>, ToEntityExtended<E, F, P1, P2> {
}
