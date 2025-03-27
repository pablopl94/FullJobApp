package com.fulljob.api.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

public abstract class GenericCrudServiceImpl<E, ID> implements IGenericCrud<E, ID> {

    // Método que deben implementar las clases hijas para indicar qué repositorio usar
    protected abstract JpaRepository<E, ID> getRepository();

    // Devuelve todos los registros de la entidad
    @Override
    public List<E> findAll() {
        try {
        	return getRepository().findAll();
        }catch (Exception e) {
			throw new ServiceException("Error al recuperar la lista con todos", e);
		}
    }

    // Busca un registro por su ID. Si el ID es nulo, lanza excepción.
    @Override
    public Optional<E> findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        try {
        	return getRepository().findById(id);
        }catch(Exception e) {
        	throw new ServiceException("No se puede acceder a la entidad con el ID: "+ id, e);
        }
    }

    // Guarda una nueva entidad en la base de datos. No permite guardar si es null.
    @Override
    @Transactional
    public E insertOne(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        try {
        	return getRepository().save(entity);
        }catch(Exception e) {
        	throw new ServiceException("Fallo al intentar actualizar la entidad", e);
        }
    }

    
    //Actualiza una entidad existente. Si falla algo, lanza una excepción genérica.
    @Override
    @Transactional
    public E updateOne(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }

        try {
            return getRepository().save(entity);
        } catch (Exception e) {
            throw new RuntimeException("Fallo al intentar actualizar la entidad", e);
        }
    }

    // Elimina una entidad por ID. Comprueba que exista y controla posibles errores.
    @Override
    @Transactional
    public void deleteOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        try {
            if (!getRepository().existsById(id)) {
                throw new EntityNotFoundException("No se encontró la entidad con ID: " + id);
            }

            getRepository().deleteById(id);

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la entidad con ID: " + id, e);
        }
    }
}
