package org.wjh.framework.param;

import java.util.ArrayList;
import java.util.List;

public class Params {

    private final List<Class<?>> types;
    private final List<Object> values;

    public Params() {
        types = new ArrayList<Class<?>>();
        values = new ArrayList<Object>();
    }

    public Params add(Class<?> type, Object value) {
        types.add(type);
        values.add(value);
        return this;
    }

    public Class<?>[] getTypes() {
        return types.toArray(new Class<?>[types.size()]);
    }

    public Object[] getValues() {
        return values.toArray(new Object[values.size()]);
    }
}
