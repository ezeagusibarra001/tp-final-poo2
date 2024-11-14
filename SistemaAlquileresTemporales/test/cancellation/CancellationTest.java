package cancellation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CancellationTest {

    private FreeCancellationPolicy freePolicy;
    private IntermediateCancellationPolicy intermediatePolicy;
    private noCancellationPolicy noPolicy;

    @BeforeEach
    void setUp() {
        freePolicy = new FreeCancellationPolicy();
        intermediatePolicy = new IntermediateCancellationPolicy();
        noPolicy = new noCancellationPolicy();
    }

    @Test
    void testFreeCancellationPolicy() {
        double totalAmount = 1000;
        double dailyPrice = 100;

        assertEquals(totalAmount, freePolicy.calculateRefund(totalAmount, 15, dailyPrice),
                "Debe reembolsar el monto total si se cancela con más de 10 días de anticipación");

        assertEquals(totalAmount - 2 * dailyPrice, freePolicy.calculateRefund(totalAmount, 5, dailyPrice),
                "Debe aplicar una penalización de 2 días de precio si se cancela con menos de 10 días de anticipación");
    }

    @Test
    void testIntermediateCancellationPolicy() {
        double totalAmount = 1000;

        assertEquals(totalAmount, intermediatePolicy.calculateRefund(totalAmount, 25, 100),
                "Debe reembolsar el monto total si se cancela con más de 20 días de anticipación");

        assertEquals(totalAmount * 0.5, intermediatePolicy.calculateRefund(totalAmount, 15, 100),
                "Debe reembolsar el 50% si se cancela entre 10 y 20 días de anticipación");

        assertEquals(0, intermediatePolicy.calculateRefund(totalAmount, 5, 100),
                "No debe haber reembolso si se cancela con menos de 10 días de anticipación");
    }

    @Test
    void testNoCancellationPolicy() {
        double totalAmount = 1000;

        assertEquals(0, noPolicy.calculateRefund(totalAmount, 30, 100),
                "No debe haber reembolso en ninguna circunstancia");
    }
}
