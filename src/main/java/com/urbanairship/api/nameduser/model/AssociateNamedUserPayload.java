/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser.model;

import com.google.common.base.Preconditions;
import com.urbanairship.api.channel.information.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import org.apache.commons.lang.StringUtils;

public final class AssociateNamedUserPayload extends PushModelObject {

    String channelId;
    DeviceType deviceType;
    String namedUserId;

    private AssociateNamedUserPayload(String channelId, DeviceType deviceType, String namedUserId) {
        this.channelId = channelId;
        this.deviceType = deviceType;
        this.namedUserId = namedUserId;
    }

    public String getChannelId() {
        return channelId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public String getNamedUserId() {
        return namedUserId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static class Builder {

        private String channelId;
        private DeviceType deviceType;
        private String namedUserId;

        private Builder() {
        }

        public Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder setDeviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        public Builder setNamedUserId(String namedUserId) {
            this.namedUserId = namedUserId;
            return this;
        }

        public AssociateNamedUserPayload build() {
            Preconditions.checkArgument(
                    StringUtils.isNotBlank(channelId) &&
                            deviceType != null &&
                            StringUtils.isNotBlank(namedUserId),
                    "Tag mutations must be set for at least one device identifier type");

            return new AssociateNamedUserPayload(channelId, deviceType, namedUserId);
        }
    }
}
