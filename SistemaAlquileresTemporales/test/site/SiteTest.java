package site;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import booking.Booking;
import booking.BookingManager;
import comment.Comment;
import comment.CommentManager;
import notification.EventType;
import notification.NotificationManager;
import property.PropertiesManager;
import property.Property;
import property.search.Filter;
import ranking.Ranking;
import ranking.RankingManager;
import ranking.RankingStrategy;
import stats.Stats;
import user.*;

import notification.EventListener;

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
    void testGetPropertiesManager() {
        assertEquals(propertiesManager, site.getPropertiesManager(), "PropertiesManager should match the mock initialized in setup");
    }
    
    @Test
    void testAddComment() {
        Comment comment = mock(Comment.class);
        CommentManager commentManagerSpy = spy(new CommentManager());
        site = spy(site);
        doReturn(commentManagerSpy).when(site).getCommentManager();
        
        site.addComment(comment);
        
        verify(commentManagerSpy).addComment(comment);
    }


    @Test
    void testSubscribeToEvent() {
        NotificationManager notificationManagerSpy = spy(new NotificationManager());
        site = spy(site);
        doReturn(notificationManagerSpy).when(site).getNotificationManager();
        
        EventType eventType = EventType.PROPERTY_CANCELLATION;
        EventListener listener = mock(EventListener.class);
        
        site.subscribeToEvent(eventType, property, listener);
        
        verify(notificationManagerSpy).subscribe(eventType, property, listener);
    }


    @Test
    void testUnsubscribeFromEvent() {
        NotificationManager notificationManagerSpy = spy(new NotificationManager());
        site = spy(site);
        doReturn(notificationManagerSpy).when(site).getNotificationManager();
        
        EventType eventType = EventType.PROPERTY_CANCELLATION;
        EventListener listener = mock(EventListener.class);
        
        site.unsubscribeFromEvent(eventType, property, (notification.EventListener) listener);
        
        verify(notificationManagerSpy).unsubscribe(eventType, property, (notification.EventListener) listener);
    }

    @Test
    void testNotifyEvent() {
        NotificationManager notificationManagerSpy = spy(new NotificationManager());
        site = spy(site);
        doReturn(notificationManagerSpy).when(site).getNotificationManager();
        
        EventType eventType = EventType.PROPERTY_CANCELLATION;
        
        site.notifyEvent(eventType, property);
        
        verify(notificationManagerSpy).notify(eventType, property);
    }

    @Test
    void testGetTopTenTenants() {
        BookingManager bookingManagerSpy = spy(new BookingManager());
        site = spy(site);
        doReturn(bookingManagerSpy).when(site).getBookingManager();
        
        site.getTopTenTenants();
        
        verify(bookingManagerSpy).getTopTenTenants(site.getSiteRegister().getTenants());
    }

    @Test
    void testGetAllAvailableProperties() {
        site.getAllAvailableProperties();
        verify(propertiesManager).getAllAvailableProperties();
    }

    @Test
    void testGetOccupancyRate() {
        site.getOccupancyRate();
        verify(propertiesManager).getOccupancyRate();
    }

}
