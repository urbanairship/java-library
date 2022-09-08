package com.urbanairship.api.createandsend.model.notification.sms;

import com.google.common.base.Objects;

import java.util.Optional;

/**
 * Represends the sms template in the create and send sms notification.
 */
public class SmsTemplate {
    private final Optional<SmsFields> smsFields;
    private final Optional<String> templateId;

    private SmsTemplate(Builder builder) {
        this.smsFields = Optional.ofNullable(builder.smsFields);
        this.templateId = Optional.ofNullable(builder.templateId);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the template you want to construct for the message.
     * @return Optional SmsFields
     */
    public Optional<SmsFields> getSmsFields() {
        return smsFields;
    }

    /**
     * Get the template ID.
     *
     * @return Optional String
     */
    public Optional<String> getTemplateId() {
        return templateId;
    }

    @Override
    public String toString() {
        return "SmsTemplate{" +
                "smsFields=" + smsFields +
                ", templateId=" + templateId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsTemplate that = (SmsTemplate) o;
        return Objects.equal(smsFields, that.smsFields) &&
                Objects.equal(templateId, that.templateId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(smsFields, templateId);
    }

    public static class Builder {
        private SmsFields smsFields;
        private String templateId;

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

        /**
         * Set the ID of a template that you created in the Airship UI that you want to use in a create-and-send payload.
         *
         * @param templateId String
         * @return SmsTemplateBuilder
         */
        public Builder setTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public SmsTemplate build() {
            return new SmsTemplate(this);
        }
    }
}
