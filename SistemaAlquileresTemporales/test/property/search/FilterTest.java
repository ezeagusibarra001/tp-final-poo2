package property.search;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import property.Property;

class FilterTest {

    private Filter mockFilter1;
    private Filter mockFilter2;

    @BeforeEach
    void setUp() {
        mock(Property.class);
        mockFilter1 = mock(Filter.class);
        mockFilter2 = mock(Filter.class);
    }

    // Tests for AndFilter

    @Test
    void testAndFilterAddFilters() {
        AndFilter andFilter = new AndFilter(new ArrayList<>());
        andFilter.addFilter(mockFilter1);
        assertEquals(1, andFilter.getFilters().size(), "AndFilter should contain one filter after adding it");
    }

    @Test
    void testAndFilterRemoveFilters() {
        List<Filter> filters = new ArrayList<>();
        filters.add(mockFilter1);
        filters.add(mockFilter2);

        AndFilter andFilter = new AndFilter(filters);
        andFilter.removeFilter(mockFilter1);
        
        assertEquals(1, andFilter.getFilters().size(), "AndFilter should contain one filter after removing one");
        assertFalse(andFilter.getFilters().contains(mockFilter1), "The removed filter should not be in AndFilter");
    }

    @Test
    void testAndFilterGetFilters() {
        List<Filter> filters = new ArrayList<>();
        filters.add(mockFilter1);

        AndFilter andFilter = new AndFilter(filters);
        assertEquals(filters, andFilter.getFilters(), "AndFilter should return the correct list of filters");
    }

    // Tests for OrFilter

    @Test
    void testOrFilterAddFilters() {
        OrFilter orFilter = new OrFilter(new ArrayList<>());
        orFilter.addFilter(mockFilter1);
        assertEquals(1, orFilter.getFilters().size(), "OrFilter should contain one filter after adding it");
    }

    @Test
    void testOrFilterRemoveFilters() {
        List<Filter> filters = new ArrayList<>();
        filters.add(mockFilter1);
        filters.add(mockFilter2);

        OrFilter orFilter = new OrFilter(filters);
        orFilter.removeFilter(mockFilter1);

        assertEquals(1, orFilter.getFilters().size(), "OrFilter should contain one filter after removing one");
        assertFalse(orFilter.getFilters().contains(mockFilter1), "The removed filter should not be in OrFilter");
    }

    @Test
    void testOrFilterGetFilters() {
        List<Filter> filters = new ArrayList<>();
        filters.add(mockFilter1);

        OrFilter orFilter = new OrFilter(filters);
        assertEquals(filters, orFilter.getFilters(), "OrFilter should return the correct list of filters");
    }
}
