/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

public class OpenLandingPageWithContentAction extends PushModelObject implements Action.OpenAction<LandingPageContent> {
    private final LandingPageContent pageContent;

    private final Optional<String> fallbackUrl;

    public OpenLandingPageWithContentAction(LandingPageContent pageContent) {
        this(pageContent, Optional.<String>empty());
    }

    public OpenLandingPageWithContentAction(LandingPageContent pageContent, String fallbackUrl) {
        this(pageContent, Optional.ofNullable(fallbackUrl));
    }

    private OpenLandingPageWithContentAction(LandingPageContent pageContent, Optional<String> fallbackUrl){
        Preconditions.checkNotNull(pageContent, "pageContent should not be null.");
        Preconditions.checkNotNull(fallbackUrl, "fallbackUrl should not be null.");

        this.pageContent = pageContent;
        this.fallbackUrl = fallbackUrl;
    }

    @Override
    public LandingPageContent getValue() {
        return pageContent;
    }

    public Optional<String> getFallbackUrl() {
        return fallbackUrl;
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
                Objects.equal(fallbackUrl, that.fallbackUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pageContent, fallbackUrl);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.OPEN_LANDING_PAGE_WITH_CONTENT;
    }

    @Override
    public String toString() {
        return "OpenLandingPageWithContentAction{" +
                "pageContent=" + pageContent +
                ", fallbackUrl=" + fallbackUrl +
                '}';
    }
}
