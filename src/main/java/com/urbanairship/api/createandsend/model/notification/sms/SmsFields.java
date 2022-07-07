package com.urbanairship.api.createandsend.model.notification.sms;

import com.google.common.base.Objects;
import java.util.Optional;

/**
 * Represents the sms fields in the create and send sms notification.
 */
public class SmsFields {
    private final Optional<String> alert;

    private SmsFields(Builder builder) {
        this.alert = Optional.ofNullable(builder.alert);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the notification you want to send to an SMS audience.
     * @return Optional String
     */
    public Optional<String> getAlert() {
        return alert;
    }

    @Override
    public String toString() {
        return "SmsFields{" +
                "alert=" + alert +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsFields smsFields = (SmsFields) o;
        return Objects.equal(alert, smsFields.alert);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert);
    }

    public static class Builder {
        private String alert;

        /**
         * Set the notification you want to send to an SMS audience.
         * @param alert String
         * @return SmsFields Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public SmsFields build() {
            return new SmsFields(this);
        }
    }
}
