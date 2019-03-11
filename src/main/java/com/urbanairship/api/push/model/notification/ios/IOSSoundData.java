package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import com.google.common.base.Optional;

import java.util.Objects;

public final class IOSSoundData extends PushModelObject {
    private final Optional<Boolean> critical;
    private final Optional<Double> volume;
    private final String name;

    public IOSSoundData(Optional<Boolean> critical, Optional<Double> volume, String name) {
        this.critical = critical;
        this.volume = volume;
        this.name = name;
    }

    public static Builder newBuilder() { return new Builder(); }

    public Optional<Boolean> getCritical() {
        return critical;
    }

    public Optional<Double> getVolume() {
        return volume;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOSSoundData that = (IOSSoundData) o;
        return Objects.equals(critical, that.critical) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(critical, volume, name);
    }

    @Override
    public String toString() {
        return "IOSSoundData{" +
                "critical=" + critical +
                ", volume=" + volume +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
       private Boolean critical = null;
       private Double volume = null;
       private String name = null;

       public Builder() {}

        public Builder setCritical(Boolean critical) {
            this.critical = critical;
            return this;
        }

        public Builder setVolume(Double volume) {
            this.volume = volume;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public IOSSoundData build() {
            Preconditions.checkNotNull(name);
            return new IOSSoundData(Optional.fromNullable(critical), Optional.fromNullable(volume), name);
        }
    }
}
