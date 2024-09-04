package com.gestor.turnos_rotativos.mapper;

import java.util.List;

public interface DataMapper<E, D>  {

    public D toDto(E entity);

    public List<D> toDtos(List<E> E);
}
