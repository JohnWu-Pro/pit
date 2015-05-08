package org.wjh.pit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.wjh.framework.domain.Category;

@Entity
public class DistributionRule extends Category {
    private static final long serialVersionUID = 5262000927146081705L;

    private String className;
    private String paramsCsv;

    @Column(nullable = false)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParamsCsv() {
        return paramsCsv;
    }

    public void setParamsCsv(String paramsCsv) {
        this.paramsCsv = paramsCsv;
    }
}
