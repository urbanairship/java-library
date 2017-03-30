package com.urbanairship.api.customevents.model;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;
import org.joda.time.DateTime;

public class CustomEventPayload extends PushModelObject {
    private final DateTime occurred;
    private final CustomEventBody customEventBody;
    private final CustomEventUser customEventUser;

    private CustomEventPayload(Builder builder) {
        this.occurred = builder.occurred;
        this.customEventBody = builder.customEventBody;
        this.customEventUser = builder.customEventUser;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public DateTime getOccurred() {
        return occurred;
    }

    public CustomEventBody getCustomEventBody() {
        return customEventBody;
    }

    public CustomEventUser getCustomEventUser() {
        return customEventUser;
    }


    public static class Builder {
        private DateTime occurred = null;
        private CustomEventBody customEventBody = null;
        private CustomEventUser customEventUser = null;

        public Builder setOccurred(DateTime occurred) {
            this.occurred = occurred;
            return this;
        }

        public Builder setCustomEventBody(CustomEventBody customEventBody) {
            this.customEventBody = customEventBody;
            return this;
        }

        public Builder setCustomEventUser(CustomEventUser customEventUser) {
            this.customEventUser = customEventUser;
            return this;
        }

        public CustomEventPayload build() {
            Preconditions.checkNotNull(occurred, "'occurred' must not be null");
            Preconditions.checkNotNull(customEventBody, "'customEventBody' must not be null");
            Preconditions.checkNotNull(customEventUser, "'customEventUser' must not be null");

            return new CustomEventPayload(this);
        }
    }
}
