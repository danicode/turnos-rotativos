package com.gestor.turnos_rotativos.mapper;

import java.util.List;

public interface GenericMapper<E, D>  {

    public D toDto(E entity);

    public List<D> toDtos(List<E> E);

    public E toEntity(D dto);

    public void updateEntityFromDto(E entity, D dto);
}
