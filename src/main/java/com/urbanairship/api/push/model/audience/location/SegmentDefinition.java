
/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience.location;

import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.audience.Selector;
import com.google.common.base.Preconditions;

/**
 * Model for the JSON definition of a segment as returned by the
 * segments-store service. We only care about the criteria.
 */
public final class SegmentDefinition extends PushModelObject {
    private final String displayName;
    private final Selector criteria;

    private SegmentDefinition(String displayName, Selector criteria) {
        this.displayName = displayName;
        this.criteria = criteria;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Selector getCriteria() {
        return criteria;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String displayName;
        private Selector criteria;

        private Builder() { }

        public Builder setDisplayName(String value) {
            displayName = value;
            return this;
        }

        public Builder setCriteria(Selector value) {
            criteria = value;
            return this;
        }

        public SegmentDefinition build() {
            Preconditions.checkNotNull(displayName);
            Preconditions.checkNotNull(criteria);
            return new SegmentDefinition(displayName, criteria);
        }
    }
}
