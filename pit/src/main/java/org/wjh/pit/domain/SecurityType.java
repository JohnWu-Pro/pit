package org.wjh.pit.domain;

import org.wjh.framework.domain.IdAwareEnum;

public enum SecurityType implements IdAwareEnum<String> {
    // @formatter:off
    /** Mutual Fund */
    MUTUAL_FUND("MF"),

    /** Stock */
    STOCK("STK"),
    // @formatter:on
    ;

    private String id;

    private SecurityType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
