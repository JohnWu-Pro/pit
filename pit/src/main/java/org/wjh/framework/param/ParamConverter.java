package org.wjh.framework.param;

public interface ParamConverter<T> {

    T convert(String value);
}
