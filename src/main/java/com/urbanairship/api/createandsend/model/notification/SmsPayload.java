package com.urbanairship.api.createandsend.model.notification;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;

public class SmsPayload extends PushModelObject implements DevicePayloadOverride {
    private final Optional<String> alert;
    private final Optional<PushExpiry> pushExpiry;
    private final Optional<SmsTemplate> smsTemplate;
    private final DeviceType deviceType = DeviceType.SMS;

    private SmsPayload(Builder builder) {
        this.alert = Optional.fromNullable(builder.alert);
        this.pushExpiry = Optional.fromNullable(builder.pushExpiry);
        this.smsTemplate = Optional.fromNullable(builder.smsTemplate);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Optional<String> getAlert() {
        return alert;
    }

    public Optional<PushExpiry> getPushExpiry() {
        return pushExpiry;
    }

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

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public Builder setPushExpiry(PushExpiry pushExpiry) {
            this.pushExpiry = pushExpiry;
            return this;
        }

        public Builder setSmsTemplate(SmsTemplate smsTemplate) {
            this.smsTemplate = smsTemplate;
            return this;
        }

        public SmsPayload build() {
            Preconditions.checkArgument(!(alert != null && smsTemplate != null), "both alert and sms template cannot be set.");
            return new SmsPayload(this);
        }
    }
}
