/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.wns;

import com.google.common.base.Optional;

public class WNSBadgeData {
    public enum Glyph {
        NONE,
        ACTIVITY,
        ALERT,
        AVAILABLE,
        AWAY,
        BUSY,
        NEW_MESSAGE,
        PAUSED,
        PLAYING,
        UNAVAILABLE,
        ERROR,
        ATTENTION;

        private final String id;

        Glyph() {
            id = name().toLowerCase().replace('_', '-');
        }

        public String getIdentifier() {
            return id;
        }

        public static Glyph get(String value) {
            for (Glyph glyph : values()) {
                if (value.equalsIgnoreCase(glyph.getIdentifier())) {
                    return glyph;
                }
            }
            return null;
        }

    }

    private final Optional<Integer> value;
    private final Optional<Glyph> glyph;

    private WNSBadgeData(Optional<Integer> value,
                         Optional<Glyph> glyph)
    {
        this.value = value;
        this.glyph = glyph;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<Integer> getValue() {
        return this.value;
    }

    public Optional<Glyph> getGlyph() {
        return this.glyph;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WNSBadgeData that = (WNSBadgeData)o;
        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }

        return !(glyph != null ? !glyph.equals(that.glyph) : that.glyph != null);
    }

    @Override
    public int hashCode() {
        int result = (value != null ? value.hashCode() : 0);
        result = 31 * result + (glyph != null ? glyph.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private Integer value;
        private Glyph glyph;

        private Builder() { }

        public Builder setValue(int value) {
            this.value = value;
            return this;
        }

        public Builder setGlyph(Glyph value) {
            this.glyph = value;
            return this;
        }

        public WNSBadgeData build() {
            if (glyph == null && value == null) {
                throw new IllegalArgumentException("Must supply one of 'value' or 'glyph'.");
            }
            if (glyph != null && value != null) {
                throw new IllegalArgumentException("Must supply one of 'value' or 'glyph', but not both.");
            }
            return new WNSBadgeData(Optional.fromNullable(value),
                                    Optional.fromNullable(glyph));
        }
    }
}
