package com.urbanairship.api.channel.model;

import com.urbanairship.api.channel.model.email.OptInLevel;

public class CreateAndSendChannel {

    private final Optional<String> uAEmailAddress;
    private final Optional<OptInLevel> uAEmailOptInLevel;
    private final Optional<>

    CreateAndSendChannel(Builder builder) {
        this.uAEmailAddress = uaEmailAddress;
        this.uAOptInLevel = uaOptInLevel;
    }

    public String getUaEmailAddress() {
        return uAEmailAddress;
    }

    public OptInLevel getUaOptInLevel() {
        return uAOptInLevel;
    }




}