/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

import com.google.common.base.Preconditions;
import com.urbanairship.api.client.APIResponseModelObject;

public final class AudienceSegment extends APIResponseModelObject {

    private final String displayName;
    private final Operator rootOperator;
    private final Predicate rootPredicate;
    private final Long count;

    public static Builder newBuilder() {
        return new Builder();
    }

    private AudienceSegment(String displayName, Operator rootOperator, Predicate rootPredicate, Long count) {
        this.displayName = displayName;
        this.rootOperator = rootOperator;
        this.rootPredicate = rootPredicate;
        this.count = count;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isOperatorRoot() {
        return rootOperator != null;
    }

    public Operator getRootOperator() {
        return rootOperator;
    }

    public boolean isPredicateRoot() {
        return rootPredicate != null;
    }

    public Predicate getRootPredicate() {
        return rootPredicate;
    }

    public boolean hasCount() {
        return count != null;
    }

    public long getCount() {
        return count;
    }

    public Builder toBuilder() {
        return new Builder()
                .setDisplayName(displayName)
                .setRootOperator(rootOperator)
                .setCount(count)
                .setRootPredicate(rootPredicate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AudienceSegment that = (AudienceSegment) o;

        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        if (rootOperator != null ? !rootOperator.equals(that.rootOperator) : that.rootOperator != null) return false;
        if (rootPredicate != null ? !rootPredicate.equals(that.rootPredicate) : that.rootPredicate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = displayName != null ? displayName.hashCode() : 0;
        result = 31 * result + (rootOperator != null ? rootOperator.hashCode() : 0);
        result = 31 * result + (rootPredicate != null ? rootPredicate.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AudienceSegment{" +
                "displayName='" + displayName + '\'' +
                ", rootOperator=" + rootOperator +
                ", rootPredicate=" + rootPredicate +
                ", count=" + count +
                '}';
    }

    public static class Builder {

        private String displayName = null;
        private Operator rootOperator = null;
        private Predicate rootPredicate =  null;
        private Long count = null;

        private Builder() {}

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setRootOperator(Operator rootOperator) {
            this.rootOperator = rootOperator;
            return this;
        }

        public Builder setRootPredicate(Predicate rootPredicate) {
            this.rootPredicate = rootPredicate;
            return this;
        }

        public Builder setCount(Long count) {
            this.count = count;
            return this;
        }

        public AudienceSegment build() {
            Preconditions.checkNotNull(displayName);
            Preconditions.checkArgument(
                (rootOperator == null && rootPredicate != null) ||
                (rootOperator != null && rootPredicate == null),
                "Must specify either a root operator or a root predicate"
            );

            return new AudienceSegment(displayName, rootOperator, rootPredicate, count);
        }
    }
}
