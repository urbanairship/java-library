package com.urbanairship.api.createandsend.model.notification.sms;

import com.google.common.base.Objects;
import java.util.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;

/**
 * Represents a Sms Payload for create and send requests.
 */
public class SmsPayload extends PushModelObject implements DevicePayloadOverride {
    private final Optional<String> alert;
    private final Optional<PushExpiry> pushExpiry;
    private final Optional<SmsTemplate> smsTemplate;
    private final DeviceType deviceType = DeviceType.SMS;

    private SmsPayload(Builder builder) {
        this.alert = Optional.ofNullable(builder.alert);
        this.pushExpiry = Optional.ofNullable(builder.pushExpiry);
        this.smsTemplate = Optional.ofNullable(builder.smsTemplate);
    }

    /**
     * SmsPayload builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getDeviceType() {
        return deviceType;
    }

    /**
     * Get the sms alert.
     * @return Optional String
     */
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Get the push expiry.
     * @return Optional PushExpiry
     */
    public Optional<PushExpiry> getPushExpiry() {
        return pushExpiry;
    }

    /**
     * Get the sms template.
     * @return Optional SmsTemplate
     */
    public Optional<SmsTemplate> getSmsTemplate() {
        return smsTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsPayload that = (SmsPayload) o;
        return Objects.equal(alert, that.alert) &&
                Objects.equal(pushExpiry, that.pushExpiry) &&
                Objects.equal(smsTemplate, that.smsTemplate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, pushExpiry, smsTemplate);
    }

    @Override
    public String toString() {
        return "SmsPayload{" +
                "alert=" + alert +
                ", pushExpiry=" + pushExpiry +
                ", smsTemplate=" + smsTemplate +
                '}';
    }

    public static class Builder {
        private String alert = null;
        private PushExpiry pushExpiry = null;
        private SmsTemplate smsTemplate = null;

        /**
         * Set the sms alert. sms templates cannot be used with the payload alert.
         * @param alert String
         * @return SmsPayload Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Set the push expiry. Delivery expiration represented as PushExpiry.
         * @param pushExpiry PushExpiry
         * @return SmsPayload Builder
         */
        public Builder setPushExpiry(PushExpiry pushExpiry) {
            this.pushExpiry = pushExpiry;
            return this;
        }

        /**
         * Set the sms template. Provide the ID or inline fields for a template.
         * Using a template enables you to provide and populate variables in your notification.
         * SmsPayload alert cannot be set when using sms templates.
         * @param smsTemplate SmsTemplate
         * @return SmsPayload Builder
         */
        public Builder setSmsTemplate(SmsTemplate smsTemplate) {
            this.smsTemplate = smsTemplate;
            return this;
        }

        /**
         * Build an SmsPayload object. Will fail if any of the following preconditions are not met.
         * <pre>
         *     1. alert and template cannot both be set.
         * </pre>
         * @throws IllegalArgumentException if alert and template are both set.
         * @return SmsPayload
         */
        public SmsPayload build() {
            Preconditions.checkArgument(!(alert != null && smsTemplate != null), "both alert and sms template cannot be set.");
            return new SmsPayload(this);
        }
    }
}
