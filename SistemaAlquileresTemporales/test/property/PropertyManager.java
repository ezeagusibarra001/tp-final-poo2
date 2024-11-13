package property;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import property.search.Filter;
import user.Owner;

class PropertiesManagerTest {

    private PropertiesManager propertiesManager;
    private Property property1;
    private Property property2;
    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = mock(Owner.class);

        // Initialize PropertiesManager with mock properties
        propertiesManager = new PropertiesManager();

        // Create mock properties with different configurations
        property1 = mock(Property.class);
        property2 = mock(Property.class);
        propertiesManager.setProperties(Arrays.asList(property1, property2));
    }

    @Test
    void testPropertiesManagerConstructorWithProperties() {
        Property property1 = mock(Property.class);
        Property property2 = mock(Property.class);

        List<Property> properties = Arrays.asList(property1, property2);
        PropertiesManager propertiesManager = new PropertiesManager(properties);

        assertEquals(2, propertiesManager.getProperties().size(), "Properties manager should contain the two initialized properties");
        assertTrue(propertiesManager.getProperties().contains(property1), "Properties manager should contain property1");
        assertTrue(propertiesManager.getProperties().contains(property2), "Properties manager should contain property2");
    }

    @Test
    void testPostProperty() {
        Property newProperty = mock(Property.class);

        // Call the method to test
        propertiesManager.post(newProperty, owner);

        // Verify the new property is added to the list
        assertTrue(propertiesManager.getProperties().contains(newProperty), "Posted property should be added to the list");
    }

    @Test
    void testSearchPropertiesWithNoFilters() {
        List<Property> result = propertiesManager.search(Arrays.asList());

        assertEquals(2, result.size(), "All properties should be returned when there are no filters");
    }

    @Test
    void testSearchPropertiesWithFilter() {
        Filter filter = mock(Filter.class);
        when(filter.matches(property1)).thenReturn(true);
        when(filter.matches(property2)).thenReturn(false);

        List<Property> result = propertiesManager.search(Arrays.asList(filter));

        assertEquals(1, result.size(), "Only properties matching the filter should be returned");
        assertTrue(result.contains(property1), "Matching property should be included in results");
        assertFalse(result.contains(property2), "Non-matching property should not be included");
    }

    @Test
    void testSearchPropertiesWithMultipleFilters() {
        Filter filter1 = mock(Filter.class);
        Filter filter2 = mock(Filter.class);
        
        when(filter1.matches(property1)).thenReturn(true);
        when(filter2.matches(property1)).thenReturn(true);
        when(filter1.matches(property2)).thenReturn(false);

        List<Property> result = propertiesManager.search(Arrays.asList(filter1, filter2));

        assertEquals(1, result.size(), "Only properties matching all filters should be returned");
        assertTrue(result.contains(property1), "Matching property should be included in results");
    }

    @Test
    void testPassFilterMethodWithAllMatchingFilters() {
        Filter filter1 = mock(Filter.class);
        Filter filter2 = mock(Filter.class);

        when(filter1.matches(property1)).thenReturn(true);
        when(filter2.matches(property1)).thenReturn(true);

        assertTrue(propertiesManager.passFilter(property1, Arrays.asList(filter1, filter2)), "Property should pass when all filters match");
    }

    @Test
    void testPassFilterMethodWithNonMatchingFilter() {
        Filter filter1 = mock(Filter.class);
        Filter filter2 = mock(Filter.class);

        when(filter1.matches(property1)).thenReturn(true);
        when(filter2.matches(property1)).thenReturn(false);

        assertFalse(propertiesManager.passFilter(property1, Arrays.asList(filter1, filter2)), "Property should not pass when one filter does not match");
    }
}
