package com.urbanairship.api.push.parse.notification;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.Interactive;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Map;

public class InteractiveReader implements JsonObjectReader<Interactive> {
    private Optional<String> type = Optional.absent();
    private Optional<ImmutableMap<String, Actions>> buttonActions = Optional.absent();

    public final static int MAX_BUTTON_IDS = 5;

    private static String IN(String typeName) {
        return typeName;
    }

    private static ImmutableSet<String> BUTTONS(String button1) {
        return ImmutableSet.of(button1);
    }

    private static ImmutableSet<String> BUTTONS(String button1, String button2) {
        return ImmutableSet.of(button1, button2);
    }

    private final static ImmutableMap<String, ImmutableSet<String>> PREDEFINED =
        new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put(IN("ua_yes_no_foreground"), BUTTONS("yes", "no"))
            .put(IN("ua_yes_no_background"), BUTTONS("yes", "no"))
            .put(IN("ua_accept_decline_foreground"), BUTTONS("accept", "decline"))
            .put(IN("ua_accept_decline_background"), BUTTONS("accept", "decline"))
            .put(IN("ua_shop_now"), BUTTONS("shop_now"))
            .put(IN("ua_buy_now"), BUTTONS("buy_now"))
            .put(IN("ua_follow"), BUTTONS("follow"))
            .put(IN("ua_opt_in"), BUTTONS("opt_in"))
            .put(IN("ua_unfollow"), BUTTONS("unfollow"))
            .put(IN("ua_opt_out"), BUTTONS("opt_out"))
            .put(IN("ua_remind_me_later"), BUTTONS("remind"))
            .put(IN("ua_download"), BUTTONS("download"))
            .put(IN("ua_share"), BUTTONS("share"))
            .put(IN("ua_download_share"), BUTTONS("download", "share"))
            .put(IN("ua_remind_share"), BUTTONS("remind", "share"))
            .put(IN("ua_opt_in_share"), BUTTONS("opt_in", "share"))
            .put(IN("ua_opt_out_share"), BUTTONS("opt_out", "share"))
            .put(IN("ua_follow_share"), BUTTONS("follow", "share"))
            .put(IN("ua_unfollow_share"), BUTTONS("unfollow", "share"))
            .put(IN("ua_shop_now_share"), BUTTONS("shop_now", "share"))
            .put(IN("ua_buy_now_share"), BUTTONS("buy_now", "share"))
            .put(IN("ua_more_like_less_like"), BUTTONS("more_like", "less_like"))
            .put(IN("ua_like_dislike"), BUTTONS("like", "dislike"))
            .put(IN("ua_like"), BUTTONS("like"))
            .put(IN("ua_like_share"), BUTTONS("like", "share"))
            .put(IN("ua_icons_happy_sad"), BUTTONS("happy", "sad"))
            .put(IN("ua_icons_up_down"), BUTTONS("up", "down"))
            .build();

    public static boolean isPredefined(String type) {
        return PREDEFINED.containsKey(type);
    }

    public static ImmutableSet<String> getPredefinedButtonIDs(String type) {
        return PREDEFINED.get(type);
    }

    public void readType(JsonParser parser) throws IOException {
        String typeName = StringFieldDeserializer.INSTANCE.deserialize(parser, "type");
        type = Optional.of(typeName);
    }

    public void readButtonActions(JsonParser parser) throws IOException {
        Map<String, Actions> actionsMap = parser.readValueAs(new TypeReference<Map<String, Actions>>() {});
        ImmutableMap<String, Actions> actionsImmutableMap = actionsMap == null ? null : ImmutableMap.copyOf(actionsMap);
        buttonActions = Optional.fromNullable(actionsImmutableMap);
    }

    @Override
    public Interactive validateAndBuild() throws IOException {
        Interactive.Builder builder = Interactive.newBuilder();
        if (type.isPresent()) {
            if (type.get().startsWith("ua_") && !isPredefined(type.get())) {
                throw new APIParsingException(
                    String.format("Predefined interactive notification type '%s' not found", type.get()));
            }
            builder.setType(type.get());
        }
        if (buttonActions.isPresent()) {
            builder.setButtonActions(buttonActions.get());
        }
        if (type.isPresent() && buttonActions.isPresent()) {
            String realType = type.get();
            boolean isPredef = isPredefined(type.get());
            if (isPredef && !getPredefinedButtonIDs(realType).containsAll(buttonActions.get().keySet())) {
                throw new APIParsingException(
                    "button_actions keys do not match predefined button IDs for predefined interactive notification");
            } else if (!isPredef && buttonActions.get().size() > MAX_BUTTON_IDS) {
                throw new APIParsingException(
                    String.format("button_actions must specify no more than %d actions", MAX_BUTTON_IDS));
            }
        }
        Interactive interactive;
        try {
            interactive = builder.build();
        } catch (NullPointerException exc) {
            throw new APIParsingException(exc.getMessage());
        }
        return interactive;
    }
}
