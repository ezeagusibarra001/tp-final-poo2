package booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cancellation.CancellationPolicy;
import property.Property;
import ranking.Ranking;
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
    private CancellationPolicy cancellationPolicy;

    @BeforeEach
    void setUp() {
        tenant = mock(Tenant.class);
        owner = mock(Owner.class);
        property = mock(Property.class);
        checkInDate = new Date();
        checkOutDate = new Date(checkInDate.getTime() + (1000 * 60 * 60 * 24));
        booking = new Booking(tenant, owner, property, checkInDate, checkOutDate);

        tenantRanking = mock(Ranking.class);
        ownerRanking = mock(Ranking.class);
        propertyRanking = mock(Ranking.class);
        cancellationPolicy = mock(CancellationPolicy.class);
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
        
        booking.makeCheckout(rankings);

        Exception exception = assertThrows(IllegalStateException.class, () -> booking.makeCheckout(rankings));
        assertEquals("Reserva completada", exception.getMessage());
    }

    @Test
    void testGetCancellationPolicy() {
        when(property.getCancellationPolicy()).thenReturn(cancellationPolicy);
        assertEquals(cancellationPolicy, booking.getCancellationPolicy(), "Cancellation policy should match");
    }

    @Test
    void testGetTotalPrice() {
        assertEquals(0, booking.getTotalPrice(), "Total price should be zero by default");
    }

    @Test
    void testGetDailyPrice() {
        assertEquals(0, booking.getDailyPrice(), "Daily price should be zero by default");
    }
}
