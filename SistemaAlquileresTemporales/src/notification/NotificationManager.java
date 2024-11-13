package notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import property.Property;

public class NotificationManager {
	
	Map<EventType, Map<Property, List<EventListener>>> listeners;

	public NotificationManager() {
        this.setListeners(new HashMap<>());
        for (EventType eventType : EventType.values()) {
            listeners.put(eventType, new HashMap<>());
        }
    }

    private void setListeners(Map<EventType, Map<Property, List<EventListener>>> listeners) {
		this.listeners = listeners;		
	}

	public void subscribe(EventType eventType, Property property, EventListener listener) {
        listeners.get(eventType).computeIfAbsent(property, k -> new ArrayList<>()).add(listener);
    }

    public void unsubscribe(EventType eventType, Property property, EventListener listener) {
        List<EventListener> propertyListeners = listeners.get(eventType).get(property);
        if (propertyListeners != null) {
            propertyListeners.remove(listener);
        }
    }

    public void notify(EventType eventType, Property property) {
        PropertyEvent event = new PropertyEvent(eventType, property);
        List<EventListener> propertyListeners = listeners.get(eventType).get(property);
        
        if (propertyListeners != null) {
            propertyListeners.forEach(listener -> listener.update(event));
        }
    }

}