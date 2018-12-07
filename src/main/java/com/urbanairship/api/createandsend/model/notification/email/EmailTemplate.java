package com.urbanairship.api.createandsend.model.notification.email;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

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

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<String> getTemplateId() {
        return templateId;
    }


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

        public Builder addVariableDetail(VariableDetail variableDetail) {
            variableDetails.add(variableDetail);
            return this;
        }

        public Builder setEmailFields(EmailFields emailFields) {
            this.emailFields = emailFields;
            return this;
        }

        public Builder setTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public EmailTemplate build() {
            Preconditions.checkArgument((templateId != null && emailFields != null), "the template id or emailFields value must be set, not both.");
            return new EmailTemplate(this);
        }
    }
}
