package comment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import property.Property;

class CommentTest {

    private Comment comment;
    private Property property;

    @BeforeEach
    void setUp() {
        property = mock(Property.class);
        comment = new Comment("John Doe", "Great stay!", property);
    }

    @Test
    void testGetAuthor() {
        assertEquals("John Doe", comment.getAuthor(), "El autor debería ser 'John Doe'");
    }

    @Test
    void testGetContent() {
        assertEquals("Great stay!", comment.getContent(), "El contenido debería ser 'Great stay!'");
    }

    @Test
    void testGetDate() {
        assertEquals(LocalDate.now(), comment.getDate(), "La fecha debería ser la de hoy");
    }

    @Test
    void testGetProperty() {
        assertEquals(property, comment.getProperty(), "La propiedad debería coincidir con el mock asociado");
    }

    @Test
    void testShowComment() {
        String output = comment.showComment();
        
        String expectedOutput = "Autor: John Doe\n" +
                                "Propiedad: " + property + "\n" +
                                "Fecha: " + LocalDate.now() + "\n" +
                                "Great stay!";

        assertEquals(expectedOutput, output, "La salida de showComment() debería coincidir con el formato esperado");
    } 


}
