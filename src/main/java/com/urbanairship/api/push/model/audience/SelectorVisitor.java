/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience;

public interface SelectorVisitor {
    void enter(Selector s);
    void exit(Selector s);
}
