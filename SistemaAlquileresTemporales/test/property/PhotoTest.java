package property;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhotoTest {

    private Photo photo;

    @BeforeEach
    void setUp() {
        photo = new Photo("http://example.com/photo.jpg");
    }

    @Test
    void testGetUrl() {
        assertEquals("http://example.com/photo.jpg", photo.getUrl(), "URL should match the initialized value");
    }

    @Test
    void testSetUrl() {
        photo.setUrl("http://newurl.com/photo.jpg");
        assertEquals("http://newurl.com/photo.jpg", photo.getUrl(), "URL should be updated to the new value");
    }
}
