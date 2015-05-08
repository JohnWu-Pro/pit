package org.wjh.framework.dao.util;

import java.io.Serializable;

import org.wjh.framework.dao.DerefDao;

public class DerefUtils {

    private static DerefDao dao;

    public static <T, ID extends Serializable> T findById(Class<T> clazz, ID id) {
        validateDerefDao();

        return dao.findOne(clazz, id);
    }

    private static void validateDerefDao() {
        if (dao == null) {
            throw new RuntimeException("DEREF_DAO_NOT_SET");
        }
    }

    /**
     * Injects the DerefDao instance.
     *
     * @param derefDao
     *            the derefDao
     */
    public static void setDerefDao(DerefDao derefDao) {
        dao = derefDao;
    }

    /**
     * Private constructor to prevent from constructing an instance of {@code DerefUtils}.
     */
    private DerefUtils() {
    }

}
