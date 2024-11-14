package site;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import property.Location;
import property.PropertiesManager;
import property.Property;
import property.search.*;
import ranking.RankingManager;
import ranking.RankingStrategy;
import ranking.SimpleRankingStrategy;

class SiteFilterTest {
    PropertiesManager propertiesManager = new PropertiesManager();
    RankingManager rankingManager = mock(RankingManager.class);
    SiteRegister siteRegister = mock(SiteRegister.class);
    Site airbnb = new Site("AIRBNB",siteRegister, propertiesManager, rankingManager);

    Date startDate;
    Date endDate;
    Property property1;
    Property property2;
    Property property3;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        startDate = calendar.getTime();

        calendar.set(2024, Calendar.DECEMBER, 31);
        endDate = calendar.getTime();

        property1 = mock(Property.class);
        Location location1 = mock(Location.class);
        when(location1.getCity()).thenReturn("New York");
        when(property1.getLocation()).thenReturn(location1);
        when(property1.getHighestPriceBetween(startDate, endDate)).thenReturn(200.0);
        when(property1.getLowerPriceBetween(startDate, endDate)).thenReturn(200.0);
        when(property1.getGuests()).thenReturn(4);
        when(property1.isAvailableBetween(startDate, endDate)).thenReturn(true);

        property2 = mock(Property.class);
        Location location2 = mock(Location.class);
        when(location2.getCity()).thenReturn("Los Angeles");
        when(property2.getLocation()).thenReturn(location2);
        when(property2.getHighestPriceBetween(startDate, endDate)).thenReturn(200.0);
        when(property2.getLowerPriceBetween(startDate, endDate)).thenReturn(200.0);
        when(property2.getGuests()).thenReturn(4);
        when(property2.isAvailableBetween(startDate, endDate)).thenReturn(true);

        property3 = mock(Property.class);
        Location location3 = mock(Location.class);
        when(location3.getCity()).thenReturn("New York");
        when(property3.getLocation()).thenReturn(location3);
        when(property3.getHighestPriceBetween(startDate, endDate)).thenReturn(300.0);
        when(property3.getLowerPriceBetween(startDate, endDate)).thenReturn(300.0);
        when(property3.getGuests()).thenReturn(6);
        when(property3.isAvailableBetween(startDate, endDate)).thenReturn(false);

        propertiesManager.setProperties(Arrays.asList(property1, property2, property3));
    }

    @Test
    void testPriceFilterWithDates() {
        PriceFilter priceFilter = new PriceFilter(150.0, 250.0, startDate, endDate);
        List<Property> filteredProperties = airbnb.searchProperties(Arrays.asList(priceFilter));

        assertEquals(2, filteredProperties.size());
        assertTrue(filteredProperties.contains(property1));
    }

    @Test
    void testGuestFilter() {
        GuestFilter guestFilter = new GuestFilter(5);
        List<Property> filteredProperties = airbnb.searchProperties(Arrays.asList(guestFilter));

        assertEquals(1, filteredProperties.size());
        assertTrue(filteredProperties.contains(property3));
    }

    @Test
    void testCombinedFilters() {
        PriceFilter priceFilter = new PriceFilter(150.0, 250.0, startDate, endDate);
        GuestFilter guestFilter = new GuestFilter(4);
        CityFilter cityFilter = new CityFilter("New York");
        DateFilter dateFilter = new DateFilter(startDate, endDate);
        AndFilter combinedFilter = new AndFilter(Arrays.asList(priceFilter, guestFilter, cityFilter, dateFilter));

        List<Property> filteredProperties = airbnb.searchProperties(Arrays.asList(combinedFilter));

        assertEquals(1, filteredProperties.size());
        assertTrue(filteredProperties.contains(property1));
    }

    @Test
    void testCityFilter() {
        CityFilter cityFilter = new CityFilter("New York");
        List<Property> filteredProperties = airbnb.searchProperties(Arrays.asList(cityFilter));

        assertEquals(2, filteredProperties.size());
        assertTrue(filteredProperties.contains(property1));
        assertTrue(filteredProperties.contains(property3));
    }

    @Test
    void testDateFilter() {
        DateFilter dateFilter = new DateFilter(startDate, endDate);
        List<Property> filteredProperties = airbnb.searchProperties(Arrays.asList(dateFilter));

        assertEquals(2, filteredProperties.size());
        assertTrue(filteredProperties.contains(property1));
        assertTrue(filteredProperties.contains(property2));
    }

    @Test
    void testOrFilter() {
        PriceFilter priceFilter = new PriceFilter(150.0, 250.0, startDate, endDate);
        GuestFilter guestFilter = new GuestFilter(10);
        OrFilter orFilter = new OrFilter(Arrays.asList(priceFilter, guestFilter));

        List<Property> filteredProperties = airbnb.searchProperties(Arrays.asList(orFilter));

        assertEquals(2, filteredProperties.size());
        assertTrue(filteredProperties.contains(property1));
        assertTrue(filteredProperties.contains(property2));
    }

    @Test
    void testAndFilterWithDateAndCity() {
        DateFilter dateFilter = new DateFilter(startDate, endDate);
        CityFilter cityFilter = new CityFilter("New York");
        AndFilter andFilter = new AndFilter(Arrays.asList(dateFilter, cityFilter));

        List<Property> filteredProperties = airbnb.searchProperties(Arrays.asList(andFilter));

        assertEquals(1, filteredProperties.size());
        assertTrue(filteredProperties.contains(property1));
    }

    @Test
    void testAndFilterWithPriceGuestAndCity() {
        PriceFilter priceFilter = new PriceFilter(150.0, 250.0, startDate, endDate);
        GuestFilter guestFilter = new GuestFilter(4);
        CityFilter cityFilter = new CityFilter("New York");
        AndFilter andFilter = new AndFilter(Arrays.asList(priceFilter, guestFilter, cityFilter));

        List<Property> filteredProperties = airbnb.searchProperties(Arrays.asList(andFilter));

        assertEquals(1, filteredProperties.size());
        assertTrue(filteredProperties.contains(property1));
    }
}
