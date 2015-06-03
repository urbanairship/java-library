/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.wns;

import com.google.common.base.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public class WNSToastData {
    public enum Duration {
        LONG,
        SHORT;

        public String getIdentifier() {
            return name().toLowerCase();
        }

        public static Duration get(String value) {
            for (Duration d : values()) {
                if (value.equalsIgnoreCase(d.name())) {
                    return d;
                }
            }
            return null;
        }
    }

    private final WNSBinding binding;
    private final Optional<Duration> duration;
    private final Optional<WNSAudioData> audio;

    private WNSToastData(WNSBinding binding,
                         Optional<Duration> duration,
                         Optional<WNSAudioData> audio)
    {
        this.binding = binding;
        this.duration = duration;
        this.audio = audio;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public WNSBinding getBinding() {
        return binding;
    }

    public Optional<Duration> getDuration() {
        return duration;
    }

    public Optional<WNSAudioData> getAudio() {
        return audio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WNSToastData that = (WNSToastData)o;
        return (binding == null ? that.binding == null : binding.equals(that.binding)) &&
            (duration == null ? that.duration == null : duration.equals(that.duration)) &&
            (audio == null ? that.audio == null : audio.equals(that.audio));
    }

    @Override
    public int hashCode() {
        int result = (binding != null ? binding.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (audio != null ? audio.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private WNSBinding binding;
        private Duration duration;
        private WNSAudioData audio;

        private Builder() { }

        public Builder setBinding(WNSBinding value) {
            this.binding = value;
            return this;
        }

        public Builder setDuration(Duration value) {
            this.duration = value;
            return this;
        }

        public Builder setAudio(WNSAudioData value) {
            this.audio = value;
            return this;
        }

        public WNSToastData build() {
            checkArgument(binding != null, "toast must have a value for 'binding'");
            return new WNSToastData(binding,
                                    Optional.fromNullable(duration),
                                    Optional.fromNullable(audio));
        }
    }
}
