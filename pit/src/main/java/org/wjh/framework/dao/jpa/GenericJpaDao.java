package org.wjh.framework.dao.jpa;

import static org.wjh.framework.dao.jpa.JpaUtils.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.wjh.framework.dao.GenericDao;

@Named("genericDao")
public final class GenericJpaDao implements GenericDao {

    private EntityManager entityManager;
    private volatile Validator validator = null;

    @Override
    public <T> void save(T entity) {
        if (entity == null) {
            return;
        }

        validate(entity);

        Object id = getIdentifier(entityManager, entity);
        // @formatter:off
        if (id == null) {
            if (entityManager.contains(entity)) { 	// is a newly created and managed entity instance
                // to cascade merge operation to relationships
                entityManager.merge(entity);
            } else { 								// is a brand new entity instance
                // to make it managed and persistent
                entityManager.persist(entity);
            }
        } else {									// is a detached entity instance
            T merged = entityManager.merge(entity);
            copyVersionIfApplicable(entityManager, merged, entity);
        }
        // @formatter:on
    }

    @Override
    public <T> void save(Iterable<? extends T> entities) {
        if (entities == null) {
            return;
        }

        for (T entity : entities) {
            save(entity);
        }
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public <T, ID extends Serializable> boolean exists(Class<T> clazz, ID id) {
        return findOne(clazz, id) != null;
    }

    @Override
    public <T, ID extends Serializable> T findOne(Class<T> clazz, ID id) {
        // For verifying PersistenceExceptionTranslationPostProcessor only
        // @SuppressWarnings("unchecked")
        // Persistable<ID> entity = (Persistable<ID>)
        // entityManager.getReference(clazz, id);
        // System.out.println("ID: " + entity.getId());
        return id == null ? null : entityManager.find(clazz, id);
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> root = query.from(clazz);

        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public <T> long count(Class<T> clazz) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(clazz);

        query.select(builder.count(root));

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public <T, ID extends Serializable> void delete(Class<T> clazz, ID id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> delete = builder.createCriteriaDelete(clazz);
        Root<T> root = delete.from(clazz);

        List<Predicate> predicates = new ArrayList<Predicate>();

        List<SingularAttribute<? super T, ?>> idAttributes = findIdAttributes(entityManager, clazz);
        if (idAttributes.size() == 1) {
            predicates.add(builder.equal(root.get(idAttributes.get(0).getName()), id));
        } else {
            for (SingularAttribute<? super T, ?> attribute : idAttributes) {
                predicates.add(builder.equal(root.get(attribute.getName()), getAttribute(id, attribute)));
            }
        }

        delete.where(predicates.toArray(new Predicate[predicates.size()]));
        entityManager.createQuery(delete).executeUpdate();
    }

    @Override
    public <T> void delete(T entity) {
        if (entity == null) {
            return;
        }

        Class<T> clazz = findEntityClass(entity);
        Serializable id = getIdentifier(entityManager, entity);

        delete(clazz, id);
    }

    @Override
    public <T> void delete(Iterable<? extends T> entities) {
        if (entities == null) {
            return;
        }

        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public <T> void deleteAll(Class<T> clazz) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> delete = builder.createCriteriaDelete(clazz);
        delete.from(clazz);

        entityManager.createQuery(delete).executeUpdate();
    }

    private <T> void validate(final T object) {
        Set<ConstraintViolation<T>> violations = getValidator().validate(object);
        if (!violations.isEmpty()) {
            throw new RuntimeException("Constraint Violations: " + violationsToMessages(violations).toString());
        }
    }

    private Validator getValidator() {
        if (validator == null) {
            synchronized (LazyValidatorFactoryHolder.VALIDATOR_FACTORY) {
                if (validator == null) {
                    validator = LazyValidatorFactoryHolder.VALIDATOR_FACTORY.getValidator();
                }
            }
        }
        return validator;
    }

    private <T> List<String> violationsToMessages(final Set<ConstraintViolation<T>> violations) {
        List<String> messages = new ArrayList<String>();
        for (ConstraintViolation<T> violation : violations) {
            messages.add(violation.getMessage());
        }

        return messages;
    }

    private static class LazyValidatorFactoryHolder {
        private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    }

    @PersistenceContext
    public final void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected final EntityManager getEntityManager() {
        return entityManager;
    }

}
