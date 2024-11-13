package site;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import user.User;

class SiteStatsTest {

    private User user;
    private LocalDate registerDate;
    private SiteStats siteStats;

    @BeforeEach
    void setUp() {
        user = Mockito.mock(User.class);
        registerDate = LocalDate.now();
        siteStats = new SiteStats(user, registerDate);
    }

    @Test
    void testSiteStatsInitialization() {
        assertNotNull(siteStats, "SiteStats should be initialized successfully with a User and LocalDate");
    }
}
