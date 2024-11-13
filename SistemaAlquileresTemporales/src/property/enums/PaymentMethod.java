package property.enums;

public enum PaymentMethod {
    CASH("Efectivo"),
    DEBIT_CARD("Tarjeta de Débito"),
    CREDIT_CARD("Tarjeta de Crédito");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
