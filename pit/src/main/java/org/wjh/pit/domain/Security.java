package org.wjh.pit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.wjh.framework.domain.Profile;

@Entity
public class Security extends Profile {
    private static final long serialVersionUID = 2256904042596931753L;

    private SecurityType type;
    private Integer issuerId;
    private String issuerCode;
    private String googleCode;
    private String distributionSetting;

    private SecurityIssuer issuer;

    @Column(nullable = false)
    public SecurityType getType() {
        return type;
    }

    public void setType(SecurityType type) {
        this.type = type;
    }

    @Column(nullable = false)
    public Integer getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Integer issuerId) {
        this.issuerId = issuerId;
    }

    @Column(nullable = false)
    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    public String getGoogleCode() {
        return googleCode;
    }

    public void setGoogleCode(String googleCode) {
        this.googleCode = googleCode;
    }

    public String getDistributionSetting() {
        return distributionSetting;
    }

    public void setDistributionSetting(String distributionSetting) {
        this.distributionSetting = distributionSetting;
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
                .append("issuerCode", getIssuerCode())
                .append("googleCode", getGoogleCode())
                .append("distributionSetting", getDistributionSetting())
                .append("lastUpdated", getLastUpdated())
                .toString();// @formatter:on
    }
}
