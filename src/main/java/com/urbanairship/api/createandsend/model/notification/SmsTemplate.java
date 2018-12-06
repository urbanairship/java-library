package com.urbanairship.api.createandsend.model.notification;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class SmsTemplate {
    private final Optional<SmsFields> smsFields;

    private SmsTemplate(Builder builder) {
        this.smsFields = Optional.fromNullable(builder.smsFields);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<SmsFields> getSmsFields() {
        return smsFields;
    }

    @Override
    public String toString() {
        return "SmsTemplate{" +
                "smsFields=" + smsFields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsTemplate that = (SmsTemplate) o;
        return Objects.equal(smsFields, that.smsFields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(smsFields);
    }

    public static class Builder {
        private SmsFields smsFields;

        public Builder setSmsFields(SmsFields smsFields) {
            this.smsFields = smsFields;
            return this;
        }

        public SmsTemplate build() {
            return new SmsTemplate(this);
        }
    }
}
