package org.wjh.pit.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.wjh.framework.domain.Profile;

@Entity
public class CalendarEntry extends Profile {
    private static final long serialVersionUID = 2256904042596931751L;

    private Integer calendarId;
    private Date date;
    private Integer templateId;

    private Calendar calendar;
    private CalendarTemplate template;

    @Column(nullable = false)
    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    @Column(nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    @Transient
    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Transient
    public CalendarTemplate getTemplate() {
        return template;
    }

    public void setTemplate(CalendarTemplate template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)// @formatter:off
                .append("id", getId())
                .append("calendarId", getCalendarId())
                .append("name", getName())
                .append("description", getDescription())
                .append("date", getDate())
                .append("templateId", getTemplateId())
                .append("lastUpdated", getLastUpdated())
                .toString();// @formatter:on
    }
}
