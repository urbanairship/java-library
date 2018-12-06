package com.urbanairship.api.createandsend.model.audience;

import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

public class CreateAndSendAudience extends PushModelObject {
    private Optional<EmailChannels> emailChannels = Optional.empty();
    private Optional<SmsChannels> smsChannels = Optional.empty();

    public CreateAndSendAudience(EmailChannels emailChannels) {
        this.emailChannels = Optional.ofNullable(emailChannels);
    }

    public CreateAndSendAudience(SmsChannels smsChannels) {
        this.smsChannels = Optional.ofNullable(smsChannels);
    }

    public Optional<EmailChannels> getEmailChannels() {
        return emailChannels;
    }

    public Optional<SmsChannels> getSmsChannels() {
        return smsChannels;
    }
}
