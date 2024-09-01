package com.gestor.turnos_rotativos.mapper;

public interface UpdateMapper<E, D> {
    public void updateEntityFromDto(E entity, D dto);
}
