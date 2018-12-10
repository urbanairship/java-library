package com.urbanairship.api.createandsend.model.notification.email;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Represents the Email Template in the create and send notification.
 */
public class EmailTemplate {
    private final Optional<String> templateId;
    private final Optional<List<VariableDetail>> variableDetails;
    private final Optional<EmailFields> fields;

    private EmailTemplate(Builder builder) {
        this.templateId = Optional.fromNullable(builder.templateId);
        this.fields = Optional.fromNullable(builder.emailFields);
        if (!builder.variableDetails.build().isEmpty()) {
            variableDetails = Optional.fromNullable(builder.variableDetails.build());
        } else {
            variableDetails = Optional.absent();
        }
    }

    /**
     * EmailTemplate Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the uuid of a template you want to use in a create-and-send payload.
     * @return Optional String
     */
    public Optional<String> getTemplateId() {
        return templateId;
    }

    /**
     * Get a list of the variables in your template and default values.
     * @return Optional List of VariableDetail
     */
    public Optional<List<VariableDetail>> getVariableDetails() {
        return variableDetails;
    }

    public Optional<EmailFields> getFields() {
        return fields;
    }

    public static class Builder {
        private ImmutableList.Builder<VariableDetail> variableDetails = ImmutableList.builder();
        private String templateId = null;
        private EmailFields emailFields = null;

        /**
         * Set a list of the variables in your template and default values.
         * @param variableDetail
         * @return
         */
        public Builder addVariableDetail(VariableDetail variableDetail) {
            variableDetails.add(variableDetail);
            return this;
        }

        /**
         * Set the email fields. The template you want to construct for the message.
         * @param emailFields
         * @return
         */
        public Builder setEmailFields(EmailFields emailFields) {
            this.emailFields = emailFields;
            return this;
        }

        /**
         * Set the uuid of a template you want to use in a create-and-send payload.
         * @param templateId String
         * @return EmailTemplate Builder
         */
        public Builder setTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public EmailTemplate build() {
            Preconditions.checkArgument(!(templateId != null && emailFields != null), "the template id or emailFields value must be set, not both.");
            Preconditions.checkArgument(!(templateId == null && emailFields == null), "The template id or email fields must be set.");
            return new EmailTemplate(this);
        }
    }
}
