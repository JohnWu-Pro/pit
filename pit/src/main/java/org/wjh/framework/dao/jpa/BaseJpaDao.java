package org.wjh.framework.dao.jpa;

import static org.wjh.lang.util.TypeUtils.cast;
import static org.wjh.lang.util.TypeUtils.getSuperclassActualTypeArgument;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.wjh.framework.dao.BaseDao;

public abstract class BaseJpaDao<T, ID extends Serializable> implements BaseDao<T, ID> {

    private final Class<T> entityClass;
    private final GenericJpaDao genericDao;

    protected BaseJpaDao() {
        this.entityClass = getEntityClass(getClass());
        this.genericDao = new GenericJpaDao();
    }

    protected BaseJpaDao(Class<T> type) {
        this.entityClass = type;
        this.genericDao = new GenericJpaDao();
    }

    private Class<T> getEntityClass(Class<?> daoClass) {
        Class<?> clazz = getSuperclassActualTypeArgument(daoClass);
        if (clazz == null) {
            throw new RuntimeException("Can NOT figure out entity class from class " + daoClass);
        }
        return cast(clazz);
    }

    @Override
    public void save(T entity) {
        genericDao.save(entity);
    }

    @Override
    public void save(Iterable<? extends T> entities) {
        genericDao.save(entities);
    }

    @Override
    public boolean exists(ID id) {
        return genericDao.exists(entityClass, id);
    }

    @Override
    public T findOne(ID id) {
        return genericDao.findOne(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return genericDao.findAll(entityClass);
    }

    @Override
    public long count() {
        return genericDao.count(entityClass);
    }

    @Override
    public void delete(ID id) {
        genericDao.delete(entityClass, id);
    }

    @Override
    public void delete(T entity) {
        genericDao.delete(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        genericDao.delete(entities);
    }

    @Override
    public void deleteAll() {
        genericDao.deleteAll(entityClass);
    }

    @PersistenceContext
    public final void setEntityManager(EntityManager entityManager) {
        genericDao.setEntityManager(entityManager);
    }

    protected final EntityManager getEntityManager() {
        return genericDao.getEntityManager();
    }
}
