package property.enums;

public enum Service {
	WATER("Agua"),
    WI_FI("Wi-Fi"),
    PRIVATE_BATHROOM("Baño privado"),
    SHARED_BATHROOM("Baño compartido"),
    AIR_CONDITIONING("Aire acondicionado"),
    GAS("Gas"),
    ELECTRICITY("Electricidad");

    private final String description;

    Service(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
