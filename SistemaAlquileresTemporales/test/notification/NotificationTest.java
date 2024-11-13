package notification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import property.Property;
import property.enums.PropertyType;

class NotificationTest {

    private NotificationManager notificationManager;
    private EventListener mobileAppListener;
    private EventListener webSiteListener;
    private PopUpWindow popUpWindow;
    private HomePagePublisher homePagePublisher;
    private Property property;

    @BeforeEach
    void setUp() {
        notificationManager = new NotificationManager();
        popUpWindow = mock(PopUpWindow.class);
        homePagePublisher = mock(HomePagePublisher.class);
        mobileAppListener = new MobileAppListener(popUpWindow);
        webSiteListener = new WebSiteListener(homePagePublisher);
        property = mock(Property.class);
    }

    @Test
    void testSubscribeAndNotifyMobileAppListener() {
        notificationManager.subscribe(EventType.PROPERTY_BOOKING, property, mobileAppListener);
        
        when(property.getPropertyType()).thenReturn(PropertyType.APARTMENT);
        PropertyEvent event = new PropertyEvent(EventType.PROPERTY_BOOKING, property);
        notificationManager.notify(EventType.PROPERTY_BOOKING, property);
        
        verify(popUpWindow).popUp("El/la APARTMENT que te interesa se ha liberado! Corre a reservarlo!", "BLACK", 12);
    }

    @Test
    void testSubscribeAndNotifyWebSiteListener() {
        notificationManager.subscribe(EventType.PRICE_DROP, property, webSiteListener);
        
        when(property.getPropertyType()).thenReturn(PropertyType.HOUSE);
        when(property.getPrice()).thenReturn(1000.0);
        PropertyEvent event = new PropertyEvent(EventType.PRICE_DROP, property);
        notificationManager.notify(EventType.PRICE_DROP, property);
        
        verify(homePagePublisher).publish("No te pierdas esta oferta: Un inmueble HOUSE a tan sólo 1000.0 pesos");
    }

    @Test
    void testUnsubscribe() {
        notificationManager.subscribe(EventType.PROPERTY_CANCELLATION, property, mobileAppListener);
        notificationManager.unsubscribe(EventType.PROPERTY_CANCELLATION, property, mobileAppListener);
        
        notificationManager.notify(EventType.PROPERTY_CANCELLATION, property);
        
        verify(popUpWindow, never()).popUp(anyString(), anyString(), anyInt());
    }

    @Test
    void testPropertyEventMethods() {
        when(property.getPrice()).thenReturn(2000.0);
        when(property.getPropertyType()).thenReturn(PropertyType.QUINCHO);
        
        PropertyEvent event = new PropertyEvent(EventType.PROPERTY_BOOKING, property);
        
        assertEquals(2000.0, event.getPropertyPrice());
        assertEquals("QUINCHO", event.getPropertyType());
    }

    @Test
    void testNotifyWithNoListeners() {
        // No se debería hacer nada si no hay listeners
        notificationManager.notify(EventType.PROPERTY_BOOKING, property);
    }
}
