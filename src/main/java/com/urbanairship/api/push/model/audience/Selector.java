package com.urbanairship.api.push.model.audience;

/**
 * The root of all selector expressions.
 */
public interface Selector {
    SelectorType getType();
    void accept(SelectorVisitor visitor);
}
