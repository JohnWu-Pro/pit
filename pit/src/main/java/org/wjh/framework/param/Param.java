package org.wjh.framework.param;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Param<T> implements Serializable {
    private static final long serialVersionUID = 5988467477971673346L;

    private String name;
    private Class<T> type;
    private T value;

    public Param() {
    }

    public Param(String name) {
        this.name = name;
    }

    public Param(Class<T> type) {
        this.type = type;
    }

    public Param(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    public Param(Class<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    public Param(String name, Class<T> type, T value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (!getClass().isInstance(obj)) {
            return false;
        }

        Param<?> rhs = (Param<?>) obj;
        return new EqualsBuilder()// @formatter:off
                .append(name, rhs.name)
                .append(type, rhs.type)
                .append(value, rhs.value)
                .isEquals();// @formatter:on
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()// @formatter:off
                .append(name)
                .append(type)
                .append(value)
                .toHashCode();// @formatter:on
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)// @formatter:off
                .append("name", name)
                .append("type", type)
                .append("value", value)
                .toString();// @formatter:on
    }
}
