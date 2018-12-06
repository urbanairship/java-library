package com.urbanairship.api.createandsend.model.audience;

import com.urbanairship.api.push.model.PushModelObject;

import java.util.Objects;
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

    @Override
    public String toString() {
        return "CreateAndSendAudience{" +
                "emailChannels=" + emailChannels +
                ", smsChannels=" + smsChannels +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateAndSendAudience)) return false;
        CreateAndSendAudience that = (CreateAndSendAudience) o;
        return Objects.equals(getEmailChannels(), that.getEmailChannels()) &&
                Objects.equals(getSmsChannels(), that.getSmsChannels());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmailChannels(), getSmsChannels());
    }
}
