package com.urbanairship.api.tag.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public final class AddRemoveSet {

    private final Optional<ImmutableSet<String>> add;
    private final Optional<ImmutableSet<String>> remove;

    public static Builder newBuilder() {
        return new Builder();
    }

    private AddRemoveSet(Optional<ImmutableSet<String>> add,
                         Optional<ImmutableSet<String>> remove) {
        this.add = add;
        this.remove = remove;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddRemoveSet that = (AddRemoveSet) o;

        if (add != null ? !add.equals(that.add) : that.add != null) return false;
        if (remove != null ? !remove.equals(that.remove) : that.remove != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = add != null ? add.hashCode() : 0;
        result = 31 * result + (remove != null ? remove.hashCode() : 0);
        return result;
    }


    public static class Builder {
        private ImmutableSet.Builder<String> add = null;
        private ImmutableSet.Builder<String> remove = null;

        private Builder() { }

        public Builder add(String value) {
            if (add == null)
                add = ImmutableSet.builder();
            this.add.add(value);
            return this;
        }

        public Builder remove(String value) {
            if (remove == null)
                remove = ImmutableSet.builder();
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
