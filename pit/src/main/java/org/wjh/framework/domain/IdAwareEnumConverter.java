package org.wjh.framework.domain;

import static org.wjh.lang.util.TypeUtils.getSuperclassActualTypeArgument;
import static org.wjh.lang.util.ValueUtils.isEqual;

import java.io.Serializable;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdAwareEnumConverter<T extends Enum<T> & IdAwareEnum<ID>, ID extends Serializable> implements AttributeConverter<T, ID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdAwareEnumConverter.class);

    private Class<T> enumClass;

    @Override
    public ID convertToDatabaseColumn(T _enum) {
        return _enum == null ? null : _enum.getId();
    }

    @Override
    public T convertToEntityAttribute(ID id) {
        if (id == null) {
            return null;
        }

        return fromId(getEnumClass(), id);
    }

    private Class<T> getEnumClass() {
        if (enumClass == null) {
            enumClass = getSuperclassActualTypeArgument(getClass());
        }
        return enumClass;
    }

    public static <ID extends Serializable, T extends Enum<T> & IdAwareEnum<ID>> T fromId(Class<T> enumClass, ID id) {
        if (id == null) {
            return null;
        }

        for (T value : enumClass.getEnumConstants()) {
            if (isEqual(value.getId(), id)) {
                return value;
            }
        }

        LOGGER.warn("No {} instance found by id ({}).", enumClass.getSimpleName(), id);
        return null;
    }
}
