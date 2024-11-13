package user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import property.Property;
import ranking.Ranking;

class OwnerTest {

    private Owner owner;
    private User otherUser;
    private Property property;
    private Ranking rankingUser;
    private Ranking rankingProperty;
    private Ranking otherUserRanking;

    @BeforeEach
    void setUp() {
        owner = new Owner("John Doe", "john@example.com", 987654321);
        otherUser = mock(User.class);
        property = mock(Property.class);
        rankingUser = mock(Ranking.class);
        rankingProperty = mock(Ranking.class);
        
        // Mock the getRanking() method for otherUser
        otherUserRanking = mock(Ranking.class);
        when(otherUser.getRanking()).thenReturn(otherUserRanking);
    }

    @Test
    void testOwnerInitialization() {
        assertEquals("John Doe", owner.fullName, "Owner full name should match");
        assertEquals("john@example.com", owner.email, "Owner email should match");
        assertEquals(987654321, owner.phone, "Owner phone should match");
    }

    @Test
    void testGetRanking() {
        assertNotNull(owner.getRanking(), "Owner should have an initial ranking");
    }

    @Test
    void testRateAfterCheckout() {
        // Act
        owner.rateAfterCheckout(otherUser, property, rankingUser, rankingProperty);

        // Verify that addScores was called on otherUser's ranking with rankingUser
        verify(otherUserRanking).addScores(rankingUser);
        
        // Verify that additionalRatings method on owner called addScores on rankingProperty
        verify(rankingProperty, never()).addScores(any(Ranking.class));
    }
}
