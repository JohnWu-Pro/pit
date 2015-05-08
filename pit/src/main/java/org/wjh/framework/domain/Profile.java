package org.wjh.framework.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@MappedSuperclass
public abstract class Profile implements Serializable {
    private static final long serialVersionUID = -8452933776570413663L;

    private Integer id;
    private String name;
    private String description;
    private Date lastUpdated;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
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

        Profile rhs = (Profile) obj;
        return new EqualsBuilder()// @formatter:off
                .append(getId(), rhs.getId())
                .append(getName(), rhs.getName())
                .isEquals();// @formatter:on
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()// @formatter:off
                .append(getId())
                .append(getName())
                .toHashCode();// @formatter:on
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)// @formatter:off
                .append("id", getId())
                .append("name", getName())
                .append("description", getDescription())
                .append("lastUpdated", getLastUpdated())
                .toString();// @formatter:on
    }
}
