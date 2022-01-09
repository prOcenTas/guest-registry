package eu.exadelpractice.registry.locations.entity;

public enum LocationType {
    OFFICE("OFFICE"), EVENT("EVENT");

    private final String type;

    LocationType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
