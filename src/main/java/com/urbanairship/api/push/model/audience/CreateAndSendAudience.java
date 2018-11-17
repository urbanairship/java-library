package com.urbanairship.api.push.model.audience;

import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.ArrayList;
import java.util.List;

public class CreateAndSendAudience extends PushModelObject {
    private List<RegisterEmailChannel> emailChannelList;

    private CreateAndSendAudience(Builder builder) {
        this.emailChannelList = builder.emailChannelList;
    }

    public static Builder newBuilder() {
        return new Builder();}

    /**
     * CreateAndSendAudience Builder.
     */
    public static class Builder {
        private List emailChannelList = new ArrayList<RegisterEmailChannel>();

        private Builder() {
        }

        public Builder setChannel(RegisterEmailChannel channel) {
            this.emailChannelList.add(channel);
            return this;
        }

        /**
         * Build the CreateAndSendAudience Object
         * @return CreateAndSendAudience
         */
        public CreateAndSendAudience build() {
            return new CreateAndSendAudience(this);
        }
    }
}
