package com.corewave.services;

import com.corewave.models.Event;

public class EventService {

    public static boolean checkEventToCreate(Event event) {
        return
                event.getName() != null &&
                        event.getEventRisk() != null &&
                        event.getEventType() != null &&
                        event.getPlace() != null &&
                        event.getId() == null;
    }

    public static boolean checkEventToUpdate(Event event) {
        return
                event.getName() != null ||
                        event.getEventRisk() != null ||
                        event.getEventType() != null ||
                        event.getPlace() != null ||
                        event.isDeleted() != null;
    }
}
