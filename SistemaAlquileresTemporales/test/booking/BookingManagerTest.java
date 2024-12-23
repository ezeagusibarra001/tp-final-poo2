package booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cancellation.CancellationPolicy;
import notification.NotificationManager;
import property.Location;
import property.Property;
import site.Site;
import user.Tenant;

class BookingManagerTest {

    private BookingManager bookingManager;
    private Site site;
    private Tenant tenant;
    private Property property;
    private Booking booking;

    @BeforeEach
    void setUp() {
        bookingManager = new BookingManager();
        site = mock(Site.class);

        tenant = mock(Tenant.class);
        property = mock(Property.class);
        booking = mock(Booking.class);
    }

    @Test
    void testAddBooking() {
        bookingManager.addBooking(booking);
        assertTrue(bookingManager.getBookings().contains(booking), "Booking should be added to the manager");
    }

    @Test
    void testCancelBooking() {
        CancellationPolicy cancellationPolicy = mock(CancellationPolicy.class);
        
        when(property.getCancellationPolicy()).thenReturn(cancellationPolicy);
        when(booking.getProperty()).thenReturn(property);
        when(booking.getCheckInDate()).thenReturn(new Date());
        when(booking.getTotalPrice()).thenReturn(100.0);
        when(booking.getDailyPrice()).thenReturn(20.0);
        when(booking.getCancellationPolicy()).thenReturn(cancellationPolicy);
        when(booking.getTenant()).thenReturn(tenant);
        when(cancellationPolicy.calculateRefund(100.0, 0, 20.0)).thenReturn(80.0);

        bookingManager.addBooking(booking);
        bookingManager.cancelBooking(site, booking);

        assertFalse(bookingManager.getBookings().contains(booking), "Booking should be removed after cancellation");

        verify(site).notifyEvent(any(), eq(property));
    }

    @Test
    void testCreateBookingWhenPropertyIsAvailable() {
        when(property.isAvailableBetween(any(), any())).thenReturn(true);
        Date checkIn = new Date();
        Date checkOut = new Date(checkIn.getTime() + 86400000);

        bookingManager.createBooking(site, tenant, property, checkIn, checkOut);

        assertEquals(1, bookingManager.getBookings().size(), "Booking should be created and confirmed when property is available");
    }
    
    @Test
    void testAddToWaitingListOf() {
        BookingQueue bookingQueue = mock(BookingQueue.class);
        Map<Property, BookingQueue> waitingList = new HashMap<>();
        waitingList.put(property, bookingQueue);
        bookingManager.setWaitingList(waitingList);

        bookingManager.addToWaitingListOf(property, booking);

        verify(bookingQueue).addConditionalBooking(booking);
    }
    
    @Test
    void testGetUserBookings() {
        Booking booking1 = mock(Booking.class);
        Booking booking2 = mock(Booking.class);
        Tenant tenant2 = mock(Tenant.class);

        when(booking.getTenant()).thenReturn(tenant);
        when(booking1.getTenant()).thenReturn(tenant);
        when(booking2.getTenant()).thenReturn(tenant2);

        bookingManager.addBooking(booking);
        bookingManager.addBooking(booking1);
        bookingManager.addBooking(booking2);

        List<Booking> result = bookingManager.getUserBookings(tenant);

        assertEquals(2, result.size(), "Should return only bookings for the specific tenant");
        assertTrue(result.contains(booking));
        assertTrue(result.contains(booking1));
        assertFalse(result.contains(booking2));
    }

    @Test
    void testGetUserFutureBookings() {
        Date pastDate = new Date(System.currentTimeMillis() - 86400000);
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);

        Booking pastBooking = mock(Booking.class);
        Booking futureBooking = mock(Booking.class);

        when(pastBooking.getTenant()).thenReturn(tenant);
        when(pastBooking.getCheckInDate()).thenReturn(pastDate);
        when(futureBooking.getTenant()).thenReturn(tenant);
        when(futureBooking.getCheckInDate()).thenReturn(futureDate);

        bookingManager.addBooking(pastBooking);
        bookingManager.addBooking(futureBooking);

        List<Booking> result = bookingManager.getUserFutureBookings(tenant);

        assertEquals(1, result.size(), "Should return only future bookings");
        assertTrue(result.contains(futureBooking));
        assertFalse(result.contains(pastBooking));
    }

    @Test
    void testGetUserBookingsInCity() {
        Booking bookingInCity = mock(Booking.class);
        Booking bookingOutOfCity = mock(Booking.class);
        Property propertyInCity = mock(Property.class);
        Property propertyOutOfCity = mock(Property.class);
        Location locationInCity = mock(Location.class);
        Location locationOutOfCity = mock(Location.class);

        when(bookingInCity.getTenant()).thenReturn(tenant);
        when(bookingOutOfCity.getTenant()).thenReturn(tenant);
        when(bookingInCity.getProperty()).thenReturn(propertyInCity);
        when(bookingOutOfCity.getProperty()).thenReturn(propertyOutOfCity);
        when(propertyInCity.getLocation()).thenReturn(locationInCity);
        when(propertyOutOfCity.getLocation()).thenReturn(locationOutOfCity);
        when(locationInCity.getCity()).thenReturn("New York");
        when(locationOutOfCity.getCity()).thenReturn("Los Angeles");

        bookingManager.addBooking(bookingInCity);
        bookingManager.addBooking(bookingOutOfCity);

        List<Booking> result = bookingManager.getUserBookingsInCity(tenant, "New York");

        assertEquals(1, result.size(), "Should return only bookings in the specific city");
        assertTrue(result.contains(bookingInCity));
        assertFalse(result.contains(bookingOutOfCity));
    }

    @Test
    void testGetCitiesWithUserBookings() {
        Booking bookingInNY = mock(Booking.class);
        Booking bookingInLA = mock(Booking.class);
        Property propertyNY = mock(Property.class);
        Property propertyLA = mock(Property.class);
        Location locationNY = mock(Location.class);
        Location locationLA = mock(Location.class);

        when(bookingInNY.getTenant()).thenReturn(tenant);
        when(bookingInLA.getTenant()).thenReturn(tenant);
        when(bookingInNY.getProperty()).thenReturn(propertyNY);
        when(bookingInLA.getProperty()).thenReturn(propertyLA);
        when(propertyNY.getLocation()).thenReturn(locationNY);
        when(propertyLA.getLocation()).thenReturn(locationLA);
        when(locationNY.getCity()).thenReturn("New York");
        when(locationLA.getCity()).thenReturn("Los Angeles");

        bookingManager.addBooking(bookingInNY);
        bookingManager.addBooking(bookingInLA);

        List<String> cities = bookingManager.getCitiesWithUserBookings(tenant);

        assertEquals(2, cities.size(), "Should return the cities with user bookings");
        assertTrue(cities.contains("New York"));
        assertTrue(cities.contains("Los Angeles"));
    }
}
