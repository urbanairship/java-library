package com.urbanairship.api.push.model;

public final class PushOptions extends PushModelObject {

    public static Builder newBuilder() {
        return new Builder();
    }

    private PushOptions() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PushOptions that = (PushOptions) o;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "PushOptions{" +
                '}';
    }

    public static class Builder {

        private Builder() { }

        public PushOptions build() {
            return new PushOptions();
        }

    }
}
