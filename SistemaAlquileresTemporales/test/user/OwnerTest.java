package user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import property.Property;
import stats.Stats;

class OwnerTest {

    private Owner owner;
    private Property property;
    private Stats stats;

    @BeforeEach
    void setUp() {
        owner = new Owner("John Doe", "john@example.com", 987654321);
        property = mock(Property.class);
        stats = mock(Stats.class);
        
        // Configura la propiedad mock para devolver stats
        when(property.getStats()).thenReturn(stats);
    }

    @Test
    void testGetRentalCountForOwnedProperty() {
        owner.addProperty(property);
        when(stats.getTotalRentals()).thenReturn(5);

        int rentalCount = owner.getRentalCount(property);

        assertEquals(5, rentalCount, "El conteo de alquileres debería coincidir con el valor de stats");
    }

    @Test
    void testGetRentalCountForUnownedProperty() {
        Property otherProperty = mock(Property.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            owner.getRentalCount(otherProperty);
        });

        assertEquals("La propiedad no le pertenece al dueño", exception.getMessage());
    }

    @Test
    void testGetTotalRentals() {
        Property property1 = mock(Property.class);
        Property property2 = mock(Property.class);
        Stats stats1 = mock(Stats.class);
        Stats stats2 = mock(Stats.class);

        when(property1.getStats()).thenReturn(stats1);
        when(property2.getStats()).thenReturn(stats2);
        when(stats1.getTotalRentals()).thenReturn(3);
        when(stats2.getTotalRentals()).thenReturn(2);

        owner.addProperty(property1);
        owner.addProperty(property2);

        int totalRentals = owner.getTotalRentals();

        assertEquals(5, totalRentals, "La cantidad total de alquileres debería ser la suma de los alquileres de cada propiedad");
    }

    @Test
    void testShowDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Configura el mock de stats para `showDetails` y `getTotalRentals`
        owner.setRegisterDate(LocalDate.now());
        when(stats.getTotalRentals()).thenReturn(5);
        owner.addProperty(property);

        owner.showDetails();

        String expectedOutput = "Promedio por categoría:" + System.lineSeparator() +
                                "Promedio total: 0.0" + System.lineSeparator() +
                                "Veces que fue alquilado: 0" + System.lineSeparator() +
                                "Fecha de registro: " + owner.getRegisterDate() + System.lineSeparator() +
                                "Cantidad total de inmuebles alquilados: " + owner.getTotalRentals() + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

}
