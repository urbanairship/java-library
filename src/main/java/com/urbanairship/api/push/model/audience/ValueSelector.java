package com.urbanairship.api.push.model.audience;

import com.google.common.base.Optional;
import java.util.Map;

/**
 * A selector which has a single value associated with it. Most atomic
 * selector expressions fall into this category, e.g. <code>{ "tag" :
 * "san_francisco_giants" }</code>.
 */
public interface ValueSelector extends Selector {
    String getValue();
    Optional<Map<String, String>> getAttributes();
}
