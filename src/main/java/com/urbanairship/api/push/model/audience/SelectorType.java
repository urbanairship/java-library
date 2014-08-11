package com.urbanairship.api.push.model.audience;

public enum SelectorType {

    TAG("tag", SelectorCategory.VALUE),
    ALIAS("alias", SelectorCategory.VALUE),
    SEGMENT("segment", SelectorCategory.VALUE),

    DEVICE_TOKEN("device_token", SelectorCategory.VALUE, true),
    DEVICE_PIN("device_pin", SelectorCategory.VALUE, true),
    APID("apid", SelectorCategory.VALUE, true),
    WNS("wns", SelectorCategory.VALUE, true),
    MPNS("mpns", SelectorCategory.VALUE, true),
    ADM("adm", SelectorCategory.VALUE, true),

    AND("and", SelectorCategory.COMPOUND),
    OR("or", SelectorCategory.COMPOUND),
    NOT("not", SelectorCategory.COMPOUND),

    LOCATION("location", SelectorCategory.LOCATION),

    /**
     * @see http://www.rdio.com/artist/Descendents/album/Somery/track/All/
     */
    ALL("all", SelectorCategory.ATOMIC),
    TRIGGERED("triggered", SelectorCategory.ATOMIC);

    private final String identifier;
    private final SelectorCategory category;
    private final boolean isDeviceId;

    SelectorType(String identifier, SelectorCategory category) {
        this.identifier = identifier;
        this.category = category;
        this.isDeviceId = false;
    }

    SelectorType(String identifier, SelectorCategory category, boolean isDeviceId) {
        this.identifier = identifier;
        this.category = category;
        this.isDeviceId = isDeviceId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public SelectorCategory getCategory() {
        return category;
    }

    public boolean isDeviceId() {
        return isDeviceId;
    }

    public static SelectorType getSelectorTypeFromIdentifier(String identifier) {
        for (SelectorType operatorType : values()) {
            if (operatorType.identifier.equalsIgnoreCase(identifier)) {
                return operatorType;
            }
        }

        return null;
    }
}
