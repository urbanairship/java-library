/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushModelObject;

public class BasicSelector extends PushModelObject implements Selector  {

    private final SelectorType type;

    protected BasicSelector(SelectorType type) {
        this.type = type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public SelectorType getType() {
        return this.type;
    }

    @Override
    public DeviceTypeData getApplicableDeviceTypes() {
        return DeviceTypeData.all();
    }

    @Override
    public void accept(SelectorVisitor visitor) {
        visitor.enter(this);
        visitor.exit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicSelector that = (BasicSelector)o;
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return  (type != null ? type.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "BasicSelector{" +
            "type=" + type.getIdentifier() +
            '}';
    }

    public static class Builder {
        private SelectorType type;

        private Builder() { }

        public Builder setType(SelectorType value) {
            this.type = value;
            return this;
        }

        public BasicSelector build() {
            Preconditions.checkArgument(type == SelectorType.ALL || type == SelectorType.TRIGGERED,
                                        "A unitary selector can be one of 'all' or 'triggered' only.");
            return new BasicSelector(type);
        }
    }
}
