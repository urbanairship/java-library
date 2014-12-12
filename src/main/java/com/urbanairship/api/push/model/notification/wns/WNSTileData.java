/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.wns;

import com.google.common.collect.ImmutableList;
import static com.google.common.base.Preconditions.checkArgument;

public class WNSTileData {
    private final ImmutableList<WNSBinding> bindings;

    private WNSTileData(ImmutableList<WNSBinding> bindings)
    {
        this.bindings = bindings;
    }

    public int getBindingCount() {
        return bindings.size();
    }

    public WNSBinding getBinding(int i) {
        return bindings.get(i);
    }

    public ImmutableList<WNSBinding> getBindings() {
        return bindings;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WNSTileData that = (WNSTileData)o;
        if (bindings != null ? !bindings.equals(that.bindings) : that.bindings != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return (bindings != null ? bindings.hashCode() : 0);
    }

    public static class Builder {

        private final ImmutableList.Builder<WNSBinding> bindingsBuilder = ImmutableList.builder();

        private Builder() { }

        public Builder addBinding(WNSBinding value) {
            bindingsBuilder.add(value);
            return this;
        }

        public Builder addAllBindings(Iterable<WNSBinding> values) {
            bindingsBuilder.addAll(values);
            return this;
        }

        public WNSTileData build() {
            // TODO: must have at least one binding
            return new WNSTileData(bindingsBuilder.build());
        }
    }
}
