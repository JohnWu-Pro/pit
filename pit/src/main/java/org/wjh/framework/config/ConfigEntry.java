package org.wjh.framework.config;

import javax.annotation.PostConstruct;

public class ConfigEntry<E> {

    private ConfigEntryAggregator<E> aggregator;
    private E value;

    @PostConstruct
    public void register() {
        if (aggregator == null) {
            throw new IllegalStateException("Aggregator must be provided.");
        }
        aggregator.add(value);
        aggregator = null; // Clears the reference to the aggregator
    }

    public void setAggregator(ConfigEntryAggregator<E> aggregator) {
        this.aggregator = aggregator;
    }

    public void setValue(E value) {
        this.value = value;
    }

}
