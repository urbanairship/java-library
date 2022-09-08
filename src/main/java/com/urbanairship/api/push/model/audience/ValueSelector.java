/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience;

import java.util.Map;
import java.util.Optional;

/**
 * A selector which has a single value associated with it. Most atomic
 * selector expressions fall into this category, e.g. <code>{ "tag" :
 * "san_francisco_giants" }</code>.
 */
public interface ValueSelector extends Selector {
    String getValue();
    Optional<Map<String, String>> getAttributes();
}
