/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.ios;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.List;

/**
 * Represents a iOS Push notification payload
 */
public final class IOSAlertData extends PushModelObject {

    private final Optional<String> body;
    private final Optional<String> actionLocKey;
    private final Optional<String> locKey;
    private final Optional<List<String>> locArgs;
    private final Optional<String> launchImage;

    private IOSAlertData(Optional<String> body,
                         Optional<String> actionLocKey,
                         Optional<String> locKey,
                         Optional<List<String>> locArgs,
                         Optional<String> launchImage) {
        this.body = body;
        this.actionLocKey = actionLocKey;
        this.locKey = locKey;
        this.locArgs = locArgs;
        this.launchImage = launchImage;
    }

    /**
     * IOSAlertData Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Returns true if object represents a dictionary of values rather than a
     * string, corresponds to the two possible "alert" values accepted by APNS
     * See Apple documentation for more details.
     * @return Boolean
     */
    public boolean isCompound() {
        return actionLocKey.isPresent()
            || locKey.isPresent()
            || locArgs.isPresent()
            || launchImage.isPresent();
    }

    /**
     * Get the text of the "body" (alert) message if this is a compound payload
     * and the alert value is set.
     * @return Alert message
     */
    public Optional<String> getBody() {
        return body;
    }

    /**
     * Get the "action-loc-key" of a compound payload if set. The action-loc-key
     * is used for localization of iOS message alerts, see Apple documentation
     * for details
     * @return the action-loc-key
     */
    public Optional<String> getActionLocKey() {
        return actionLocKey;
    }

    /**
     * Get the "loc-key" of a compound payload if set. The loc-key is a key for
     * an alert string in Localizable.strings. See Apple documentation for
     * details
     * @return the loc-key
     */
    public Optional<String> getLocKey() {
        return locKey;
    }

    /**
     * Get the "loc-args" of a compound payload if set. The loc-args are
     * arguments used in the loc-key. See Apple documentation for details.
     * @return loc-args
     */
    public Optional<List<String>> getLocArgs() {
        return locArgs;
    }

    /**
     * Get the "launch-image" filename of a compound payload if set. The
     * launch image file name should match an image file in the app bundle. See
     * Apple documentation for details.
     * @return launch-image
     */
    public Optional<String> getLaunchImage() {
        return launchImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IOSAlertData that = (IOSAlertData)o;
        if (body != null ? !body.equals(that.body) : that.body != null) {
            return false;
        }
        if (actionLocKey != null ? !actionLocKey.equals(that.actionLocKey) : that.actionLocKey != null) {
            return false;
        }
        if (locKey != null ? !locKey.equals(that.locKey) : that.locKey != null) {
            return false;
        }
        if (locArgs != null ? !locArgs.equals(that.locArgs) : that.locArgs != null) {
            return false;
        }
        if (launchImage != null ? !launchImage.equals(that.launchImage) : that.launchImage != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (body != null ? body.hashCode() : 0);
        result = 31 * result + (actionLocKey != null ? actionLocKey.hashCode() : 0);
        result = 31 * result + (locKey != null ? locKey.hashCode() : 0);
        result = 31 * result + (locArgs != null ? locArgs.hashCode() : 0);
        result = 31 * result + (launchImage != null ? launchImage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IOSAlertData{" +
                "body=" + body +
                ", actionLocKey=" + actionLocKey +
                ", locKey=" + locKey +
                ", locArgs=" + locArgs +
                ", launchImage=" + launchImage +
                '}';
    }

    /**
     * Build IOSAlertDAta objects
     */
    public static class Builder {
        private String body;
        private String actionLocKey;
        private String locKey;
        private List<String> locArgs;
        private String launchImage;

        private Builder() { }

        /**
         * Set the body. This translates to the "alert" string of the message,
         * and is associated with the "body" key. See Apple documentation for
         * details.
         * @param body Alert message
         * @return Builder
         */
        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        /**
         * Set the "action-loc-key". This controls button behavior for
         * iOS messages, see Apple documentation for behavior.
         * @param value value for key
         * @return Builder
         */
        public Builder setActionLocKey(String value) {
            this.actionLocKey = value;
            return this;
        }

        /**
         * Set the "loc-key". This is a key for an alert string in Localizable.strings
         * See Apple documentation for details
         * @param value key value
         * @return Builder
         */
        public Builder setLocKey(String value) {
            this.locKey = value;
            return this;
        }

        /**
         * Set the "loc-args". These are arguments used to populate values
         * in the "loc-key". See Apple documentation for details.
         * @param value args
         * @return Builder
         */
        public Builder setLocArgs(List<String> value) {
            this.locArgs = value;
            return this;
        }

        /**
         * Set the file name for a launch image on the device.
         * See Apple documentation for details.
         * @param value file name
         * @return Builder
         */
        public Builder setLaunchImage(String value) {
            this.launchImage = value;
            return this;
        }

        /**
         * Build IOSAlertData
         * @return IOSAlertData
         */
        public IOSAlertData build() {
            return new IOSAlertData(Optional.fromNullable(body),
                                    Optional.fromNullable(actionLocKey),
                                    Optional.fromNullable(locKey),
                                    Optional.fromNullable(locArgs),
                                    Optional.fromNullable(launchImage));
        }
    }
}
