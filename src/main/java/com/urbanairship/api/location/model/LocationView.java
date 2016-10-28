/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;

import java.util.List;

/**
 * Represents a single location object.
 */
public final class LocationView {

    private final String locationId;
    private final String locationType;
    private final JsonNode propertiesJsonNode;
    private final Optional<Point> centroid;
    private final Optional<BoundedBox> bounds;

    private LocationView(String locationId,
                        String locationType,
                        JsonNode propertiesJsonNode,
                        Optional<Point> centroid,
                        Optional<BoundedBox> bounds) {
        this.locationId = locationId;
        this.locationType = locationType;
        this.propertiesJsonNode = propertiesJsonNode;
        this.centroid = centroid;
        this.bounds = bounds;
    }

    /**
     * New LocationView builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the location ID.
     *
     * @return The location ID as a string.
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * Get the location type.
     *
     * @return The location type as a string.
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * Get the properties JSON string.
     *
     * @return The properties JSON as a string.
     */
    public String getPropertiesJsonString() {
        return propertiesJsonNode.toString();
    }

    /**
     * Get the properties JSON node.
     *
     * @return The properties as a JSON node.
     */
    public JsonNode getPropertiesJsonNode() {
        return propertiesJsonNode;
    }

    /**
     * Get the centroid.
     *
     * @return The centroid as an optional Point object.
     */
    public Optional<Point> getCentroid() {
        return centroid;
    }

    /**
     * Get the bounds.
     *
     * @return The bounds as an optional BoundedBox object.
     */
    public Optional<BoundedBox> getBounds() {
        return bounds;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId='" + locationId + '\'' +
                ", locationType='" + locationType + '\'' +
                ", propertiesJsonNode=" + propertiesJsonNode +
                ", centroid=" + centroid +
                ", bounds=" + bounds +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(locationId, locationType, propertiesJsonNode, centroid, bounds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LocationView other = (LocationView) obj;
        return Objects.equal(this.locationId, other.locationId) && Objects.equal(this.locationType, other.locationType) && Objects.equal(this.propertiesJsonNode, other.propertiesJsonNode) && Objects.equal(this.centroid, other.centroid) && Objects.equal(this.bounds, other.bounds);
    }

    public static class Builder {

        private String locationId = null;
        private String locationType = null;
        private JsonNode propertiesJsonNode = null;
        private Point centroid = null;
        private BoundedBox bounds = null;

        private Builder() {
        }

        /**
         * Set the location ID.
         *
         * @param locationId The location ID as a string.
         * @return Builder
         */
        public Builder setLocationId(String locationId) {
            this.locationId = locationId;
            return this;
        }

        /**
         * Set the location type.
         *
         * @param locationType The location type as a string.
         * @return Builder
         */
        public Builder setLocationType(String locationType) {
            this.locationType = locationType;
            return this;
        }

        /**
         * Set the properties node.
         *
         * @param propertiesJsonNode The properties as a JsonNode.
         * @return Builder
         */
        public Builder setPropertiesNode(JsonNode propertiesJsonNode) {
            this.propertiesJsonNode = propertiesJsonNode;
            return this;
        }

        /**
         * Set the centroid.
         *
         * @param centroid The centroid as a Point object.
         * @return Builder
         */
        public Builder setCentroid(Point centroid) {
            this.centroid = centroid;
            return this;
        }

        /**
         * Set the centroid.
         *
         * @param value The centroid as a list of Doubles.
         * @return Builder
         */
        public Builder setCentroid(List<Double> value) {
            Preconditions.checkArgument(value.size() == 2, "Need two values to set a point.");

            this.centroid = Point.newBuilder()
                    .setLatitude(value.get(0))
                    .setLongitude(value.get(1))
                    .build();
            return this;
        }

        /**
         * Set the bounds.
         *
         * @param bounds The bounds as a BoundedBox object.
         * @return Builder
         */
        public Builder setBounds(BoundedBox bounds) {
            this.bounds = bounds;
            return this;
        }

        /**
         * Set the bounds.
         *
         * @param value The bounds as a list of Doubles.
         * @return Builder
         */
        public Builder setBounds(List<Double> value) {
            Preconditions.checkArgument(value.size() == 4, "Need four values to set a bounding box.");
            Point one = Point.newBuilder()
                    .setLatitude(value.get(0))
                    .setLongitude(value.get(1))
                    .build();

            Point two = Point.newBuilder()
                    .setLatitude(value.get(2))
                    .setLongitude(value.get(3))
                    .build();

            this.bounds = BoundedBox.newBuilder()
                .setCornerOne(one)
                .setCornerTwo(two)
                .build();

            return this;
        }

        /**
         * Build the LocationView object. Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. locationId may not be blank.
         * 2. locationType may not be blank.
         * 3. propertiesJsonNode may not be null.
         * </pre>
         *
         * @return LocationView
         */
        public LocationView build() {
            Preconditions.checkArgument(StringUtils.isNotBlank(locationId), "Location id cannot be blank");
            Preconditions.checkArgument(StringUtils.isNotBlank(locationType), "Location type cannot be blank");
            Preconditions.checkArgument(!propertiesJsonNode.isNull(), "Properties jsonNode cannot be null");

            return new LocationView(
                    locationId,
                    locationType,
                    propertiesJsonNode,
                    Optional.fromNullable(centroid),
                    Optional.fromNullable(bounds)
            );
        }
    }
}