package org.wjh.framework.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@MappedSuperclass
public abstract class Category implements Serializable {
    private static final long serialVersionUID = 6624703054672565972L;

    private String id;
    private String name;
    private String description;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (!getClass().isInstance(obj)) {
            return false;
        }

        Category rhs = (Category) obj;
        return new EqualsBuilder()// @formatter:off
                .append(getId(), rhs.getId())
                .isEquals();// @formatter:on
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()// @formatter:off
                .append(getId())
                .toHashCode();// @formatter:on
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)// @formatter:off
                .append("id", getId())
                .append("name", getName())
                .append("description", getDescription())
                .toString();// @formatter:on
    }
}
