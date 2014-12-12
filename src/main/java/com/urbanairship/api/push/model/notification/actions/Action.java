/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

/**
 * Notification actions.
 * @param <A>
 */
public interface Action<A> {
    /**
     * Content/parameters for the action.
     * @return
     */
    A getValue();

    /**
     * The specific type of action.
     * @return
     */
    ActionType getActionType();

    /**
     * A marker interface to distinguish "open" actions from vanilla
     * actions.
     * @param <A>
     */
    public interface OpenAction<A> extends Action<A> { }
}