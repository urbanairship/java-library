package com.urbanairship.api.push.model.notification.ios;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public final class IOSBadgeData extends PushModelObject {

    public enum Type {
        VALUE,
        AUTO,
        INCREMENT,
        DECREMENT;

        private String id;

        Type() {
            id = name().toLowerCase();
        }

        public String getIdentifier() {
            return id;
        }

        public static Type get(String value) {
            for (Type type : values()) {
                if (value.equalsIgnoreCase(type.getIdentifier())) {
                    return type;
                }
            }
            return null;
        }
    }

    private Optional<Integer> value;
    private Type type;

    private IOSBadgeData(Type type, Optional<Integer> value) {
        this.type = type;
        this.value = value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Type getType() {
        return type;
    }

    public Optional<Integer> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IOSBadgeData that = (IOSBadgeData)o;
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (type != null ? type.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IOSBadgeData{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }

    public static class Builder {
        private Type type;
        private Integer value;

        private Builder() { }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setValue(int value) {
            this.value = value;
            return this;
        }

        public IOSBadgeData build() {
            Preconditions.checkNotNull(type);
            return new IOSBadgeData(type, Optional.fromNullable(value));
        }
    }
}
