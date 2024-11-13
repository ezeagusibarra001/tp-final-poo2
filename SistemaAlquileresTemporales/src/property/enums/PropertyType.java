package property.enums;

public enum PropertyType {
    HOUSE("Casa"),
    APARTMENT("Apartamento"),
    ROOM("Habitación"),
    QUINCHO("Quincho");

    private final String description;

    PropertyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}