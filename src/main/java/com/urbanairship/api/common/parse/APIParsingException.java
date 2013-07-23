package com.urbanairship.api.common.parse;

import com.google.common.base.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonLocation;


public class APIParsingException extends RuntimeException {

    private static final Logger log = LogManager.getLogger("com.urbanairship.api");

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

    public Optional<String> getPath() {
        return path;
    }

    public Optional<JsonLocation> getLocation() {
        return location;
    }


//    private static String getPath(JsonParser parser) {
//        StringBuffer sb = new StringBuffer();
//        JsonStreamContext context = parser.getParsingContext();
//        doGetPath(context, sb);
//        return sb.toString();
//    }
//
//    private static void doGetPath(JsonStreamContext context, StringBuffer sb) {
//        if (context.inRoot()) {
//            return;
//        } else {
//            doGetPath(context.getParent(), sb);
//            if (context.inObject()) {
//                String name = context.getCurrentName();
//                if (name != null) {
//                    if (sb.length() > 0) {
//                        sb.append('.');
//                    }
//                    sb.append(name);
//                }
//            } else if (context.inArray()) {
//                sb.append('[')
//                    .append(context.getCurrentIndex())
//                    .append(']');
//            }
//        }
//    }


}
