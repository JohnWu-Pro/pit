package org.wjh.framework.dao.jpa;

import static org.wjh.lang.util.TypeUtils.cast;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;

/**
 * JPA utility class.
 *
 * @author John Wu
 */
class JpaUtils {

    public static <T> Class<T> findEntityClass(T entity) {
        Class<?> clazz = entity.getClass();
        while (true) {
            if (clazz.getAnnotation(Entity.class) != null) {
                return cast(clazz);
            }

            Class<?> superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw new IllegalArgumentException(entity.getClass() + " is not an Entity class.");
            } else {
                clazz = superClass;
            }
        }
    }

    public static <T, ID extends Serializable> ID getIdentifier(EntityManager entityManager, T entity) {
        return cast(entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity));
    }

// @formatter:off
//	public static <T> List<SingularAttribute<? super T, ?>> findIdAttributes(EntityManager entityManager, T entity) {
//		return findIdAttributes(entityManager, findEntityClass(entity));
//	}
// @formatter:on

    public static <T> List<SingularAttribute<? super T, ?>> findIdAttributes(EntityManager entityManager, Class<T> entityClass) {
        List<SingularAttribute<? super T, ?>> idAttributes = new ArrayList<SingularAttribute<? super T, ?>>();
        ManagedType<T> managedType = entityManager.getMetamodel().managedType(entityClass);
        for (SingularAttribute<? super T, ?> attribute : managedType.getSingularAttributes()) {
            if (attribute.isId()) {
                idAttributes.add(attribute);
            }
        }
        if (idAttributes.isEmpty()) {
            throw new IllegalStateException(entityClass + " has no Id attribute declared.");
        }
        return idAttributes;
    }

    public static <T> Object getAttribute(Object managedObject, SingularAttribute<? super T, ?> attribute) {
        Member member = attribute.getJavaMember();
        if (member instanceof Method) {
            Method getter = (Method) member;
            boolean getterIsAccessible = getter.isAccessible();
            try {
                if (!getterIsAccessible) {
                    getter.setAccessible(true);
                }
                return getter.invoke(managedObject);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } finally {
                if (!getterIsAccessible) {
                    getter.setAccessible(false);
                }
            }
        } else if (member instanceof Field) {
            Field field = (Field) member;
            boolean fieldIsAccessible = field.isAccessible();
            try {
                if (!fieldIsAccessible) {
                    field.setAccessible(true);
                }
                return field.get(managedObject);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                if (!fieldIsAccessible) {
                    field.setAccessible(false);
                }
            }
        } else {
            throw new IllegalStateException(managedObject + " has an attribute " + attribute + " of neither Method nor Field.");
        }
    }

    public static <T> void copyVersionIfApplicable(EntityManager entityManager, T from, T to) {
        Class<T> entityClass = findEntityClass(from);

        // TODO: Cache version getter/setter or field
        Member member = findVersionAttribute(entityManager, entityClass);
        if (member == null) {
            return;
        }

        if (member instanceof Method) {
            Method getter = (Method) member;
            boolean getterIsAccessible = getter.isAccessible();
            Method setter = findVersionSetter(entityClass, getter);
            boolean setterIsAccessible = setter.isAccessible();
            try {
                if (!getterIsAccessible) {
                    getter.setAccessible(true);
                }
                if (!setterIsAccessible) {
                    setter.setAccessible(true);
                }
                setter.invoke(to, getter.invoke(from));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } finally {
                if (!getterIsAccessible) {
                    getter.setAccessible(false);
                }
                if (!setterIsAccessible) {
                    setter.setAccessible(false);
                }
            }
        } else if (member instanceof Field) {
            Field field = (Field) member;
            boolean fieldIsAccessible = field.isAccessible();
            try {
                if (!fieldIsAccessible) {
                    field.setAccessible(true);
                }
                field.set(to, field.get(from));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                if (!fieldIsAccessible) {
                    field.setAccessible(false);
                }
            }
        } else {
            throw new IllegalStateException(from.getClass() + " has a Version attribute of neither Method nor Field.");
        }
    }

    private static <T> Member findVersionAttribute(EntityManager entityManager, Class<T> entityClass) {
        ManagedType<T> managedType = entityManager.getMetamodel().managedType(entityClass);
        for (SingularAttribute<? super T, ?> attribute : managedType.getSingularAttributes()) {
            if (attribute.isVersion()) {
                attribute.getJavaMember();
            }
        }
        return null;
    }

    private static Method findVersionSetter(Class<?> clazz, Method versionGetter) {
        Class<?> versionType = versionGetter.getReturnType();
        String setterName = "set" + versionGetter.getName().substring(3);
        Method method = findVersionSetter(clazz, setterName, versionType);
        if (method == null) {
            throw new RuntimeException("No method " + setterName + " found for " + clazz);
        }
        return method;
    }

    private static Method findVersionSetter(Class<?> clazz, String name, Class<?> type) {
        try {
            return clazz.getDeclaredMethod(name, type);
        } catch (NoSuchMethodException e) {
            // throw new RuntimeException(e);
        }

        if (clazz.getSuperclass() != null) {
            Method method = findVersionSetter(clazz.getSuperclass(), name, type);
            if (method != null) {
                return method;
            }
        }

        for (Class<?> _interface : clazz.getInterfaces()) {
            Method method = findVersionSetter(_interface, name, type);
            if (method != null) {
                return method;
            }
        }

        return null;
    }

}
