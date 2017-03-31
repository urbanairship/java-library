/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

public class OpenLandingPageWithContentAction extends PushModelObject implements Action.OpenAction<LandingPageContent> {
    private final LandingPageContent pageContent;

    private final Optional<String> fallbackURL;

    public OpenLandingPageWithContentAction(LandingPageContent pageContent) {
        this(pageContent, Optional.<String>absent());
    }

    public OpenLandingPageWithContentAction(LandingPageContent pageContent, String fallbackURL) {
        this(pageContent, Optional.fromNullable(fallbackURL));
    }

    private OpenLandingPageWithContentAction(LandingPageContent pageContent, Optional<String> fallbackURL){
        Preconditions.checkNotNull(pageContent, "pageContent should not be null.");
        Preconditions.checkNotNull(fallbackURL, "fallbackURL should not be null.");

        this.pageContent = pageContent;
        this.fallbackURL = fallbackURL;
    }

    @Override
    public LandingPageContent getValue() {
        return pageContent;
    }

    public Optional<String> getFallbackURL() {
        return fallbackURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OpenLandingPageWithContentAction that = (OpenLandingPageWithContentAction) o;

        return Objects.equal(pageContent, that.pageContent) &&
                Objects.equal(fallbackURL, that.fallbackURL);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pageContent, fallbackURL);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.OPEN_LANDING_PAGE_WITH_CONTENT;
    }

    @Override
    public String toString() {
        return "OpenLandingPageWithContentAction{" +
                "pageContent=" + pageContent +
                ", fallbackURL=" + fallbackURL +
                '}';
    }
}
