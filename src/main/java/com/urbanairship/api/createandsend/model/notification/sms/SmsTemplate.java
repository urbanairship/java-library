package com.urbanairship.api.createandsend.model.notification.sms;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

/**
 * Represends the sms template in the create and send sms notification.
 */
public class SmsTemplate {
    private final Optional<SmsFields> smsFields;

    private SmsTemplate(Builder builder) {
        this.smsFields = Optional.fromNullable(builder.smsFields);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the template you want to construct for the message.
     * @return
     */
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

        /**
         * Set the template you want to construct for the message.
         * The template you want to construct for the message. Provide variables in the template in double curly brackets
         * —  {{variable_name}}. The variable name must be a case-sensitive match of a key in your create-and-send objects
         * or a key specified in variable_details of a template to be replaced as a part of the template.
         * @param smsFields SmsFields
         * @return SmsTemplate Builder
         */
        public Builder setSmsFields(SmsFields smsFields) {
            this.smsFields = smsFields;
            return this;
        }

        public SmsTemplate build() {
            return new SmsTemplate(this);
        }
    }
}
