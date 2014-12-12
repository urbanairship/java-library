/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.tag.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public final class AddRemoveSet {

    private final Optional<ImmutableSet<String>> add;
    private final Optional<ImmutableSet<String>> remove;

    private AddRemoveSet(Optional<ImmutableSet<String>> add,
                         Optional<ImmutableSet<String>> remove) {
        this.add = add;
        this.remove = remove;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<ImmutableSet<String>> getAdd() {
        return add;
    }

    public Optional<ImmutableSet<String>> getRemove() {
        return remove;
    }

    @Override
    public String toString() {
        return "AddRemoveSet{" +
                "add=" + add +
                ", remove=" + remove +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(add, remove);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AddRemoveSet other = (AddRemoveSet) obj;
        return Objects.equal(this.add, other.add) && Objects.equal(this.remove, other.remove);
    }


    public static class Builder {
        private ImmutableSet.Builder<String> add = null;
        private ImmutableSet.Builder<String> remove = null;

        private Builder() {
        }

        public Builder add(String value) {
            if (add == null) {
                add = ImmutableSet.builder();
            }
            this.add.add(value);
            return this;
        }

        public Builder remove(String value) {
            if (remove == null) {
                remove = ImmutableSet.builder();
            }
            remove.add(value);
            return this;
        }

        public AddRemoveSet build() {
            Preconditions.checkArgument(!(add == null && remove == null), "There must be something to add and/or remove");
            return new AddRemoveSet(add != null ? Optional.fromNullable(add.build()) : null,
                    remove != null ? Optional.fromNullable(remove.build()) : null);
        }
    }
}
