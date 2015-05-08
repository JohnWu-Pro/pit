package org.wjh.pit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.wjh.framework.domain.Profile;

@Entity
public class Portfolio extends Profile {
    private static final long serialVersionUID = -2962070197997363378L;

    private RegistrationType type;
    private String issuerId;
    private String tag;

    private SecurityIssuer issuer;

    @Column(name = "RegType", nullable = false)
    public RegistrationType getType() {
        return type;
    }

    public void setType(RegistrationType type) {
        this.type = type;
    }

    @Column(nullable = false)
    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Transient
    public SecurityIssuer getIssuer() {
        return issuer;
    }

    public void setIssuer(SecurityIssuer issuer) {
        this.issuer = issuer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)// @formatter:off
                .append("id", getId())
                .append("name", getName())
                .append("description", getDescription())
                .append("type", getType())
                .append("issuerId", getIssuerId())
                .append("lastUpdated", getLastUpdated())
                .toString();// @formatter:on
    }
}
