package com.urbanairship.api.createandsend.model.notification.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;


/**
 * Represents the Email Template in the create and send notification.
 */
public class EmailTemplate {
    private final Optional<String> templateId;
    private final Optional<List<VariableDetail>> variableDetails;
    private final Optional<EmailFields> emailFields;

    private EmailTemplate(
    @JsonProperty("template_id") String templateId,
    @JsonProperty("variable_details") List<VariableDetail> variableDetails,
    @JsonProperty("fields") EmailFields emailFields
    ) {
        this.templateId = Optional.ofNullable(templateId);
        this.emailFields = Optional.ofNullable(emailFields);
        if (variableDetails == null){
            this.variableDetails = Optional.empty();
        }
        else if (!variableDetails.isEmpty()) {
            this.variableDetails = Optional.ofNullable(variableDetails);
        } else {
            this.variableDetails = Optional.empty();
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
        return emailFields;
    }

    @Override
    public String toString() {
        return "EmailTemplate{" +
                "templateId=" + templateId+
                ", fields=" + emailFields +
                ", variableDetails=" + variableDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailTemplate)) return false;
        EmailTemplate that = (EmailTemplate) o;
        return Objects.equals(getTemplateId(), that.getTemplateId()) &&
                Objects.equals(getFields(), that.getFields()) &&
                Objects.equals(getVariableDetails(), that.getVariableDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateId(), getFields(), getVariableDetails());
    }

    public static class Builder {
        private ImmutableList.Builder<VariableDetail> variableDetails = ImmutableList.builder();
        private String templateId = null;
        private EmailFields emailFields = null;

        /**
         * Set a list of the variables in your template and default values.
         * @param variableDetail VariableDetail
         * @return EmailTemplate Builder
         */
        public Builder addVariableDetail(VariableDetail variableDetail) {
            variableDetails.add(variableDetail);
            return this;
        }

        /**
         * Set the email fields. The template you want to construct for the message.
         * @param emailFields EmailFields
         * @return EmailTemplate Builder
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
            return new EmailTemplate(templateId, variableDetails.build(), emailFields);
        }
    }
}
