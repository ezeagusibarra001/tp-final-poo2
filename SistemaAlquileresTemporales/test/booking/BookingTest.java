package booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import property.Property;
import ranking.Ranking;
import ranking.RankingType;
import user.Owner;
import user.Tenant;

class BookingTest {

    private Booking booking;
    private Tenant tenant;
    private Owner owner;
    private Property property;
    private Date checkInDate;
    private Date checkOutDate;
    private Ranking tenantRanking;
    private Ranking ownerRanking;
    private Ranking propertyRanking;

    @BeforeEach
    void setUp() {
        tenant = mock(Tenant.class);
        owner = mock(Owner.class);
        property = mock(Property.class);
        checkInDate = new Date();
        checkOutDate = new Date(checkInDate.getTime() + (1000 * 60 * 60 * 24)); // Un día después
        booking = new Booking(tenant, owner, property, checkInDate, checkOutDate);

        tenantRanking = mock(Ranking.class);
        ownerRanking = mock(Ranking.class);
        propertyRanking = mock(Ranking.class);
    }

    @Test
    void testConfirmWhenPropertyIsAvailable() {
        when(property.isAvailable()).thenReturn(true);

        booking.confirm();

        assertTrue(booking.isCompleted(), "Booking should be marked as completed when property is available");
        verify(property).setAvailable(false);
    }

    @Test
    void testConfirmWhenPropertyIsNotAvailable() {
        when(property.isAvailable()).thenReturn(false);

        booking.confirm();

        assertFalse(booking.isCompleted(), "Booking should not be completed when property is not available");
        verify(property, never()).setAvailable(false);
    }

    @Test
    void testMakeCheckoutSuccessfully() {
        when(property.isAvailable()).thenReturn(false);
        List<Ranking> rankings = Arrays.asList(tenantRanking, ownerRanking, propertyRanking);

        booking.makeCheckout(rankings);

        assertTrue(booking.isCompleted(), "Booking should be marked as completed after checkout");
        verify(property).setAvailable(true);
        verify(tenant).rateAfterCheckout(owner, property, ownerRanking, propertyRanking);
        verify(owner).rateAfterCheckout(tenant, property, tenantRanking, propertyRanking);
    }

    @Test
    void testMakeCheckoutWhenAlreadyCompleted() {
        when(property.isAvailable()).thenReturn(false);
        List<Ranking> rankings = Arrays.asList(tenantRanking, ownerRanking, propertyRanking);
        
        // First checkout to mark the booking as completed
        booking.makeCheckout(rankings);

        // Attempting a second checkout should throw an exception
        Exception exception = assertThrows(IllegalStateException.class, () -> booking.makeCheckout(rankings));
        assertEquals("Reserva completada", exception.getMessage());
    }
}
