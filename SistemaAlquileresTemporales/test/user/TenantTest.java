package user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import comment.Comment;
import property.Property;
import ranking.Ranking;
import site.Site;

class TenantTest {

    private Tenant tenant;
    private User otherUser;
    private Property property;
    private Ranking rankingUser;
    private Ranking rankingProperty;
    private Ranking propertyRanking;
    private Site site;

    @BeforeEach
    void setUp() {
        tenant = new Tenant("Jane Doe", "jane@example.com", 123456789);
        otherUser = mock(User.class);
        property = mock(Property.class);
        rankingUser = mock(Ranking.class);
        rankingProperty = mock(Ranking.class);
        site = mock(Site.class);

        // Mock the getRanking() method for otherUser and property
        when(otherUser.getRanking()).thenReturn(mock(Ranking.class));
        propertyRanking = mock(Ranking.class);
        when(property.getRanking()).thenReturn(propertyRanking);
    }

    @Test
    void testTenantInitialization() {
        assertEquals("Jane Doe", tenant.fullName, "Tenant full name should match");
        assertEquals("jane@example.com", tenant.email, "Tenant email should match");
        assertEquals(123456789, tenant.phone, "Tenant phone should match");
    }

    @Test
    void testGetRanking() {
        assertNotNull(tenant.getRanking(), "Tenant should have an initial ranking");
    }

    @Test
    void testRateAfterCheckout() {
        tenant.rateAfterCheckout(otherUser, property, rankingUser, rankingProperty);

        // Verify that addScores was called on otherUser's ranking with rankingUser
        verify(otherUser.getRanking()).addScores(rankingUser);
        
        // Verify that addScores was called on property’s ranking with rankingProperty
        verify(property.getRanking()).addScores(rankingProperty);
    }

    @Test
    void testAdditionalRatings() {
        tenant.additionalRatings(property, rankingUser);
        verify(property.getRanking()).addScores(rankingUser);
    }

    @Test
    void testRequestBooking() {
        Date checkInDate = new Date();
        Date checkOutDate = new Date(checkInDate.getTime() + (1000 * 60 * 60 * 24)); // One day later

        tenant.requestBooking(site, property, checkInDate, checkOutDate);
        verify(site).requestBooking(tenant, property, checkInDate, checkOutDate);
        // Optionally, if you want to confirm the property was added to propertiesVisited, uncomment the following line
        // assertTrue(tenant.getPropertiesVisited().contains(property), "Property should be added to propertiesVisited after booking");
    }

    @Test
    void testMakeCommentWhenPropertyIsVisited() {
        tenant.addProperty(property);

        String content = "Great stay!";
        tenant.makeComment(site, property, content);

        verify(site).addComment(any(Comment.class));
    }

    @Test
    void testMakeCommentWhenPropertyIsNotVisited() {
        String content = "Great stay!";
        tenant.makeComment(site, property, content);

        verify(site, never()).addComment(any(Comment.class));
    }
}
