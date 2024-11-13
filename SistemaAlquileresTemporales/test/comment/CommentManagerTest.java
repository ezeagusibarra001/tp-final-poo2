package comment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
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
}
