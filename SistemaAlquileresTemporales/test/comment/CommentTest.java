package comment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import property.Property;

class CommentTest {

    private Comment comment;
    private Property property;

    @BeforeEach
    void setUp() {
        // Mocking a property to associate with the comment
        property = mock(Property.class);
        comment = new Comment("John Doe", "Great stay!", property);
    }

    @Test
    void testCommentInitialization() {
        assertEquals(true, comment.showComment().contains("Autor: John Doe"));
        assertTrue(comment.showComment().contains("Propiedad: " + property));
        assertTrue(comment.showComment().contains("Fecha: " + LocalDate.now()));
        assertTrue(comment.showComment().contains("Great stay!"));
    }

    @Test
    void testShowComment() {
        String expectedOutput = "Autor: John Doe" +
                "\nPropiedad: " + property +
                "\nFecha: " + LocalDate.now() +
                "\nGreat stay!";
        assertEquals(expectedOutput, comment.showComment(), "The formatted comment output should match");
    }
}
