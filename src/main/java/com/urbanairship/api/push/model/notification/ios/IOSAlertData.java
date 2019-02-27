/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.push.model.PushModelObject;
import org.checkerframework.checker.nullness.Opt;

import java.util.List;
import java.util.Objects;

public final class IOSAlertData extends PushModelObject {

    private final Optional<String> body;
    private final Optional<String> actionLocKey;
    private final Optional<String> locKey;
    private final Optional<List<String>> locArgs;
    private final Optional<String> launchImage;
    private final Optional<String> summary_arg;
    private final Optional<Integer> summary_arg_count;

    private IOSAlertData(Optional<String> body,
                         Optional<String> actionLocKey,
                         Optional<String> locKey,
                         Optional<List<String>> locArgs,
                         Optional<String> launchImage,
                         Optional<String> summary_arg,
                         Optional<Integer> summary_arg_count) {
        this.body = body;
        this.actionLocKey = actionLocKey;
        this.locKey = locKey;
        this.locArgs = locArgs;
        this.launchImage = launchImage;
        this.summary_arg = summary_arg;
        this.summary_arg_count = summary_arg_count;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isCompound() {
        return actionLocKey.isPresent()
            || locKey.isPresent()
            || locArgs.isPresent()
            || launchImage.isPresent();
    }

    public Optional<String> getBody() {
        return body;
    }

    public Optional<String> getActionLocKey() {
        return actionLocKey;
    }

    public Optional<String> getLocKey() {
        return locKey;
    }

    public Optional<List<String>> getLocArgs() {
        return locArgs;
    }

    public Optional<String> getLaunchImage() {
        return launchImage;
    }

    public Optional<String> getSummaryArg() {
        return summary_arg;
    }

    public Optional<Integer> getSummaryArgCount() {
        return summary_arg_count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOSAlertData that = (IOSAlertData) o;
        return Objects.equals(body, that.body) &&
                Objects.equals(actionLocKey, that.actionLocKey) &&
                Objects.equals(locKey, that.locKey) &&
                Objects.equals(locArgs, that.locArgs) &&
                Objects.equals(launchImage, that.launchImage) &&
                Objects.equals(summary_arg, that.summary_arg) &&
                Objects.equals(summary_arg_count, that.summary_arg_count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, actionLocKey, locKey, locArgs, launchImage, summary_arg, summary_arg_count);
    }

    @Override
    public String toString() {
        return "IOSAlertData{" +
                "body=" + body +
                ", actionLocKey=" + actionLocKey +
                ", locKey=" + locKey +
                ", locArgs=" + locArgs +
                ", launchImage=" + launchImage +
                ", summary_arg=" + summary_arg +
                ", summary_arg_count=" + summary_arg_count +
                '}';
    }

    public static class Builder {
        private String body = null;
        private String actionLocKey = null;
        private String locKey = null;
        private List<String> locArgs = null;
        private String launchImage = null;
        private String summary_arg = null;
        private Integer summary_arg_count = null;

        private Builder() { }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setActionLocKey(String value) {
            this.actionLocKey = value;
            return this;
        }

        public Builder setLocKey(String value) {
            this.locKey = value;
            return this;
        }

        public Builder setLocArgs(List<String> value) {
            this.locArgs = value;
            return this;
        }

        public Builder setLaunchImage(String value) {
            this.launchImage = value;
            return this;
        }

        public Builder setSummaryArg(String value) {
            this.summary_arg = value;
            return this;
        }

        public Builder setSummaryArgCount(Integer value) {
            this.summary_arg_count = value;
            return this;
        }

        public IOSAlertData build() {
            return new IOSAlertData(Optional.fromNullable(body),
                                    Optional.fromNullable(actionLocKey),
                                    Optional.fromNullable(locKey),
                                    Optional.fromNullable(locArgs),
                                    Optional.fromNullable(launchImage),
                                    Optional.fromNullable(summary_arg),
                                    Optional.fromNullable(summary_arg_count));
        }
    }
}
