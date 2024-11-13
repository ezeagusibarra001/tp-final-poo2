package property;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import property.enums.*;

class PropertyTest {

    private Property property;
    private SpecialPrice specialPrice1;
    private SpecialPrice specialPrice2;
    private Date checkInDate;
    private Date checkOutDate;

    @BeforeEach
    void setUp() {
        // Define check-in and check-out dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        checkInDate = calendar.getTime();

        calendar.set(2024, Calendar.JANUARY, 10);
        checkOutDate = calendar.getTime();

        // Mock services, photos, and special prices
        List<Service> services = Arrays.asList(mock(Service.class));
        List<Photo> photos = Arrays.asList(new Photo("http://example.com/photo1.jpg"));
        Location location = new Location("USA", "123 Main St", "New York");

        // Create a property with basic attributes and special prices
        specialPrice1 = new SpecialPrice(180.0, checkInDate, checkOutDate);
        specialPrice2 = new SpecialPrice(150.0, checkInDate, checkOutDate);
        List<SpecialPrice> specialPrices = new ArrayList<SpecialPrice>();
        specialPrices.add(specialPrice1);
        specialPrices.add(specialPrice2);
        property = new Property(
            PropertyType.APARTMENT,
            100,
            checkInDate,
            checkOutDate,
            200.0,
            Arrays.asList(PaymentMethod.CREDIT_CARD),
            4,
            services,
            photos,
            location,
            specialPrices
        );
    }

    @Test
    void testAvailability() {
        assertTrue(property.isAvailable(), "Property should be available initially");
    }

    @Test
    void testSetAvailability() {
        property.setAvailable(false);
        assertFalse(property.isAvailable(), "Property availability should be set to false");
    }

    @Test
    void testGetHighestPriceBetweenDates() {
        double highestPrice = property.getHighestPriceBetween(checkInDate, checkOutDate);
        assertEquals(180.0, highestPrice, 0.01, "Highest price should be the base price if no special prices");
    }

    @Test
    void testGetLowestPriceBetweenDates() {
        double lowestPrice = property.getLowerPriceBetween(checkInDate, checkOutDate);
        assertEquals(150.0, lowestPrice, 0.01, "Lowest price should be the lowest special price");
    }

    @Test
    void testAddSpecialPrice() {
        SpecialPrice newSpecialPrice = new SpecialPrice(130.0, checkInDate, checkOutDate);
        property.addSpecialPrice(newSpecialPrice);
        assertTrue(property.getSpecialPrices().contains(newSpecialPrice), "Special price should be added to property");
    }

    @Test
    void testShowDetails() {
        // Check if details are printed correctly
        property.showDetails();
        // This would require manual checking for correct output or could use a logging system for validation
    }
}
