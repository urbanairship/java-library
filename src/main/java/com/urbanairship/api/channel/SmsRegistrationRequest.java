package com.urbanairship.api.channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.SmsRegistrationResponse;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.DateFormats;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class SmsRegistrationRequest implements Request<SmsRegistrationResponse> {

    private final static String REGISTER_SMS_CHANNEL = "/api/channels/sms";
    private final static String OPT_OUT = "/api/channels/sms/opt-out";
    private final static String UNINSTALL = "/api/channels/sms/uninstall";

    private static final String SENDER_KEY = "sender";
    private static final String MSISDN_KEY = "msisdn";
    private static final String OPTED_IN_KEY = "opted_in";

    private final String path;
    private final Map<String, String> payload = new HashMap<String, String>();

    private SmsRegistrationRequest(String path, String sender, String msisdn) {
        this.path = path;
        payload.put(SENDER_KEY, sender);
        payload.put(MSISDN_KEY, msisdn);
    }

    private SmsRegistrationRequest(String path, String sender, String msisdn, DateTime optedIn) {
        this.path = path;
        payload.put(SENDER_KEY, sender);
        payload.put(MSISDN_KEY, msisdn);
        payload.put(OPTED_IN_KEY, DateFormats.DATE_FORMATTER.print(optedIn));
    }

    /**
     * This will mark an SMS channel as opted-out (inactive) and it will not receive alerts even when they are addressed in the future.
     *
     * @param sender The SMS sender ID provided by Urban Airship. Must be numeric characters only.
     * @param msisdn The mobile phone number you want to opt-out of SMS messages.
     * @return SmsRegistrationRequest
     */
    public static SmsRegistrationRequest newOptOutRequest(String sender, String msisdn) {
        return new SmsRegistrationRequest(OPT_OUT, sender, msisdn);
    }

    /**
     * Removes phone numbers and accompanying data from Urban Airship. Use with caution.
     * Uninstalling an SMS channel will prevent you from retrieving opt-in and opt-out history for the corresponding msisdn.
     * If the uninstalled msisdn opts-in again, it will generate a new channel_id.
     * The new channel_id cannot be reassociated with any opt-in information, tags, named users, insight reports,
     * or other information from the uninstalled SMS channel.
     *
     * @param sender A number the app is configured to send from, provided by Urban Airship.
     * @param msisdn The mobile phone number you want to register as an SMS channel (or send a request to opt-in).
     * @return SmsRegistrationRequest
     */
    public static SmsRegistrationRequest newUninstallRequest(String sender, String msisdn) {
        return new SmsRegistrationRequest(UNINSTALL, sender, msisdn);
    }

    /**
     * Begin the opt-in process for a new SMS channel.
     *
     * @param sender A number the app is configured to send from, provided by Urban Airship.
     * @param msisdn The mobile phone number you want to register as an SMS channel.
     * @return SmsRegistrationRequest
     */
    public static SmsRegistrationRequest newRegistrationRequest(String sender, String msisdn) {
        return new SmsRegistrationRequest(REGISTER_SMS_CHANNEL, sender, msisdn);
    }

    /**
     * Begin the opt-in process for a new SMS channel.
     *
     * @param sender A number the app is configured to send from, provided by Urban Airship.
     * @param msisdn The mobile phone number you want to register as an SMS channel.
     * @param optedIn The datetime that represents the date and time when explicit permission was received from the user to receive messages.
     * @return SmsRegistrationRequest
     */
    public static SmsRegistrationRequest newRegistrationRequest(String sender, String msisdn, DateTime optedIn) {
        return new SmsRegistrationRequest(REGISTER_SMS_CHANNEL, sender, msisdn, optedIn);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        Preconditions.checkArgument(!payload.isEmpty());
        Preconditions.checkArgument(payload.containsKey(SENDER_KEY) && payload.containsKey(MSISDN_KEY),
                "Sender and msisdn are required for opt-out, registration, and uninstall requests.");

        try {
            return ChannelObjectMapper.getInstance().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            return "{ \"exception\" : \"" + e.getClass().getName() + "\", \"message\" : \"" + e.getMessage() + "\" }";
        }
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<SmsRegistrationResponse> getResponseParser() {
        return new ResponseParser<SmsRegistrationResponse>() {
            @Override
            public SmsRegistrationResponse parse(String response) throws IOException {
                return ChannelObjectMapper.getInstance().readValue(response, SmsRegistrationResponse.class);
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return true;
    }
}