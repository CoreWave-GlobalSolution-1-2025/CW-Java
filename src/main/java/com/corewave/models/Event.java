package com.corewave.models;

import java.util.Objects;

public class Event extends _BaseEntity {
    private EVENT_TYPE eventType;
    private String place;
    private String description;

    public Event() {
    }

    public EVENT_TYPE getEventType() {
        return eventType;
    }

    public void setEventType(EVENT_TYPE eventType) {
        this.eventType = eventType;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Event event = (Event) o;
        return getEventType() == event.getEventType() && Objects.equals(getPlace(), event.getPlace()) && Objects.equals(getDescription(), event.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEventType(), getPlace(), getDescription());
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventType=" + eventType +
                ", place='" + place + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
