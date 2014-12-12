/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

public final class LocationAlias {

    private final String aliasType;
    private final String aliasValue;

    public LocationAlias(String aliasType, String aliasValue) {
        this.aliasType = aliasType;
        this.aliasValue = aliasValue;
    }

    public String getAliasValue() {
        return aliasValue;
    }

    public String getAliasType() {
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

        LocationAlias alias = (LocationAlias) o;

        if (aliasType != null ? !aliasType.equals(alias.aliasType) : alias.aliasType != null) {
            return false;
        }
        if (aliasValue != null ? !aliasValue.equals(alias.aliasValue) : alias.aliasValue != null) {
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

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String aliasType;
        private String aliasValue;

        private Builder() { }

        public Builder setAliasValue(String aliasValue) {
            this.aliasValue = aliasValue;
            return this;
        }

        public Builder setAliasType(String aliasType) {
            this.aliasType = aliasType;
            return this;
        }

        public LocationAlias build() {
            Preconditions.checkArgument(StringUtils.isNotBlank(aliasType), "Alias type must be non empty string");
            Preconditions.checkArgument(StringUtils.isNotBlank(aliasValue), "Alias name must be non empty string");

            return new LocationAlias(aliasType, aliasValue);
        }
    }
}
