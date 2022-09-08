package com.urbanairship.api.segments.model;

import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.push.model.audience.Selector;

import java.util.Objects;

public class SegmentView {
    private final Selector criteria;
    private final String displayName;
    private final String error;
    private final ErrorDetails errorDetails;

    private SegmentView(Selector criteria, String displayName, String error, ErrorDetails errorDetails) {
        this.criteria = criteria;
        this.displayName = displayName;
        this.error = error;
        this.errorDetails = errorDetails;
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

    public String getError() {
        return error;
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "SegmentView{" +
                "criteria=" + criteria +
                ", displayName='" + displayName + '\'' +
                ", error='" + error + '\'' +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentView that = (SegmentView) o;
        return Objects.equals(criteria, that.criteria) &&
                Objects.equals(displayName, that.displayName) &&
                Objects.equals(error, that.error) &&
                Objects.equals(errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criteria, displayName, error, errorDetails);
    }

    public final static class Builder {
        private String displayName = null;
        private Selector criteria = null;
        private String error = null;
        private ErrorDetails errorDetails = null;

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

        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        public SegmentView build() {
            return new SegmentView(criteria, displayName, error, errorDetails);
        }
    }
}
