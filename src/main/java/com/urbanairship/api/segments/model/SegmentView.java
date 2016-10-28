package com.urbanairship.api.segments.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.audience.Selector;

public class SegmentView {
    private final Selector criteria;
    private final String displayName;

    private SegmentView(Selector criteria, String displayName) {
        this.criteria = criteria;
        this.displayName = displayName;
    }

    /**
     * New SegmentView builder.
     *
     * @return builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the criteria.
     *
     * @return Selector
     */
    public Selector getCriteria() {
        return criteria;
    }

    /**
     * Get the display name.
     *
     * @return String
     */
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "SegmentView{" +
                "criteria=" + criteria +
                "display_name=" + displayName +
                "}";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(criteria, displayName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SegmentView other = (SegmentView) obj;

        return Objects.equal(this.displayName, other.displayName) &&
                Objects.equal(this.criteria, other.criteria);
    }

    public final static class Builder {
        private String displayName = null;
        private Selector criteria = null;

        private Builder() {
        }

        /**
         * Set the display name
         *
         * @param displayName String
         * @return Builder
         */
        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /**
         * Set the criteria
         *
         * @param criteria Selector
         * @return Builder
         */
        public Builder setCriteria(Selector criteria) {
            this.criteria = criteria;
            return this;
        }

        public SegmentView build() {
            Preconditions.checkNotNull(criteria);
            Preconditions.checkNotNull(displayName);
            return new SegmentView(criteria, displayName);
        }
    }
}
