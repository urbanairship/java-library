/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience.location;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

/**
 * API model object, which is read when a location expression in an
 * audience selector (or segment definition) uses an alias rather than
 * an id.
 */
public final class LocationAlias extends PushModelObject {

    private final String aliasType;
    private final String aliasValue;

    private LocationAlias(String aliasType, String aliasValue) {
        this.aliasType = aliasType;
        this.aliasValue = aliasValue;
    }

    /**
     * Get the location value.
     * @return alias
     */
    public String getValue() {
        return aliasValue;
    }

    /**
     * Get the location type. Alias types are predefined locations in the
     * API, such as "us_state", and "us_zip". See Urban Airship API documentation
     * for details.
     * @return alias type
     */
    public String getType() {
        return aliasType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationAlias that = (LocationAlias) o;

        if (aliasType != null ? !aliasType.equals(that.aliasType) : that.aliasType != null) {
            return false;
        }
        if (aliasValue != null ? !aliasValue.equals(that.aliasValue) : that.aliasValue != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = aliasType != null ? aliasType.hashCode() : 0;
        result = 31 * result + (aliasValue != null ? aliasValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationAlias{" +
                "aliasType='" + aliasType + '\'' +
                ", aliasValue='" + aliasValue + '\'' +
                '}';
    }

    /**
     * Create a new LocationAlias Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String aliasType;
        private String aliasValue;

        private Builder() { }

        /**
         * Set the value. The value of the location is related to the location
         * type. For example, a "us_zip" could have a value of 97239
         * @param aliasValue value appropriate for location type.
         * @return Builder
         */
        public Builder setValue(String aliasValue) {
            this.aliasValue = aliasValue;
            return this;
        }

        /**
         * Set the location type. Alias types are predefined locations in the
         * API, such as "us_state", and "us_zip". See Urban Airship API documentation
         * for details.
         * @return alias type
         */
        public Builder setType(String aliasType) {
            this.aliasType = aliasType;
            return this;
        }

        /**
         * Build LocationAlias
         * @return LocationAlias
         */
        public LocationAlias build() {
            Preconditions.checkArgument(StringUtils.isNotBlank(aliasType), "Alias type must be non empty string");
            Preconditions.checkArgument(StringUtils.isNotBlank(aliasValue), "Alias name must be non empty string");

            return new LocationAlias(aliasType, aliasValue);
        }
    }
}
