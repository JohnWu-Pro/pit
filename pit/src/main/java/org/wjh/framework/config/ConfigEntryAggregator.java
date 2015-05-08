package org.wjh.framework.config;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.wjh.lang.util.TypeUtils.cast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigEntryAggregator<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigEntryAggregator.class);

    //
    // Injected properties
    //
    private Class<E> type;
    private boolean allowDuplication;
    private String name;

    //
    // Internal properties
    //
    private final List<E> entries;

    public ConfigEntryAggregator() {
        this(null);
    }

    public ConfigEntryAggregator(Class<E> type) {
        this.type = type;
        this.allowDuplication = false;
        this.entries = new ArrayList<E>();
    }

    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Parameter element must NOT be null.");
        }

        if (allowDuplication || !entries.contains(element)) {
            entries.add(element);
            LOGGER.debug("Added element {}", element);
        }
    }

    public List<E> list() {
        return entries;
    }

    public E[] array() {
        E[] array = cast(Array.newInstance(getType(), entries.size()));
        return entries.toArray(array);
    }

    private Class<E> getType() {
        if (type != null) {
            return type;
        } else if (!entries.isEmpty()) {
            return cast(entries.get(0).getClass());
        } else {
            throw new IllegalStateException("Can NOT figure out the type of the entries.");
        }
    }

    public String csv() {
        if (entries.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (E entry : entries) {
            builder.append(entry.toString()).append(',');
        }
        builder.setLength(builder.length() - 1);

        return builder.toString();
    }

    @Override
    public String toString() {
        return isBlank(name) ? super.toString() : ConfigEntryAggregator.class.getSimpleName() + "[" + name + "]";
    }

    public void setAllowDuplication(boolean allowDuplication) {
        this.allowDuplication = allowDuplication;
    }

    public void setName(String name) {
        this.name = name;
    }
}
