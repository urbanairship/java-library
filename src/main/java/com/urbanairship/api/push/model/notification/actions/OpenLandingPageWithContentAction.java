/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

public class OpenLandingPageWithContentAction extends PushModelObject implements Action.OpenAction<LandingPageContent> {
    private final LandingPageContent pageContent;

    public OpenLandingPageWithContentAction(LandingPageContent pageContent) {
        Preconditions.checkNotNull(pageContent, "pageContent should not be null.");
        this.pageContent = pageContent;
    }

    @Override
    public LandingPageContent getValue() {
        return pageContent;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pageContent);
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
                '}';
    }

}
