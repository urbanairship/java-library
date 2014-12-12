/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience;

import com.google.common.collect.ImmutableList;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceTypeData;

public class BasicCompoundSelector implements CompoundSelector {

    private final SelectorType type;
    private final ImmutableList<Selector> children;

    private BasicCompoundSelector(SelectorType type, ImmutableList<Selector> children) {
        this.type = type;
        this.children = children;
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
    public Iterable<Selector> getChildren() {
        return this.children;
    }

    @Override
    public void accept(SelectorVisitor visitor) {
        visitor.enter(this);
        for (Selector child : children) {
            child.accept(visitor);
        }
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

        BasicCompoundSelector that = (BasicCompoundSelector)o;
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (children != null ? !children.equals(that.children) : that.children != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (type != null ? type.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BasicCompoundSelector{" +
            "type=" + type.getIdentifier() +
            ",children=" + children.toString() +
            '}';
    }

    public static class Builder {
        private SelectorType type;
        private final ImmutableList.Builder<Selector> selectorsBuilder = ImmutableList.builder();

        private Builder() { }

        public Builder setType(SelectorType value) {
            this.type = value;
            return this;
        }

        public Builder addSelector(Selector value) {
            this.selectorsBuilder.add(value);
            return this;
        }

        public Builder addAllSelectors(Iterable<Selector> values) {
            this.selectorsBuilder.addAll(values);
            return this;
        }

        public BasicCompoundSelector build() {
            ImmutableList<Selector> selectors = selectorsBuilder.build();
            Preconditions.checkArgument(!selectors.isEmpty(), "A compound selector must have at least one child.");
            if (type == SelectorType.NOT && selectors.size() > 1) {
                throw new IllegalArgumentException("A 'not' expression can have only one child.");
            }
            for (Selector child : selectors) {
                SelectorType childType = child.getType();
                Preconditions.checkArgument(childType.getCategory() != SelectorCategory.ATOMIC,
                                            "A compound selector cannot an atomic selector.");
            }

            return new BasicCompoundSelector(type, selectors);
        }
    }
}
