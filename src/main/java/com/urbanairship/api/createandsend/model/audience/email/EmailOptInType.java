package com.urbanairship.api.createandsend.model.audience.email;


import com.google.common.base.Optional;

public enum EmailOptInType {
    COMMERCIAL_OPTED_IN("ua_commercial_opted_in"),
    TRANSACTIONAL_OPTED_IN("ua_transactional_opted_in");

    private final String identifier;

    private EmailOptInType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<EmailOptInType> find (String identifier) {
        for (EmailOptInType optInType : values()) {
            if (optInType.getIdentifier().equals(identifier)) {
                return Optional.of(optInType);
            }
        }
        return Optional.absent();
    }
}
