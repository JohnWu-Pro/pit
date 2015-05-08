package org.wjh.framework.param;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.splitPreserveAllTokens;
import static org.wjh.lang.util.TypeUtils.cast;
import static org.wjh.lang.util.ValueUtils.in;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ParamUtils {

    private static final Map<String, Class<?>> PARAM_TYPE_NAME_TO_CLASS_MAP;
    private static final Map<Class<?>, ParamConverter<?>> PARAM_TYPE_CLASS_TO_CONVERTER_MAP;

    private static final String[] BOOLEAN_TRUE_LITERAL = new String[]{"Y", "T", "1"};
    private static final String[] BOOLEAN_FALSE_LITERAL = new String[]{"N", "F", "0"};

    private static final ParamConverter<Boolean> BOOLEAN_CONVERTER = new ParamConverter<Boolean>() {
        @Override
        public Boolean convert(String value) {
            if (isBlankOrNull(value)) {
                return null;
            } else {
                value = value.trim().toUpperCase();
                if (in(value, BOOLEAN_TRUE_LITERAL)) {
                    return true;
                } else if (in(value, BOOLEAN_FALSE_LITERAL)) {
                    return false;
                } else {
                    throw new IllegalArgumentException("Can NOT convert '" + value + "' to Boolean.");
                }
            }
        }
    };

    private static final ParamConverter<Integer> INTEGER_CONVERTER = new ParamConverter<Integer>() {
        @Override
        public Integer convert(String value) {
            if (isBlankOrNull(value)) {
                return null;
            } else {
                return Integer.valueOf(value.trim());
            }
        }
    };

    private static final ParamConverter<Long> LONG_CONVERTER = new ParamConverter<Long>() {
        @Override
        public Long convert(String value) {
            if (isBlankOrNull(value)) {
                return null;
            } else {
                return Long.valueOf(value.trim());
            }
        }
    };

    private static final ParamConverter<BigDecimal> DECIMAL_CONVERTER = new ParamConverter<BigDecimal>() {
        @Override
        public BigDecimal convert(String value) {
            if (isBlankOrNull(value)) {
                return null;
            } else {
                return new BigDecimal(value.trim());
            }
        }
    };

    private static final ParamConverter<String> STRING_CONVERTER = new ParamConverter<String>() {
        @Override
        public String convert(String value) {
            if (isBlankOrNull(value)) {
                return null;
            } else {
                value = value.trim();
                if (isQuoted(value, "'") || isQuoted(value, "\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                return value;
            }
        }
    };

    private static final boolean isQuoted(String value, String delimiter) {
        return value.startsWith(delimiter) && value.endsWith(delimiter);
    }

    private static final String NULL_LITERAL = "null";

    private static final boolean isBlankOrNull(String value) {
        if (value == null) {
            return true;
        }

        value = value.trim();
        return value.isEmpty() ? true : value.toLowerCase().equals(NULL_LITERAL);
    }

    static {
        PARAM_TYPE_NAME_TO_CLASS_MAP = new LinkedHashMap<String, Class<?>>();
        PARAM_TYPE_NAME_TO_CLASS_MAP.put("Boolean", Boolean.class);
        PARAM_TYPE_NAME_TO_CLASS_MAP.put("Integer", Integer.class);
        PARAM_TYPE_NAME_TO_CLASS_MAP.put("Long", Long.class);
        PARAM_TYPE_NAME_TO_CLASS_MAP.put("Decimal", BigDecimal.class);
        PARAM_TYPE_NAME_TO_CLASS_MAP.put("String", String.class);

        PARAM_TYPE_CLASS_TO_CONVERTER_MAP = new LinkedHashMap<Class<?>, ParamConverter<?>>();
        PARAM_TYPE_CLASS_TO_CONVERTER_MAP.put(Boolean.class, BOOLEAN_CONVERTER);
        PARAM_TYPE_CLASS_TO_CONVERTER_MAP.put(Integer.class, INTEGER_CONVERTER);
        PARAM_TYPE_CLASS_TO_CONVERTER_MAP.put(Long.class, LONG_CONVERTER);
        PARAM_TYPE_CLASS_TO_CONVERTER_MAP.put(BigDecimal.class, DECIMAL_CONVERTER);
        PARAM_TYPE_CLASS_TO_CONVERTER_MAP.put(String.class, STRING_CONVERTER);
    }

    private static final String PARAM_SEPARATORS = ",|:";

// @formatter:off
//    public static Map<String, Class<?>> populateParamTypes(String paramNamesCsv, String paramTypesCsv) {
//        if (isBlank(paramNamesCsv)) {
//            return Collections.emptyMap();
//        } else if (isBlank(paramTypesCsv)) {
//            throwNamesNotMatchTypes(paramNamesCsv, paramTypesCsv);
//        }
//
//        String[] paramNames = splitPreserveAllTokens(paramNamesCsv, PARAM_SEPARATORS);
//        String[] paramTypes = splitPreserveAllTokens(paramTypesCsv, PARAM_SEPARATORS);
//        if (paramNames.length != paramTypes.length) {
//            throwNamesNotMatchTypes(paramNamesCsv, paramTypesCsv);
//        }
//
//        Map<String, Class<?>> map = new LinkedHashMap<String, Class<?>>();
//        for (int index = 0; index < paramNames.length; index++) {
//            String name = paramNames[index].trim();
//            String type = paramTypes[index].trim();
//            Class<?> clazz = PARAM_TYPE_NAME_TO_CLASS_MAP.get(type);
//            if (clazz == null) {
//                throw new IllegalArgumentException("Parameter type ('" + type + "') is not supported. ");
//            }
//            map.put(name, clazz);
//        }
//
//        return map;
//    }
//
//    private static void throwNamesNotMatchTypes(String paramNamesCsv, String paramTypesCsv) {
//        throw new IllegalArgumentException("Parameter names ('" + paramNamesCsv + "') and types ('" + paramTypesCsv
//                + "') does NOT match in number of elements.");
//    }
// @formatter:on

    /**
     * Gets the parameter type class based on the given type name.<br />
     * <br />
     * Supported parameter type names v.s. classes are:
     * <ul>
     * <li>Boolean -> {@link Boolean}</li>
     * <li>Integer -> {@link Integer}</li>
     * <li>Long -> {@link Long}</li>
     * <li>Decimal -> {@link BigDecimal}</li>
     * <li>String -> {@link String}</li>
     * </ul>
     *
     * @param paramType
     *            the parameter type name
     * @return the parameter type class
     * @throws IllegalArgumentException
     *             if the given parameter type name is blank, or if it's not in the supported names listed above
     */
    public static Class<?> getParamType(String paramType) {
        if (isBlank(paramType)) {
            throw new IllegalArgumentException("Parameter type name must NOT be blank. ");
        }

        paramType = paramType.trim();
        if (PARAM_TYPE_NAME_TO_CLASS_MAP.containsKey(paramType)) {
            return PARAM_TYPE_NAME_TO_CLASS_MAP.get(paramType);
        } else {
            throw new IllegalArgumentException("Parameter type ('" + paramType + "') is not supported. ");
        }
    }

    /**
     * Gets the parameter value based on the given parameter type class and parameter value in the raw string form. If not quoted by a pair of ' or ",
     * a blank string or {@code null} (case-insensitive) will be converted to {@code null}.<br />
     * <br />
     * Supported parameter type classes are:
     * <ul>
     * <li>{@link Boolean}</li>
     * <li>{@link Integer}</li>
     * <li>{@link Long}</li>
     * <li>{@link BigDecimal}</li>
     * <li>{@link String}</li>
     * </ul>
     *
     * @param paramType
     *            the parameter type class
     * @param paramValue
     *            the parameter value in the raw string form
     * @return the converted parameter value
     * @throws IllegalArgumentException
     *             if the given parameter type class is not in the supported types listed above
     */
    public static <T> T getParamValue(Class<T> paramType, String paramValue) {
        if (PARAM_TYPE_CLASS_TO_CONVERTER_MAP.containsKey(paramType)) {
            ParamConverter<T> converter = cast(PARAM_TYPE_CLASS_TO_CONVERTER_MAP.get(paramType));
            return converter.convert(paramValue);
        } else {
            throw new IllegalArgumentException("Parameter type ('" + paramType + "') is not supported. ");
        }
    }

    /**
     * Populates the parameters based on the given parameter CSV. The parameter CSV is expected in the form of
     * {@code ParamTypeName1 _ ParamTypeValue1 _ ParamTypeName2 _ ParamTypeValue2 ...}<br />
     * Where {@code _} represents a separator, which can be a Comma (,), a Colon (:), or a Vertical Bar (|). The leading and tailing whitespace(s) of
     * the parameter type name and the parameter value will be trimmed.<br />
     * <br />
     * Here are a few parameter CSV examples:
     * <ul>
     * <li>Boolean:Y, Decimal:1.13, Long:1002</li>
     * <li>Boolean,Y, Decimal,1.13, Long,1002</li>
     * <li>Boolean,Y,Decimal,1.13|Long,1002</li>
     * </ul>
     *
     * @param paramsCsv
     *            the parameter CSV
     * @return the constructed {@link Params} instance, or {@code null} if the parameter CSV is blank
     */
    public static Params populateParams(String paramsCsv) {
        if (isBlank(paramsCsv)) {
            return null;
        }

        String[] tokens = splitPreserveAllTokens(paramsCsv, PARAM_SEPARATORS);
        if (tokens.length % 2 != 0) {
            throw new IllegalArgumentException("Parameter types and values in ('" + paramsCsv + "') does NOT match.");
        }

        Params params = new Params();
        for (int index = 0; index < tokens.length;) {
            Class<?> type = getParamType(tokens[index++]);
            Object value = getParamValue(type, tokens[index++]);
            params.add(type, value);
        }

        return params;
    }

    /**
     * Private constructor to prevent from constructing an instance of {@code ParamUtils}.
     */
    private ParamUtils() {
    }
}
