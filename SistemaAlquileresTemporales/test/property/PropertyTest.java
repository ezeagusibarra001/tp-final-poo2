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

import cancellation.CancellationPolicy;
import property.enums.*;
import stats.Stats;
import user.Owner;

class PropertyTest {

	private Property property;
	private SpecialPrice specialPrice1;
	private SpecialPrice specialPrice2;
	private Date checkInDate;
	private Date checkOutDate;

	@BeforeEach
	void setUp() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1);
		checkInDate = calendar.getTime();

		calendar.set(2024, Calendar.JANUARY, 10);
		checkOutDate = calendar.getTime();

		List<Service> services = Arrays.asList(mock(Service.class));
		List<Photo> photos = Arrays.asList(new Photo("http://example.com/photo1.jpg"));
		Location location = new Location("USA", "123 Main St", "New York");
		
		Owner owner = mock(Owner.class);
		CancellationPolicy cancelationPolicy = mock(CancellationPolicy.class);

		specialPrice1 = new SpecialPrice(180.0, checkInDate, checkOutDate);
		specialPrice2 = new SpecialPrice(150.0, checkInDate, checkOutDate);
		List<SpecialPrice> specialPrices = new ArrayList<SpecialPrice>();
		specialPrices.add(specialPrice1);
		specialPrices.add(specialPrice2);
		property = new Property(owner, PropertyType.APARTMENT, 100, checkInDate, checkOutDate, 200.0,
				Arrays.asList(PaymentMethod.CREDIT_CARD), 4, services, photos, location, specialPrices,
				cancelationPolicy);
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
	    Stats statsMock = mock(Stats.class);
	    when(statsMock.showDetails()).thenReturn("Estadísticas:\n- Rating: 4.5");

	    Property propertyMock = spy(property);
	    doReturn(PropertyType.APARTMENT).when(propertyMock).getPropertyType();
	    doReturn(50).when(propertyMock).getArea();
	    doReturn(1000.0).when(propertyMock).getPrice();
	    doReturn(new Location("Argentina", "Calle Falsa 123", "Buenos Aires")).when(propertyMock).getLocation();
	    doReturn(2).when(propertyMock).getGuests();
	    doReturn(Arrays.asList(Service.WI_FI)).when(propertyMock).getServices();
	    doReturn(new Date()).when(propertyMock).getTime_check_in();
	    doReturn(new Date()).when(propertyMock).getTime_check_out();
	    doReturn(Arrays.asList(PaymentMethod.CREDIT_CARD)).when(propertyMock).getPaymentMethods();
	    doReturn(Arrays.asList(new Photo("http://example.com/photo.jpg"))).when(propertyMock).getPhotos();
	    doReturn(statsMock).when(propertyMock).getStats();  

	    String expectedOutput = "Detalles de propiedad:\n" +
	                            "Tipo: Apartamento\n" +
	                            "Superficie: 50 m²\n" +
	                            "Precio: $1000.0\n" +
	                            "Ubicación:\n" +
	                            "  - País: Argentina\n" +
	                            "  - Ciudad: Buenos Aires\n" +
	                            "  - Dirección: Calle Falsa 123\n" +
	                            "Capacidad: 2 personas\n" +
	                            "Servicios:\n" +
	                            "  - " + Service.WI_FI.getDescription() + "\n" +
	                            "Horario:\n" +
	                            "  - Check-in: " + propertyMock.getTime_check_in() + "\n" +
	                            "  - Check-out: " + propertyMock.getTime_check_out() + "\n" +
	                            "Métodos de pago:\n" +
	                            "  - Tarjeta de Crédito\n" +
	                            "Fotos:\n" +
	                            "  - http://example.com/photo.jpg\n" +
	                            "Estadísticas:\n- Rating: 4.5";

	    assertEquals(expectedOutput, propertyMock.showDetails(), "La salida de showDetails debería coincidir con el formato esperado");
	}




	
	@Test
	void testGetCancellationPolicy() {
	    assertNotNull(property.getCancellationPolicy(), "Cancellation policy should not be null");
	}

	@Test
	void testGetOwner() {
	    assertNotNull(property.getOwner(), "Owner should not be null");
	}

	@Test
	void testGetPaymentMethods() {
	    List<PaymentMethod> paymentMethods = property.getPaymentMethods();
	    assertNotNull(paymentMethods, "Payment methods should not be null");
	    assertTrue(paymentMethods.contains(PaymentMethod.CREDIT_CARD), "Payment methods should include CREDIT_CARD");
	}

	@Test
	void testGetPhotos() {
	    List<Photo> photos = property.getPhotos();
	    assertNotNull(photos, "Photos should not be null");
	    assertEquals(1, photos.size(), "There should be one photo");
	    assertEquals("http://example.com/photo1.jpg", photos.get(0).getUrl(), "Photo URL should match");
	}

	@Test
	void testGetRanking() {
	    assertNotNull(property.getRanking(), "Ranking should not be null");
	}

	@Test
	void testGetServices() {
	    List<Service> services = property.getServices();
	    assertNotNull(services, "Services should not be null");
	    assertFalse(services.isEmpty(), "Services should not be empty");
	}

	@Test
	void testGetTimeCheckIn() {
	    assertEquals(checkInDate, property.getTime_check_in(), "Check-in date should match the expected value");
	}

	@Test
	void testGetTimeCheckOut() {
	    assertEquals(checkOutDate, property.getTime_check_out(), "Check-out date should match the expected value");
	}

	@Test
	void testIsAvailableBetweenDates() {
	    Date startDate = checkInDate;
	    Date endDate = checkOutDate;
	    assertTrue(property.isAvailableBetween(startDate, endDate), "Property should be available between these dates");
	}

}
