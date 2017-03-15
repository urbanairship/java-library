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
    private final Optional<String> faillbackUrl;

    public OpenLandingPageWithContentAction(LandingPageContent pageContent) {
        this(pageContent, Optional.<String>absent());
    }

    public OpenLandingPageWithContentAction(LandingPageContent pageContent, Optional<String> fallbackUrl){
        Preconditions.checkNotNull(pageContent, "pageContent should not be null.");
        Preconditions.checkNotNull(fallbackUrl, "fallbackUrl should not be null.");

        this.pageContent = pageContent;
        this.faillbackUrl = fallbackUrl;
    }

    @Override
    public LandingPageContent getValue() {
        return pageContent;
    }

    @Override
    public int hashCode() {
        int result = (pageContent != null ? pageContent.hashCode() : 0);
        result = 31 * result + (faillbackUrl != null ? faillbackUrl.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final OpenLandingPageWithContentAction other = (OpenLandingPageWithContentAction) obj;

        if(!faillbackUrl.equals(other.faillbackUrl)) return false;

        return Objects.equal(this.pageContent, other.pageContent);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.OPEN_LANDING_PAGE_WITH_CONTENT;
    }

    @Override
    public String toString() {
        return "OpenLandingPageWithContentAction{" +
                "data=" + pageContent +
                ", fallbackUrl=" + faillbackUrl +
                '}';
    }
}
