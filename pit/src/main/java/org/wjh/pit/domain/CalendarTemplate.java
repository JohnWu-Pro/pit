package org.wjh.pit.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.wjh.framework.domain.Profile;

@Entity
public class CalendarTemplate extends Profile {
    private static final long serialVersionUID = 2256904042596931750L;

    private Integer calendarId;
    private Date startDate;
    private Date endDate;
    private String recurrenceSetting;

    private Calendar calendar;

    @Column(nullable = false)
    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    @Column(nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(nullable = false)
    public String getRecurrenceSetting() {
        return recurrenceSetting;
    }

    public void setRecurrenceSetting(String recurrenceSetting) {
        this.recurrenceSetting = recurrenceSetting;
    }

    @Transient
    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)// @formatter:off
                .append("id", getId())
                .append("calendarId", getCalendarId())
                .append("name", getName())
                .append("description", getDescription())
                .append("startDate", getStartDate())
                .append("endDate", getEndDate())
                .append("recurrenceSetting", getRecurrenceSetting())
                .append("lastUpdated", getLastUpdated())
                .toString();// @formatter:on
    }
}
