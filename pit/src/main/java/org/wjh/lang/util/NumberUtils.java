package org.wjh.lang.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Utility class to operate numbers. In particular, it
 * <UL>
 * <LI>Checks if the number is zero</LI>
 * <LI>Adds two numbers</LI>
 * <LI>Unboxes number from wrapper object, <code>null</code>-safely</LI>
 * <LI>Rounding up/down numbers by unit size</LI>
 * <LI>Truncates numbers</LI>
 * </UL>
 *
 * @author John Wu
 */
public abstract class NumberUtils {

    private static final int DEFAULT_DECIMAL_SCALE = 4;

    private static final RoundingMode DEFAULT_DECIMAL_ROUNDING_MODE = RoundingMode.DOWN;

    /**
     * Checks if the value equals to ZERO. Examples:
     *
     * <pre>
     * isZero(null)                      = false
     * isZero(0)                         = true
     * isZero(1)                         = false
     * isZero(0L)                        = true
     * isZero(0.0D)                      = true
     * isZero(1.0E308D)                  = false
     * isZero(0.0F)                      = true
     * isZero(1.0E-45F)                  = false
     * isZero(new Integer(0))            = true
     * isZero(new BigDecimal(0))         = true
     * </pre>
     *
     * @param value
     *            the value to check
     * @return true if and only if the value equals to ZERO
     */
    public static boolean isZero(Number value) {
        if (value == null) {
            return false;
        } else if (value instanceof Integer || value instanceof Short || value instanceof Byte) {
            return ((Number) value).intValue() == 0;
        } else if (value instanceof Long) {
            return value.longValue() == 0L;
        } else if (value instanceof BigInteger) {
            return BigInteger.ZERO.equals(value);
        } else if (value instanceof BigDecimal) {
            return BigInteger.ZERO.equals(((BigDecimal) value).unscaledValue());
        } else if (value instanceof Float) {
            return value.floatValue() == 0.0F;
        } else if (value instanceof Double) {
            return value.doubleValue() == 0.0D;
        } else {
            throw new IllegalArgumentException("Unsupported value " + value + "of type " + value.getClass().getName());
        }
    }

    /**
     * Checks if the value equals to ZERO. Examples:
     *
     * <pre>
     * isNonZero(null)                      = false
     * isNonZero(0)                         = false
     * isNonZero(1)                         = true
     * isNonZero(0L)                        = false
     * isNonZero(0.0D)                      = false
     * isNonZero(1.0E308D)                  = true
     * isNonZero(0.0F)                      = false
     * isNonZero(1.0E-45F)                  = true
     * isNonZero(new Integer(0))            = false
     * isNonZero(new BigDecimal(0))         = false
     * </pre>
     *
     * @param value
     *            the value to check
     * @return true if and only if the value does NOT equal to ZERO
     */
    public static boolean isNonZero(Number value) {
        return value == null ? false : !isZero(value);
    }

    /**
     * Calculates the sum of two {@link Integer} objects. <code>null</code> considered to be zero.
     *
     * @param augend
     *            the first {@link Integer} object
     * @param addend
     *            the second {@link Integer} object
     * @return the sum of the two {@link Integer} objects
     */
    public static int sum(Integer augend, Integer addend) {
        return unbox(augend) + unbox(addend);
    }

    /**
     * Calculates the sum of two {@link Long} objects. <code>null</code> considered to be zero.
     *
     * @param augend
     *            the first {@link Long} object
     * @param addend
     *            the second {@link Long} object
     * @return the sum of the two {@link Long} objects
     */
    public static long sum(Long augend, Long addend) {
        return unbox(augend) + unbox(addend);
    }

    /**
     * Calculates the sum of two {@link BigDecimal} objects. <code>null</code> considered to be zero.
     *
     * @param augend
     *            the first {@link BigDecimal} object
     * @param addend
     *            the second {@link BigDecimal} object
     * @return the sum of the two {@link BigDecimal} objects
     */
    public static BigDecimal sum(BigDecimal augend, BigDecimal addend) {
        return augend == null ? addend : (addend == null ? augend : augend.add(addend));
    }

    /**
     * Calculates the subtraction of two {@link Integer} objects. <code>null</code> considered to be zero.
     *
     * @param minuend
     *            the minuend {@link Integer} object
     * @param subtrahend
     *            the subtrahend {@link Integer} object
     * @return the subtraction result of {@code minuend - subtrahend}
     */
    public static int subtract(Integer minuend, Integer subtrahend) {
        return unbox(minuend) - unbox(subtrahend);
    }

    /**
     * Calculates the subtraction of two {@link Long} objects. <code>null</code> considered to be zero.
     *
     * @param minuend
     *            the minuend {@link Long} object
     * @param subtrahend
     *            the subtrahend {@link Long} object
     * @return the subtraction result of {@code minuend - subtrahend}
     */
    public static long subtract(Long minuend, Long subtrahend) {
        return unbox(minuend) - unbox(subtrahend);
    }

    /**
     * Calculates the subtraction of two {@link BigDecimal} objects. <code>null</code> considered to be zero.
     *
     * @param minuend
     *            the minuend {@link BigDecimal} object
     * @param subtrahend
     *            the subtrahend {@link BigDecimal} object
     * @return the subtraction result of {@code minuend - subtrahend}
     */
    public static BigDecimal subtract(BigDecimal minuend, BigDecimal subtrahend) {
        if (minuend == null) {
            minuend = BigDecimal.ZERO;
        }

        if (subtrahend == null) {
            return minuend;
        }

        return minuend.subtract(subtrahend);
    }

    /**
     * Unbox the {@link Long} object to a primitive long value. <code>null</code> considered to be zero.
     *
     * @param number
     *            the {@link Long} object
     * @return the wrapped primitive long value, or zero if the <code>number</code> is <code>null</code>
     */
    public static long unbox(Long number) {
        return number == null ? 0L : number.longValue();
    }

    /**
     * Unbox the {@link Integer} object to a primitive int value. <code>null</code> considered to be zero.
     *
     * @param number
     *            the {@link Integer} object
     * @return the wrapped primitive int value, or zero if the <code>number</code> is <code>null</code>
     */
    public static int unbox(Integer number) {
        return number == null ? 0 : number.intValue();
    }

    /**
     * Rounds the value down based on the incremental size.
     *
     * @param value
     *            the input value
     * @param unit
     *            the incremental size
     * @return the rounded down value
     */
    public static long roundDown(long value, long unit) {
        return round(value, unit, RoundingMode.DOWN);
    }

    /**
     * Rounds the value up based on the incremental size.
     *
     * @param value
     *            the input value
     * @param unit
     *            the incremental size
     * @return the rounded up value
     */
    public static long roundUp(long value, long unit) {
        return round(value, unit, RoundingMode.UP);
    }

    private static long round(long value, long unit, RoundingMode mode) {
        long div = value / unit;
        long rem = value - div * unit;
        if (rem == 0) {
            return value;
        }

        switch (mode) {
            case DOWN:
                return value - rem;
            case UP:
                return (div + 1) * unit;
            default:
                throw new IllegalArgumentException("Unsupported rounding mode: " + mode);
        }
    }

    /**
     * Truncates {@code DOWN} the input decimal value to scale of 4, if input decimal value scale is greater than 4.
     *
     * @param value
     *            the input decimal value
     * @return the truncated value, or the input value if it is {@code null} or of scale 4 or less
     */
    public static BigDecimal truncate(BigDecimal value) {
        return truncate(value, DEFAULT_DECIMAL_SCALE);
    }

    /**
     * Truncates {@code DOWN} the input decimal value to the specified new scale, if input decimal value scale is greater than the new scale.
     *
     * @param value
     *            the input decimal value
     * @param newScale
     *            the new scale
     * @return the truncated value, or the input value if it is {@code null} or of scale {@code newScale} or less
     */
    public static BigDecimal truncate(BigDecimal value, int newScale) {
        return truncate(value, newScale, DEFAULT_DECIMAL_ROUNDING_MODE);
    }

    /**
     * Truncates the input decimal value by the specified rounding mode to the specified new scale, if input decimal value scale is greater than the
     * new scale.
     *
     * @param value
     *            the input decimal value
     * @param newScale
     *            the new scale
     * @param roundingMode
     *            the rounding mode
     * @return the truncated value, or the input value if it is {@code null} or of scale {@code newScale} or less
     */
    public static BigDecimal truncate(BigDecimal value, int newScale, RoundingMode roundingMode) {
        return value == null ? null : (value.scale() <= newScale ? value : value.setScale(newScale, roundingMode));
    }

    /**
     * Private constructor to prevent from constructing an instance of {@code NumberUtils}.
     */
    private NumberUtils() {
    }
}
