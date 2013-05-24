package com.pfe.server.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * Common interface operations for all the DAOs
 * 
 */
public interface IBaseDao<I extends Serializable, E> {
	
	/**
	 * @param entity
	 * @return saved entity
	 */
	E persist(E entity);

	/**
	 * @param entity
	 */
	void delete(E entity);

	/**
	 * @param id
	 * @return entity with given key
	 */
	E get(I id);
	
	/**
	 * Return a list with all the items
	 * @return a list with all the entities
	 */
	List<E> findAll();
	
	/**
	 * Count the number of instances of an entity in the database.
	 * @param entityClass
	 * @return the number of instances
	 * @throws DataAccessException
	 */
	long count();

	void deleteAll();

	E merge(E entity);

	void evict(E o);

}
