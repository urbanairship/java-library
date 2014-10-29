/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.collect.ImmutableMap;

/**
 * Every pre-defined action has a 'short name' and 'long name' on the
 * mobile device. This class enables name lookup, based on the particular action's
 * class.
 * <p/>
 * The mapping of actions to short & long names is maintained in the Push API v3 spec.
 */
public final class ActionNameRegistry {

    public static final ActionNameRegistry INSTANCE = new ActionNameRegistry();

    private final ImmutableMap<ActionType, String> shortNames = ImmutableMap.<ActionType, String>builder()
            .put(ActionType.ADD_TAG, "^+t")
            .put(ActionType.REMOVE_TAG, "^-t")
            .put(ActionType.OPEN_EXTERNAL_URL, "^u")
            .put(ActionType.OPEN_LANDING_PAGE_WITH_CONTENT, "^p")
            .put(ActionType.OPEN_DEEP_LINK, "^d")
            .put(ActionType.SHARE, "^s")
            .build();

    private final ImmutableMap<ActionType, String> longNames = ImmutableMap.<ActionType, String>builder()
            .put(ActionType.ADD_TAG, "add_tags_action")
            .put(ActionType.REMOVE_TAG, "remove_tags_action")
            .put(ActionType.OPEN_EXTERNAL_URL, "open_external_url_action")
            .put(ActionType.OPEN_LANDING_PAGE_WITH_CONTENT, "landing_page_action")
            .put(ActionType.OPEN_DEEP_LINK, "deep_link_action")
            .put(ActionType.SHARE, "share_action")
            .build();

    private final ImmutableMap<ActionType, String> fieldNames = ImmutableMap.<ActionType, String>builder()
            .put(ActionType.ADD_TAG, "add_tag")
            .put(ActionType.REMOVE_TAG, "remove_tag")
            .put(ActionType.OPEN_EXTERNAL_URL, "open")
            .put(ActionType.OPEN_LANDING_PAGE_WITH_CONTENT, "open")
            .put(ActionType.OPEN_DEEP_LINK, "open")
            .put(ActionType.APP_DEFINED, "app_defined")
            .put(ActionType.SHARE, "share")
            .build();

    private ActionNameRegistry() {
    }

    public String getShortName(ActionType actionType) {
        if (shortNames.containsKey(actionType)) {
            return shortNames.get(actionType);
        }

        throw new RuntimeException("Can't find a short name for action type: " + actionType.name());
    }

    public String getLongName(ActionType actionType) {
        if (longNames.containsKey(actionType)) {
            return longNames.get(actionType);
        }

        throw new RuntimeException("Can't find a long name for action type: " + actionType.name());
    }

    public String getFieldName(ActionType actionType) {
        if (fieldNames.containsKey(actionType)) {
            return fieldNames.get(actionType);
        }

        throw new RuntimeException("Can't find a field name for action type: " + actionType.name());
    }
}
