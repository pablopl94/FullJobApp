package com.fulljob.api.services;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica para operaciones CRUD (Crear, Leer, Actualizar y Eliminar).
 * 
 * @param <E> Tipo de la entidad que se va a manejar (por ejemplo, Vacante, Empresa...).
 * @param <ID> Tipo del identificador de la entidad (por ejemplo, Long, String...).
 */
public interface IGenericCrud<E, ID> {

    /**
     * Obtiene una lista con todos los elementos de la entidad.
     *
     * @return lista de elementos encontrados.
     */
    List<E> findAll();

    /**
     * Busca un elemento por su identificador.
     *
     * @param id identificador del elemento.
     * @return un Optional que puede contener el elemento si se encuentra.
     */
    Optional<E> findById(ID id);

    /**
     * Inserta un nuevo elemento en la base de datos.
     *
     * @param entity el objeto que se quiere guardar.
     * @return el objeto guardado.
     */
    E insertOne(E entity);

    /**
     * Actualiza un elemento existente.
     *
     * @param entity el objeto con los datos actualizados.
     * @return el objeto actualizado.
     */
    E updateOne(E entity); 

    /**
     * Elimina un elemento por su identificador.
     *
     * @param id identificador del elemento a eliminar.
     */
    void deleteOne(ID id);
}
