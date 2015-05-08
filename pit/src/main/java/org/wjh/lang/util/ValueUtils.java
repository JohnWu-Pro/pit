package org.wjh.lang.util;

import static org.wjh.lang.util.NumberUtils.isZero;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Utility class to check values. In particular, it checks
 * <UL>
 * <LI>if the value is trivial (<code>null</code>, 0, or empty)</LI>
 * <LI>if the value is in the collection</LI>
 * <LI>if values are equal</LI>
 * <LI>if the flag is enabled</LI>
 * </UL>
 *
 * @author John Wu
 */
public abstract class ValueUtils {

    /**
     * Checks if the value is <code>null</code>, empty or zero. Examples:
     *
     * <pre>
     * isTrivial(null)                      = true
     * isTrivial("")                        = true
     * isTrivial(" ")                       = false
     * isTrivial(0)                         = true
     * isTrivial(1)                         = false
     * isTrivial(0L)                        = true
     * isTrivial(0.0D)                      = true
     * isTrivial(1.0E308D)                  = false
     * isTrivial(0.0F)                      = true
     * isTrivial(1.0E-45F)                  = false
     * isTrivial(new Integer(0))            = true
     * isTrivial(new BigDecimal(0))         = true
     * isTrivial(Collections.emptyList())   = true
     * isTrivial(Collections.emptyMap())    = true
     * isTrivial(Collections.emptySet())    = true
     * isTrivial(new int[0])                = true
     * isTrivial(new int[]{2})              = false
     * isTrivial(new Object[0])             = true
     * isTrivial(new Object[]{})            = true
     * </pre>
     *
     * @param <T>
     *            the value type parameter
     * @param value
     *            the value object
     * @return true if and only if the value is <code>null</code>, empty or zero
     */
    public static <T> boolean isTrivial(T value) {
        if (value == null) {
            return true;
        } else if (value instanceof CharSequence) {
            return ((CharSequence) value).length() == 0;
        } else if (value instanceof Number) {
            return isZero((Number) value);
        } else if (value instanceof Collection<?>) {
            return ((Collection<?>) value).isEmpty();
        } else if (value instanceof Map<?, ?>) {
            return ((Map<?, ?>) value).isEmpty();
        } else if (value instanceof Object[]) {
            return ((Object[]) value).length == 0;
        } else if (value.getClass().isArray()) { // Primitive Array
            return Array.getLength(value) == 0;
        } else {
            return false;
        }
    }

    /**
     * Checks if the value is <code>null</code>, empty or zero. Examples:
     *
     * <pre>
     * isNonTrivial(null)                      = false
     * isNonTrivial("")                        = false
     * isNonTrivial(" ")                       = true
     * isNonTrivial(0)                         = false
     * isNonTrivial(1)                         = true
     * isNonTrivial(0L)                        = false
     * isNonTrivial(0.0D)                      = false
     * isNonTrivial(1.0E308D)                  = true
     * isNonTrivial(0.0F)                      = false
     * isNonTrivial(1.0E-45F)                  = true
     * isNonTrivial(new Integer(0))            = false
     * isNonTrivial(new BigDecimal(0))         = false
     * isNonTrivial(Collections.emptyList())   = false
     * isNonTrivial(Collections.emptyMap())    = false
     * isNonTrivial(Collections.emptySet())    = false
     * isNonTrivial(new int[0])                = false
     * isNonTrivial(new int[]{2})              = true
     * isNonTrivial(new Object[0])             = false
     * isNonTrivial(new Object[]{})            = false
     * </pre>
     *
     * @param <T>
     *            the value type parameter
     * @param value
     *            the value object
     * @return true if and only if the value is NOT <code>null</code>, empty or zero
     */
    public static <T> boolean isNonTrivial(T value) {
        return !isTrivial(value);
    }

    /**
     * Checks if the actual value equals to one of the expected values.
     *
     * @param actualValue
     *            the actual values to check
     * @param expectedValues
     *            the expected values
     * @return true if the actual value equals to one of the expected values, otherwise, false
     */
    public static <T> boolean in(T actualValue, T... expectedValues) {
        if (expectedValues == null || expectedValues.length == 0) {
            return false;
        }
        for (Object expectedValue : expectedValues) {
            if (isEqual(actualValue, expectedValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if two objects are equal. This method calls the {@link Object#equals(Object)} and is <code>null</code> safe.
     *
     * @param obj1
     *            the object 1
     * @param obj2
     *            the object 2
     * @return true if object 1 equals to object 2, otherwise, false
     */
    public static boolean isEqual(Object obj1, Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

    /**
     * Checks if the flag is 0 (disabled). This method is <code>null</code> safe.
     *
     * @param flag
     *            the flag Integer
     * @return true if flag is 0 (disabled), otherwise, false
     */
    public static boolean isDisabled(Integer flag) {
        return Integer.valueOf(0).equals(flag);
    }

    /**
     * Checks if the flag is 1 (enabled). This method is <code>null</code> safe.
     *
     * @param flag
     *            the flag Integer
     * @return true if flag is 1 (enabled), otherwise, false
     */
    public static boolean isEnabled(Integer flag) {
        return Integer.valueOf(1).equals(flag);
    }

    /**
     * Private constructor to prevent from constructing an instance of {@code ValueUtils}.
     */
    private ValueUtils() {
    }
}
