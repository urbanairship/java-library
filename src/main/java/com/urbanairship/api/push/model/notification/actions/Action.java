/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

/**
 * Notification actions.
 * @param <A> Action type
 */
public interface Action<A> {
    /**
     * Content/parameters for the action.
     * @return A
     */
    A getValue();

    /**
     * The specific type of action.
     * @return ActionType
     */
    ActionType getActionType();

    /**
     * A marker interface to distinguish "open" actions from vanilla
     * actions.
     * @param <A> OpenAction type
     */
    interface OpenAction<A> extends Action<A> { }
}
