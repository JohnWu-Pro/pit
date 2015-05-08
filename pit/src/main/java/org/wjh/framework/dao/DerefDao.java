package org.wjh.framework.dao;

import java.io.Serializable;

/**
 * Interface for generic object graph navigation based on object id, that is, dereferencing.<br />
 */
public interface DerefDao {
    /**
     * Retrieves an entity by its primary key.
     *
     * @param id
     * @return the entity with the given primary key or {@code null} if none found
     * @throws IllegalArgumentException
     *             if primaryKey is {@code null}
     */
    <T, ID extends Serializable> T findOne(Class<T> clazz, ID id);
}
