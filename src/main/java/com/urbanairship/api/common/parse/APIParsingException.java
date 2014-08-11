package com.urbanairship.api.common.parse;

import com.urbanairship.api.common.APIException;
import com.google.common.base.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonStreamContext;
import javax.ws.rs.core.Response;

public class APIParsingException extends APIException {

    private static final Logger log = LogManager.getLogger(APIParsingException.class);

    private final Optional<String> path;
    private final Optional<JsonLocation> location;

    public APIParsingException(String message, String path, JsonLocation location) {
        super(message);
        this.path = Optional.fromNullable(path);
        this.location = Optional.fromNullable(location);
    }

    public APIParsingException(String message) {
        super(message);
        this.path = Optional.absent();
        this.location = Optional.absent();
    }

    public APIParsingException(String message, Throwable cause) {
        super(message, cause);
        this.path = Optional.absent();
        this.location = Optional.absent();
    }

    public APIParsingException(Throwable cause) {
        super(cause);
        this.path = Optional.absent();
        this.location = Optional.absent();
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.BAD_REQUEST;
    }

    public Optional<String> getPath() {
        return path;
    }

    public Optional<JsonLocation> getLocation() {
        return location;
    }

    public static APIParsingException raise(String msg, JsonParser parser) throws APIParsingException {
        String message = msg;
        try {
            message = String.format("%s; at line %d, col %d, '%s'",
                                    msg,
                                    parser.getCurrentLocation().getLineNr(),
                                    parser.getCurrentLocation().getColumnNr(),
                                    getPath(parser));
            log.debug(msg);
        } catch ( Exception ex ) {
            log.debug("Exception while formatting exception.", ex);
        }
        throw new APIParsingException(msg, getPath(parser), parser.getCurrentLocation());
    }

    private static String getPath(JsonParser parser) {
        StringBuffer sb = new StringBuffer();
        JsonStreamContext context = parser.getParsingContext();
        doGetPath(context, sb);
        return sb.toString();
    }

    private static void doGetPath(JsonStreamContext context, StringBuffer sb) {
        if (context.inRoot()) {
            return;
        } else {
            doGetPath(context.getParent(), sb);
            if (context.inObject()) {
                String name = context.getCurrentName();
                if (name != null) {
                    if (sb.length() > 0) {
                        sb.append('.');
                    }
                    sb.append(name);
                }
            } else if (context.inArray()) {
                sb.append('[')
                    .append(context.getCurrentIndex())
                    .append(']');
            }
        }
    }
}
