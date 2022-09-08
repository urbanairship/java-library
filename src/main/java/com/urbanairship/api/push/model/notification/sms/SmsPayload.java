package com.urbanairship.api.push.model.notification.sms;

import com.google.common.base.Objects;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;

import java.util.Optional;

/**
 * Represents the payload to be used for sending to sms.
 */
public class SmsPayload extends PushModelObject implements DevicePayloadOverride {
    private Optional<String> alert;
    private Optional<PushExpiry> expiry;

    private SmsPayload(Builder builder) {
        alert = Optional.ofNullable(builder.alert);
        expiry = Optional.ofNullable(builder.expiry);
    }

    /**
     * New SmsPayload Builder.
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the device type.
     * @return DeviceType.SMS
     */
    @Override
    public DeviceType getDeviceType() {
        return DeviceType.SMS;
    }

    /**
     * Get the sms alert.
     * @return Optional String alert
     */
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Get the sms expiry.
     * @return Optional PushExpiry expiry
     */
    public Optional<PushExpiry> getExpiry() {
        return expiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsPayload that = (SmsPayload) o;
        return Objects.equal(alert, that.alert) &&
                Objects.equal(expiry, that.expiry);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, expiry);
    }

    /**
     * SmsPayload Builder
     */
    public static class Builder {
        private String alert;
        private PushExpiry expiry;

        /**
         * Overrides the alert provided at the top level of the notification for SMS channels.
         * The maximum length of an SMS alert is 1600 characters.
         * @param alert String
         * @return SmsPayload builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Delivery expiration, as either absolute ISO UTC timestamp, or number of seconds from now.
         * @param expiry PushExpiry
         * @return SmsPayload builder
         */
        public Builder setExpiry(PushExpiry expiry) {
            this.expiry = expiry;
            return this;
        }

        public SmsPayload build() {
            return new SmsPayload(this);
        }
    }
}
