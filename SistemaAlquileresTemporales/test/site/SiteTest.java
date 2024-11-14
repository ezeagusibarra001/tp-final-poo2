package site;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import booking.Booking;
import comment.Comment;
import comment.CommentManager;
import property.PropertiesManager;
import property.Property;
import property.search.Filter;
import ranking.Ranking;
import ranking.RankingManager;
import ranking.RankingStrategy;
import stats.Stats;
import user.*;

class SiteTest {

    private Site site;
    private PropertiesManager propertiesManager;
    private RankingStrategy rankingStrategy;
    private CommentManager commentManager;
    private User user;
    private Owner owner;
    private Tenant tenant;
    private Property property;
    private Booking booking;
 
    @BeforeEach
    void setUp() {
        propertiesManager = mock(PropertiesManager.class);
        rankingStrategy = mock(RankingStrategy.class);
        RankingManager rankingManager = new RankingManager(rankingStrategy);
        Set<Category> categoriesTenant = new HashSet<>();
        Set<Category> categoriesOwner = new HashSet<>();
        Set<Category> categoriesProperty = new HashSet<>();
        SiteRegister siteRegister = new SiteRegister(categoriesTenant, categoriesOwner, categoriesProperty);
        site = new Site("Test Site", siteRegister, propertiesManager, rankingManager);

        user = mock(User.class);
        owner = mock(Owner.class);
        tenant = mock(Tenant.class);
        property = mock(Property.class);
        booking = mock(Booking.class);
    }

    @Test
    void testRegisterUser() {
        LocalDate date = LocalDate.now();
        site.getSiteRegister().registerUser(user, date);
        assertTrue(site.getSiteRegister().isRegistered(user), "User should be registered with an initial SiteStats entry");
    }

    @Test
    void testPostProperty() {
        site.postProperty(property, owner);
        verify(propertiesManager).post(property, owner);
    }

    @Test
    void testSearchProperties() {
        Filter filter = mock(Filter.class);
        site.searchProperties(Arrays.asList(filter));
        verify(propertiesManager).search(Arrays.asList(filter));
    }

    @Test 
    void testRequestBookingWhenPropertyIsAvailable() {
        Date checkInDate = new Date();
        Date checkOutDate = new Date(checkInDate.getTime() + (1000 * 60 * 60 * 24));

        when(property.isAvailable()).thenReturn(true);
        when(property.getOwner()).thenReturn(owner);

        site.requestBooking(tenant, property, checkInDate, checkOutDate);

        assertEquals(1, site.getBookings().size(), "A new booking should be added when the property is available");
    }

    @Test
    void testRequestBookingWhenPropertyIsNotAvailable() {
        Date checkInDate = new Date();
        Date checkOutDate = new Date(checkInDate.getTime() + (1000 * 60 * 60 * 24));

        when(property.isAvailable()).thenReturn(false);

        site.requestBooking(tenant, property, checkInDate, checkOutDate);

        assertEquals(0, site.getBookings().size(), "No booking should be added when the property is not available");
    }

    @Test
    void testMakeCheckoutWithValidRankings() {
        Ranking tenantRanking = mock(Ranking.class);
        Ranking ownerRanking = mock(Ranking.class);
        Ranking propertyRanking = mock(Ranking.class);
        Stats ownerStats = mock(Stats.class);
        Stats tenantStats = mock(Stats.class);
        Stats propertyStats = mock(Stats.class);

        when(booking.getTenant()).thenReturn(tenant);
        when(booking.getOwner()).thenReturn(owner);
        when(booking.getProperty()).thenReturn(property);
        when(tenant.getRanking()).thenReturn(tenantRanking);
        when(owner.getRanking()).thenReturn(ownerRanking);
        when(property.getRanking()).thenReturn(propertyRanking);
        when(owner.getStats()).thenReturn(ownerStats);
        when(tenant.getStats()).thenReturn(tenantStats);
        when(property.getStats()).thenReturn(propertyStats);

        List<Ranking> rankings = Arrays.asList(tenantRanking, ownerRanking, propertyRanking);

        site.makeCheckout(booking, rankings); 

        verify(booking).makeCheckout(rankings);
        verify(rankingStrategy).calculateAvgPerCategory(tenantRanking);
        verify(rankingStrategy).calculateAvgPerCategory(ownerRanking);
        verify(rankingStrategy).calculateAvgPerCategory(propertyRanking);
    }



    @Test
    void testMakeCheckoutWithInvalidRankings() {
        Ranking tenantRanking = mock(Ranking.class);
        Ranking ownerRanking = mock(Ranking.class);

        List<Ranking> rankings = Arrays.asList(tenantRanking, ownerRanking);

        assertThrows(IllegalArgumentException.class, () -> site.makeCheckout(booking, rankings),
                "An exception should be thrown if the number of rankings does not match RankingType values");
    } 

    @Test
    void testGetCommentManager() {
        CommentManager result = site.getCommentManager();
        assertNotNull(result, "CommentManager should not be null");
    }

    @Test
    void testGetName() {
        assertEquals("Test Site", site.getName(), "Site name should match the initialized value");
    }

    @Test
    void testGetRankingManager() {
        assertNotNull(site.getRankingManager(), "RankingManager should not be null");
    }

    @Test
    void testRegisterUserIntegration() {
        site.registerUser(user);
        assertTrue(site.getSiteRegister().isRegistered(user), "User should be registered in the Site");
    }

    @Test
    void testSetBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(mock(Booking.class));
        site.setBookings(bookings);

        assertEquals(1, site.getBookings().size(), "Bookings size should match the list passed to setBookings");
    }

    @Test
    void testGetPropertiesManager() {
        assertEquals(propertiesManager, site.getPropertiesManager(), "PropertiesManager should match the mock initialized in setup");
    }

}
