/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;


public final class CustomEventsDetailResponse {

    private final String name;
    private final String conversion;
    private final String location;
    private final Integer count;
    private final Float value;

    public CustomEventsDetailResponse( 
        @JsonProperty("name") String name,
        @JsonProperty("conversion") String conversion,
        @JsonProperty("location") String location,
        @JsonProperty("count") Integer count,
        @JsonProperty("value") Float value
        ) {
        this.name = name;
        this.conversion = conversion;
        this.location = location;
        this.count = count;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getConversion() {
        return conversion;
    }

    public String getLocation() {
        return location;
    }

    public Integer getCount() {
        return count;
    }

    public Float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CustomEventsDetailResponse{" +
                "name=" + name +
                ", conversion=" + conversion +
                ", location=" + location +
                ", count=" + count +
                ", value=" + value +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, conversion, location, count, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final CustomEventsDetailResponse other = (CustomEventsDetailResponse) obj;
        return Objects.equal(this.name, other.name)
                && Objects.equal(this.conversion, other.conversion)
                && Objects.equal(this.location, other.location)
                && Objects.equal(this.count, other.count)
                && Objects.equal(this.value, other.value);
    }
}
