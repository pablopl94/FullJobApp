package com.fulljob.api.services;

import java.util.List;
import java.util.Optional;

/**
 * Esta interfaz la hemos creado en grupo para tener una base común con las funciones
 * básicas que casi todas las entidades necesitan (crear, leer, actualizar y eliminar).
 * 
 * Así, en vez de repetir el mismo código en cada clase, usamos esta interfaz y lo reutilizamos.
 *
 * @param <E> Tipo de entidad que vamos a usar (por ejemplo: Empresa, Vacante...).
 * @param <ID> Tipo del identificador (puede ser un número, un string... depende de la entidad).
 */
public interface IGenericCrud<E, ID> {

    /**
     * Devuelve una lista con todos los elementos guardados de ese tipo.
     *
     * @return lista con todos los elementos encontrados.
     */
    List<E> findAll();

    /**
     * Busca un elemento por su ID (identificador único).
     *
     * @param id el valor con el que queremos buscar.
     * @return un Optional que puede traer el resultado o estar vacío si no existe.
     */
    Optional<E> findById(ID id);

    /**
     * Guarda un nuevo elemento en la base de datos.
     *
     * @param entity el objeto que queremos guardar.
     * @return el objeto guardado.
     */
    E insertOne(E entity);

    /**
     * Actualiza un objeto que ya existe con nuevos datos.
     *
     * @param entity el objeto con los cambios.
     * @return el objeto actualizado.
     */
    E updateOne(E entity); 

    /**
     * Borra un objeto de la base de datos usando su ID.
     *
     * @param id el identificador del objeto que queremos eliminar.
     */
    void deleteOne(ID id);
}
