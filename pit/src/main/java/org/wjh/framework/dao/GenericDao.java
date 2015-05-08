package org.wjh.framework.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for generic CRUD operations.<br />
 * <br />
 * Source: Copied from {@link org.springframework.data.repository.CrudRepository CrudRepository} of Spring Data, and changed Iterable&lt;E&gt; to
 * List&lt;E&gt; for the method returns.
 */
public interface GenericDao extends DerefDao {

    /**
     * Saves the given entity. If the entity had been saved before, a merge operation will be performed (with the latest state after merging copied
     * back to the given entity), otherwise, a new persist operation will be performed (and ID will be set after flushing).
     *
     * @param entity
     *            the entity to be saved
     */
    <T> void save(T entity);

    /**
     * Saves all given entities. For each entity, it had been saved before, a merge operation will be performed (with the latest state after merging
     * copied back to the given entity), otherwise, a new persist operation will be performed (and ID will be set after flushing).
     *
     * @param entities
     *            the entities to be saved
     */
    <T> void save(Iterable<? extends T> entities);

    /**
     * Synchronizes the persistence context to the underlying database.
     */
    void flush();

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id
     * @return true if an entity with the given id exists, alse otherwise
     * @throws IllegalArgumentException
     *             if primaryKey is {@code null}
     */
    <T, ID extends Serializable> boolean exists(Class<T> clazz, ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    <T> List<T> findAll(Class<T> clazz);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    <T> long count(Class<T> clazz);

    /**
     * Deletes the entity with the given id.
     *
     * @param id
     */
    <T, ID extends Serializable> void delete(Class<T> clazz, ID id);

    /**
     * Deletes a given entity.
     *
     * @param entity
     */
    <T> void delete(T entity);

    /**
     * Deletes the given entities.
     *
     * @param entities
     */
    <T> void delete(Iterable<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     */
    <T> void deleteAll(Class<T> clazz);
}
