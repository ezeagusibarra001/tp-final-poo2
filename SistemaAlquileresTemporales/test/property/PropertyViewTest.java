package property;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import comment.Comment;
import comment.CommentManager;
import user.Owner;

class PropertyViewTest {

    private PropertyView propertyView;
    private Property property;
    private CommentManager commentManager;
    private Owner owner;

    @BeforeEach
    void setUp() {
        property = mock(Property.class);
        commentManager = mock(CommentManager.class);
        owner = mock(Owner.class);

        when(property.getOwner()).thenReturn(owner);

        propertyView = new PropertyView(property, commentManager);
    }

    @Test
    void testShowDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        List<Comment> comments = Arrays.asList(mock(Comment.class));
        when(commentManager.filterComments(property)).thenReturn(comments);
        when(owner.getRentalCount(property)).thenReturn(3);

        propertyView.showDetails();

        verify(property).showDetails();
        verify(commentManager).showComments(comments);
        verify(owner).showDetails();

        String expectedOutput = "Cantidad de veces que alquilo el inmueble: 3" + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput), "The rental count details should be correctly formatted and printed");

        System.setOut(System.out); // Restore original System.out
    }
}
