package com.urbanairship.api.push.model.audience;

/**
 * Selector expressions that have an array of one or more selector
 * expressions as children.
 */
public interface CompoundSelector extends Selector {
    Iterable<Selector> getChildren();
}
