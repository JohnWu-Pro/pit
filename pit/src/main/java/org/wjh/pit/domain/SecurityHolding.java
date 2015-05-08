package org.wjh.pit.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class SecurityHolding implements Serializable {
    private static final long serialVersionUID = 2256904042596931757L;

    private Integer id;
    private Integer portfolioId;
    private Integer securityId;
    private BigDecimal units;
    private BigDecimal totalContribution;
    private Date lastUpdated;

    private Portfolio portfolio;
    private Security security;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    @Column(nullable = false)
    public Integer getSecurityId() {
        return securityId;
    }

    public void setSecurityId(Integer securityId) {
        this.securityId = securityId;
    }

    @Column(nullable = false)
    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    @Column(nullable = false)
    public BigDecimal getTotalContribution() {
        return totalContribution;
    }

    public void setTotalContribution(BigDecimal totalContribution) {
        this.totalContribution = totalContribution;
    }

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Transient
    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Transient
    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)// @formatter:off
                .append("id", getId())
                .append("portfolioId", getPortfolioId())
                .append("securityId", getSecurityId())
                .append("units", getUnits())
                .append("totalContribution", getTotalContribution())
                .append("lastUpdated", getLastUpdated())
                .toString();// @formatter:on
    }
}
