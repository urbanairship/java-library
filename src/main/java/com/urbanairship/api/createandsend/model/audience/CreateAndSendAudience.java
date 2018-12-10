package com.urbanairship.api.createandsend.model.audience;

import com.urbanairship.api.createandsend.model.audience.email.EmailChannels;
import com.urbanairship.api.createandsend.model.audience.sms.SmsChannels;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Objects;
import java.util.Optional;

public class CreateAndSendAudience extends PushModelObject {
    private Optional<EmailChannels> emailChannels = Optional.empty();
    private Optional<SmsChannels> smsChannels = Optional.empty();

    /**
     * Create and send audience for EmailChannels
     * @param emailChannels EmailChannels
     */
    public CreateAndSendAudience(EmailChannels emailChannels) {
        this.emailChannels = Optional.ofNullable(emailChannels);
    }

    /**
     * Create and send audience for SmsChannels
     * @param smsChannels SmsChannels
     */
    public CreateAndSendAudience(SmsChannels smsChannels) {
        this.smsChannels = Optional.ofNullable(smsChannels);
    }

    /**
     * Get the audience email channels if present.
     * @return Optional EmailChannels
     */
    public Optional<EmailChannels> getEmailChannels() {
        return emailChannels;
    }

    /**
     * Get the audience sms channels if present.
     * @return Optional SmsChannels
     */
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
