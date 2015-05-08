package org.wjh.lang.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Generic type utility class.
 *
 * @author John Wu
 */
public abstract class TypeUtils {

    /**
     * Casts the object to the desired type.
     *
     * @param object
     *            the object to be type casted
     * @return the object of type {@code T}
     */
    public static <T> T cast(Object object) {
        @SuppressWarnings("unchecked")
        T concreteType = (T) object;
        return concreteType;
    }

    /**
     * Gets the actual type argument for the generic super class of a non-generic concrete class.
     *
     * @param clazz
     *            the non-generic concrete class
     * @return the actual type argument for the generic super class
     */
    public static <T> Class<T> getSuperclassActualTypeArgument(Class<?> clazz) {
        return getSuperclassActualTypeArgument(clazz, 0);
    }

    /**
     * Gets the actual type argument for the generic super class of a non-generic concrete class.
     *
     * @param clazz
     *            the non-generic concrete class
     * @param typeArgumentIndex
     *            the type argument index ({@code 0} based)
     * @return the actual type argument for the generic super class
     */
    public static <T> Class<T> getSuperclassActualTypeArgument(Class<?> clazz, int typeArgumentIndex) {
        while (clazz != null && clazz != Class.class) {
            Type superType = clazz.getGenericSuperclass();
            if (superType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) superType;
                @SuppressWarnings("unchecked")
                Class<T> actualType = (Class<T>) parameterizedType.getActualTypeArguments()[typeArgumentIndex];
                return actualType;
            } else {
                clazz = superType.getClass();
            }
        }
        return null;
    }

    /**
     * Private constructor to prevent from constructing an instance of {@code TypeUtils}.
     */
    private TypeUtils() {
    }
}
