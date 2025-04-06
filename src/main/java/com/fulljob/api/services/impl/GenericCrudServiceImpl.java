package com.fulljob.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fulljob.api.services.IGenericCrud;

import jakarta.persistence.EntityNotFoundException;

/**
 * Esta clase la hemos creado como base para los servicios que hacen operaciones
 * CRUD.
 * 
 * Así evitamos repetir el mismo código en cada clase de servicio (como Empresa,
 * Vacante, etc.).
 * 
 * Las clases que extienden de esta tienen que indicar qué repositorio van a
 * usar implementando el método getRepository().
 *
 * @param <E>  Tipo de la entidad (por ejemplo, Empresa, Vacante...).
 * @param <ID> Tipo del identificador (por ejemplo, Integer, String...).
 */
@Service
public abstract class GenericCrudServiceImpl<E, ID> implements IGenericCrud<E, ID> {

	// Este método lo tienen que implementar las clases hijas para decir qué
	// repositorio usan
	protected abstract JpaRepository<E, ID> getRepository();

	/**
	 * Devuelve todos los registros de la entidad desde la base de datos. Si hay
	 * algún problema, lanza una excepción con el error.
	 */
	@Override
	public List<E> findAll() {
		try {
			return getRepository().findAll();
		} catch (Exception e) {
			throw new ServiceException("Error al recuperar la lista con todos", e);
		}
	}

	/**
	 * Busca un registro por su ID. Si el ID es nulo, lanza una excepción. Si hay
	 * algún problema con la búsqueda, también lanza una excepción.
	 */
	@Override
	public Optional<E> findById(ID id) {
		if (id == null) {
			throw new IllegalArgumentException("El ID no puede ser nulo");
		}
		try {
			return getRepository().findById(id);
		} catch (Exception e) {
			throw new ServiceException("No se puede acceder a la entidad con el ID: " + id, e);
		}
	}

	/**
	 * Inserta una nueva entidad en la base de datos. Si la entidad es nula, lanza
	 * una excepción. Esta operación es transaccional, por si algo falla, no se
	 * guarda nada.
	 */
	@Override
	@Transactional
	public E insertOne(E entity) {
		if (entity == null) {
			throw new IllegalArgumentException("La entidad no puede ser nula");
		}
		try {
			return getRepository().save(entity);
		} catch (Exception e) {
			throw new ServiceException("Fallo al intentar guardar la entidad", e);
		}
	}

	/**
	 * Actualiza una entidad que ya existe. Si la entidad es nula, lanza una
	 * excepción. Esta operación también es transaccional.
	 */
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

	/**
	 * Elimina una entidad usando su ID. Primero revisa si existe. Si no existe,
	 * lanza una excepción. También está marcada como transaccional.
	 */
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