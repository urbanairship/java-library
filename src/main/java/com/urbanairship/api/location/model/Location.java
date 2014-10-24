package com.urbanairship.api.location.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;

import java.util.List;

public final class Location {

    private final String locationId;
    private final String locationType;
    private final JsonNode propertiesJsonNode;
    private final Optional<Point> centroid;
    private final Optional<BoundedBox> bounds;

    public static Builder newBuilder() {
        return new Builder();
    }

    public Location(String locationId,
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

    public String getLocationId() {
        return locationId;
    }

    public String getLocationType() {
        return locationType;
    }

    public String getPropertiesJsonString() {
        return propertiesJsonNode.toString();
    }

    public JsonNode getPropertiesJsonNode() {
        return propertiesJsonNode;
    }

    public Optional<Point> getCentroid() {
        return centroid;
    }

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
        final Location other = (Location) obj;
        return Objects.equal(this.locationId, other.locationId) && Objects.equal(this.locationType, other.locationType) && Objects.equal(this.propertiesJsonNode, other.propertiesJsonNode) && Objects.equal(this.centroid, other.centroid) && Objects.equal(this.bounds, other.bounds);
    }

    public static class Builder {

        private String locationId = null;
        private String locationType = null;
        private JsonNode propertiesJsonNode = null;
        private Point centroid = null;
        private BoundedBox bounds = null;

        private Builder() { }

        public Builder setLocationId(String locationId) {
            this.locationId = locationId;
            return this;
        }

        public Builder setLocationType(String locationType) {
            this.locationType = locationType;
            return this;
        }

        public Builder setPropertiesNode(JsonNode propertiesJsonNode) {
            this.propertiesJsonNode = propertiesJsonNode;
            return this;
        }

        public Builder setCentroid(Point centroid) {
            this.centroid = centroid;
            return this;
        }

        public Builder setCentroid(List<Double> value) {
            Preconditions.checkArgument(value.size() == 2, "Need two values to set a point.");
            Point p = Point.newBuilder()
                    .setLatitude(value.get(0))
                    .setLongitude(value.get(1))
                    .build();

            this.centroid = p;
            return this;
        }

        public Builder setBounds(BoundedBox bounds) {
            this.bounds = bounds;
            return this;
        }

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

            this.bounds = new BoundedBox(one, two);
            return this;
        }

        public Location build() {
            Preconditions.checkArgument(StringUtils.isNotBlank(locationId), "Location id cannot be blank");
            Preconditions.checkArgument(StringUtils.isNotBlank(locationType), "Location type cannot be blank");
            Preconditions.checkArgument(!propertiesJsonNode.isNull(), "Properties jsonNode cannot be null");

            return new Location(
                    locationId,
                    locationType,
                    propertiesJsonNode,
                    Optional.fromNullable(centroid),
                    Optional.fromNullable(bounds)
            );
        }
    }
}