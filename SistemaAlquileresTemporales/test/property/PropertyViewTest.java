package property;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import comment.Comment;
import comment.CommentManager;
import user.Owner;

class PropertyViewTest {

    private PropertyView propertyView;
    private Property propertyMock;
    private CommentManager commentManagerMock;
    private Owner ownerMock;

    @BeforeEach
    void setUp() {
        propertyMock = mock(Property.class);
        commentManagerMock = mock(CommentManager.class);
        ownerMock = mock(Owner.class);

        when(propertyMock.getOwner()).thenReturn(ownerMock);

        propertyView = new PropertyView(propertyMock, commentManagerMock);
    }

    @Test
    void testShowDetails() {
        when(propertyMock.showDetails()).thenReturn("Detalles de propiedad:\nTipo: Departamento\n...");
        when(ownerMock.showDetails()).thenReturn("Detalles del propietario: John Doe");
        when(ownerMock.getRentalCount(propertyMock)).thenReturn(5);

        List<Comment> comments = Arrays.asList(mock(Comment.class));
        when(commentManagerMock.filterComments(propertyMock)).thenReturn(comments);
        when(commentManagerMock.showComments(comments)).thenReturn("Comentario: ¡Excelente lugar!");

        String expectedOutput = "Detalles de propiedad:\nTipo: Departamento\n...\n" +
                                "Comentarios:\n" +
                                "Comentario: ¡Excelente lugar!\n" +
                                "Detalles del propietario: John Doe\n" +
                                "Cantidad de veces que alquiló el inmueble: 5";

        assertEquals(expectedOutput, propertyView.showDetails(), "La salida de showDetails debe coincidir con el formato esperado");
    }
}
