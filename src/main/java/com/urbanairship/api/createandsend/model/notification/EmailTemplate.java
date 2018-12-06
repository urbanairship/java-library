package com.urbanairship.api.createandsend.model.notification;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class EmailTemplate {
    private final Optional<String> templateId;
    private final Optional<List<VariableDetail>> variableDetails;
    private final Optional<Fields> fields;

    private EmailTemplate(Builder builder) {
        this.templateId = Optional.fromNullable(builder.templateId);
        this.fields = Optional.fromNullable(builder.fields);
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

    public Optional<Fields> getFields() {
        return fields;
    }

    public static class Builder {
        private ImmutableList.Builder<VariableDetail> variableDetails = ImmutableList.builder();
        private String templateId = null;
        private Fields fields = null;

        public Builder addVariableDetail(VariableDetail variableDetail) {
            variableDetails.add(variableDetail);
            return this;
        }

        public Builder setFields(Fields fields) {
            this.fields = fields;
            return this;
        }

        public Builder setTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public EmailTemplate build() {
            Preconditions.checkArgument((templateId != null && fields != null), "the template id or fields value must be set, not both.");
            return new EmailTemplate(this);
        }
    }
}
