/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience;

public interface SelectorVisitor {
    void enter(Selector s);
    void exit(Selector s);
}
