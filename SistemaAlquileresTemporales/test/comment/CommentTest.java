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
        // Mocking a property to associate with the comment
        property = mock(Property.class);
        comment = new Comment("John Doe", "Great stay!", property);
    }

    @Test
    void testGetAuthor() {
        // Verifica que el autor sea el esperado
        assertEquals("John Doe", comment.getAuthor(), "El autor debería ser 'John Doe'");
    }

    @Test
    void testGetContent() {
        // Verifica que el contenido sea el esperado
        assertEquals("Great stay!", comment.getContent(), "El contenido debería ser 'Great stay!'");
    }

    @Test
    void testGetDate() {
        // Verifica que la fecha sea la actual (fecha de creación del comentario)
        assertEquals(LocalDate.now(), comment.getDate(), "La fecha debería ser la de hoy");
    }

    @Test
    void testGetProperty() {
        // Verifica que la propiedad asociada sea la correcta
        assertEquals(property, comment.getProperty(), "La propiedad debería coincidir con el mock asociado");
    }

    @Test
    void testShowComment() {
        // Captura la salida en consola de showComment()
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        comment.showComment();

        String expectedOutput = "Autor: John Doe" + System.lineSeparator() +
                                "Propiedad: " + property + System.lineSeparator() +
                                "Fecha: " + LocalDate.now() + System.lineSeparator() +
                                "Great stay!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString(), "La salida de showComment() debería coincidir con el formato esperado");

        // Restaura la salida estándar
        System.setOut(System.out);
    }
}
