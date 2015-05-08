package org.wjh.lang.util;

/**
 * Exception checking utility class.
 *
 * @author John Wu
 */
public abstract class ExceptionUtils {

    /**
     * Recursively unwraps the cause of the specified {@code throwable}, till one of the {@code causeClasses} instance is encountered, or {@code null}
     * otherwise.
     *
     * @param throwable
     *            the {@link Throwable} instance to be checked
     * @param causeClasses
     *            the {@link Throwable} classes to be checked against
     * @return one of the {@code causeClasses} instance if encountered, or {@code null} otherwise
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T unwrapCause(Throwable throwable, Class<T>... causeClasses) {
        if (throwable == null) {
            return null;
        }

        if (causeClasses == null || causeClasses.length == 0) {
            return null;
        }

        Throwable cause = throwable;
        while (cause != null) {
            if (instanceOf(cause, causeClasses)) {
                return (T) cause;
            }
            cause = cause.getCause();
        }
        return null;
    }

    /**
     * Checks if the specified {@code throwable} is caused by one of the {@code causeClasses}.
     *
     * @param throwable
     *            the {@link Throwable} instance to be checked
     * @param causeClasses
     *            the {@link Throwable} classes to be checked against
     * @return {@code true} if the specified {@code throwable} is caused by one of the {@code causeClasses}, {@code false} otherwise
     */
    @SuppressWarnings("unchecked")
    public static boolean isCausedBy(Throwable throwable, Class<? extends Throwable>... causeClasses) {
        return unwrapCause(throwable, (Class<Throwable>[]) causeClasses) != null;
    }

    /**
     * Checks if the specified {@code throwable} is instance of one of the {@code throwableClasses}.
     *
     * @param throwable
     *            the {@link Throwable} instance to be checked
     * @param throwableClasses
     *            the {@link Throwable} classes to be checked against
     * @return {@code true} if the specified {@code throwable} is instance of one of the {@code throwableClasses}, {@code false} otherwise
     */
    public static boolean isInstanceOf(Throwable throwable, Class<? extends Throwable>... throwableClasses) {
        if (throwable == null) {
            return false;
        }

        if (throwableClasses == null || throwableClasses.length == 0) {
            return false;
        }

        return instanceOf(throwable, throwableClasses);
    }

    private static boolean instanceOf(Throwable throwable, Class<? extends Throwable>... throwableClasses) {
        for (Class<? extends Throwable> throwableClass : throwableClasses) {
            if (throwableClass.isAssignableFrom(throwable.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Private constructor to prevent from constructing an instance of {@code ExceptionUtils}.
     */
    private ExceptionUtils() {
    }
}
