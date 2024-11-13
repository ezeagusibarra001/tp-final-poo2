package comment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import property.Property;

class CommentManagerTest {

    private CommentManager commentManager;
    private Property property1;
    private Property property2;
    private Comment comment1;
    private Comment comment2;
    private Comment comment3;

    @BeforeEach
    void setUp() {
        commentManager = new CommentManager();
        
        // Mock properties
        property1 = mock(Property.class);
        property2 = mock(Property.class);

        // Create comments with different properties
        comment1 = new Comment("John Doe", "Great stay!", property1);
        comment2 = new Comment("Jane Smith", "Amazing view!", property1);
        comment3 = new Comment("Tom Brown", "Cozy place!", property2);

        // Add comments to manager
        commentManager.addComment(comment1);
        commentManager.addComment(comment2);
        commentManager.addComment(comment3);
    }

    @Test
    void testAddComment() {
        List<Comment> property1Comments = commentManager.filterComments(property1);

        assertEquals(2, property1Comments.size(), "There should be 3 comments in total");
        assertTrue(property1Comments.contains(comment1));
        assertTrue(property1Comments.contains(comment2));
    }

    @Test
    void testFilterCommentsByProperty() {
        List<Comment> filteredComments = commentManager.filterComments(property1);

        assertEquals(2, filteredComments.size(), "There should be 2 comments for property1");
        assertTrue(filteredComments.contains(comment1));
        assertTrue(filteredComments.contains(comment2));
        assertFalse(filteredComments.contains(comment3));
    }

    @Test
    void testFilterCommentsWithNoMatch() {
        Property newProperty = mock(Property.class);
        List<Comment> filteredComments = commentManager.filterComments(newProperty);

        assertTrue(filteredComments.isEmpty(), "There should be no comments for a property with no comments");
    }

    @Test
    void testShowComments() {
        List<Comment> commentsToShow = new ArrayList<>();
        commentsToShow.add(comment1);
        commentsToShow.add(comment2);

        // Captura el resultado de showComments
        String output = commentManager.showComments(commentsToShow);

        // Verifica que la salida contiene las piezas clave de información
        assertTrue(output.contains("Autor: John Doe"), "Output should contain author 'John Doe'");
        assertTrue(output.contains("Autor: Jane Smith"), "Output should contain author 'Jane Smith'");
        assertTrue(output.contains("Great stay!"), "Output should contain the comment 'Great stay!'");
        assertTrue(output.contains("Amazing view!"), "Output should contain the comment 'Amazing view!'");
        assertTrue(output.contains("Fecha: " + LocalDate.now()), "Output should contain the current date");

        // Opcionalmente, verifica que la palabra "Propiedad" está incluida en el texto, sin verificar el mock exacto.
        assertTrue(output.contains("Propiedad:"), "Output should contain the word 'Propiedad:'");

        // Restaura System.out
        System.setOut(System.out);
    }


}
