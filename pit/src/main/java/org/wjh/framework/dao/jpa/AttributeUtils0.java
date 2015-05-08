package org.wjh.framework.dao.jpa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Version;

public class AttributeUtils0 {

    public static <T> void copyVersionAttributeIfExists(T from, T to) {
        // TODO: Cache version getter and setter
        Class<?> clazz = from.getClass();
        Method getter = findVersionGetter(clazz);
        if (getter == null) {
            return;
        }

        Method setter = findVersionSetter(clazz, getter);
        try {
            if (!setter.isAccessible()) {
                setter.setAccessible(true);
            }
            setter.invoke(to, getter.invoke(from));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method findVersionGetter(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(Version.class) != null) {
                return method;
            }
        }

        if (clazz.getSuperclass() != null) {
            Method method = findVersionGetter(clazz.getSuperclass());
            if (method != null) {
                return method;
            }
        }

        for (Class<?> _interface : clazz.getInterfaces()) {
            Method method = findVersionGetter(_interface);
            if (method != null) {
                return method;
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
        } catch (SecurityException e) {
            throw new RuntimeException(e);
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
