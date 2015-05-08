package org.wjh.pit.domain;

import org.wjh.framework.domain.IdAwareEnum;

public enum RegistrationType implements IdAwareEnum<String> {
    // @formatter:off
    /** Open (Non-registered) */
    OPEN("OPEN"),

    /** Registered Education Savings Plan */
    RESP("RESP"),

    /** Registered Retirement Savings Plan */
    RRSP("RRSP"),

    /** Tax-Free Savings Account */
    TFSA("TFSA"),
    // @formatter:on
    ;

    private String id;

    private RegistrationType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
