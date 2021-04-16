package com.urbanairship.api.push.model.notification.adm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.Optional;

@JsonDeserialize(builder = ADMTemplate.Builder.class)
public class ADMTemplate {
    private final Optional<String> templateId;
    private final Optional<ADMFields> admFields;

    private ADMTemplate(Builder builder) {
        this.templateId = Optional.ofNullable(builder.templateId);
        this.admFields = Optional.ofNullable(builder.admFields);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Set the template id.
     *
     * The ID of a template that you created in the Airship UI.
     *
     * @return Optional String templateId
     */
    @JsonProperty("template_id")
    public Optional<String> getTemplateId() {
        return templateId;
    }

    /**
     * Set the adm fields.
     *
     * Items in the field object are personalizable with handlebars.
     *
     * @return Optional ADMFields admFields
     */
    @JsonProperty("fields")
    public Optional<ADMFields> getAdmFields() {
        return admFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ADMTemplate that = (ADMTemplate) o;
        return Objects.equal(templateId, that.templateId) &&
                Objects.equal(admFields, that.admFields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(templateId, admFields);
    }

    @Override
    public String toString() {
        return "ADMTemplate{" +
                "templateId=" + templateId +
                ", admFields=" + admFields +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        String templateId = null;
        ADMFields admFields = null;

        /**
         * Set the template id.
         *
         * The ID of a template that you created in the Airship UI.
         *
         * @param templateId String
         * @return Builder
         */
        @JsonProperty("template_id")
        public Builder setTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        /**
         * Set the adm fields.
         *
         * Items in the field object are personalizable with handlebars.
         *
         * @param admFields ADMFields
         * @return Builder
         */
        public Builder setFields(ADMFields admFields) {
            this.admFields = admFields;
            return this;
        }

        public ADMTemplate build() {
            Preconditions.checkArgument(!(templateId != null && admFields != null), "templateID and fields cannot both be set.");

            return new ADMTemplate(this);
        }
    }
}
