package com.urbanairship.api.push.model.audience.location;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * An identifier for a location definition, as seen in either an API
 * audience selector expression, or in a segment definition. It can
 * contain either an id or an alias, but not both.
 */
public final class LocationIdentifier extends PushModelObject {

    private final Optional<String> id;
    private final Optional<LocationAlias> alias;

    private LocationIdentifier(Optional<String> id, Optional<LocationAlias> alias) {
        this.id = id;
        this.alias = alias;
    }

    /**
     * New LocationIdentifier Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Returns true if this LocationIdentifier is an alias
     * @return true if alias, false if id
     */
    public boolean isAlias() {
        return alias != null && alias.isPresent();
    }

    /**
     * Get the LocationAlias. This is optional
     * @return Optional<<T>LocationAlias</T>>
     */
    public Optional<LocationAlias> getAlias() {
        return alias;
    }

    /**
     * Get the id. This is optional.
     * @return Optional<<T>String</T>> id
     */
    public Optional<String> getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationIdentifier that = (LocationIdentifier) o;

        if (alias.isPresent() && that.getAlias().isPresent()){
            return (alias.hashCode() == that.getAlias().hashCode());
        }
        else if (id.isPresent() && that.getId().isPresent()){
            return (id.hashCode() == that.getId().hashCode());
        }
        else return false;
    }


    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationIdentifier{" +
                "alias=" + alias +
                ", id='" + id + '\'' +
                '}';
    }

    /**
     * LocationIdentifier Builder
     */
    public static class Builder {
        private String id;
        private LocationAlias alias;

        private Builder() { }

        /**
         * Set the id
         * @param value id value
         * @return Builder
         */
        public Builder setId(String value) {
            id = value;
            return this;
        }

        /**
         * Set the location alias.
         * @param value LocationAlias
         * @return Builder
         */
        public Builder setAlias(LocationAlias value) {
            alias = value;
            return this;
        }

        /**
         * Build a new LocationIdentifier
         * @return LocationIdentifier
         */
        public LocationIdentifier build() {
            Preconditions.checkArgument(id != null || alias != null, "Must have only one of 'id' or an alias");
            return new LocationIdentifier(Optional.fromNullable(id),
                                          Optional.fromNullable(alias));
        }
    }
}
