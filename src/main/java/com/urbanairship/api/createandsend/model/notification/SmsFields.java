package com.urbanairship.api.createandsend.model.notification;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class SmsFields {
    private final Optional<String> alert;

    private SmsFields(Builder builder) {
        this.alert = Optional.fromNullable(builder.alert);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

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

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public SmsFields build() {
            return new SmsFields(this);
        }
    }
}
