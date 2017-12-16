package com.agykoala.timemanager.DB;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class EventDTO extends RealmObject {

    @Required
    String name, description, date, timeStart, timeEnd;

    public EventDTO() {
    }

    public EventDTO(String date, String name, String description, String timeStart, String timeEnd) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
