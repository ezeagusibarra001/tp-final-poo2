package notification;

import java.util.Date;

import property.Property;


public class PropertyEvent {
    
    private EventType eventType;
    private Property property;
    
    
    public PropertyEvent(EventType eventType, Property property) {
    	this.setEventType(eventType);
        this.setProperty(property);        
    }



	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}



	public double getPropertyPrice() {
		return property.getPrice();
	}
	
	public String getPropertyType() {
		return property.getPropertyType().toString(); 
	}

	public boolean propertyIsAvailableBetween(Date startDate, Date endDate) {
		return property.isAvailableBetween(startDate, endDate);
	}

	public void setProperty(Property property) {
		this.property = property;
	}

}

