package org.wjh.framework.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Base interface for CRUD operations for a specific type.<br />
 * <br />
 * Source: Copied from {@link org.springframework.data.repository.CrudRepository CrudRepository} of Spring Data, and changed Iterable&lt;E&gt; to
 * List&lt;E&gt; for the method returns.
 */
public interface BaseDao<T, ID extends Serializable> {

    /**
     * Saves the given entity. If the entity had been saved before, a merge operation will be performed (with the latest state after merging copied
     * back to the given entity), otherwise, a new persist operation will be performed (and ID will be set after flushing).
     *
     * @param entity
     *            the entity to be saved
     */
    void save(T entity);

    /**
     * Saves all given entities. For each entity, it had been saved before, a merge operation will be performed (with the latest state after merging
     * copied back to the given entity), otherwise, a new persist operation will be performed (and ID will be set after flushing).
     *
     * @param entities
     *            the entities to be saved
     */
    void save(Iterable<? extends T> entities);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id
     * @return true if an entity with the given id exists, alse otherwise
     * @throws IllegalArgumentException
     *             if primaryKey is {@code null}
     */
    boolean exists(ID id);

    /**
     * Retrieves an entity by its primary key.
     *
     * @param id
     * @return the entity with the given primary key or {@code null} if none found
     * @throws IllegalArgumentException
     *             if primaryKey is {@code null}
     */
    T findOne(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<T> findAll();

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();

    /**
     * Deletes the entity with the given id.
     *
     * @param id
     */
    void delete(ID id);

    /**
     * Deletes a given entity.
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * Deletes the given entities.
     *
     * @param entities
     */
    void delete(Iterable<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();
}
