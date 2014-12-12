/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

public final class Operator {

    private final OperatorType type;
    private final List<OperatorChild> children;

    private Operator(OperatorType type, List<OperatorChild> children) {
        this.type = type;
        this.children = children;
    }

    public static Builder newBuilder(OperatorType type) {
        return new Builder(type);
    }

    public OperatorType getType() {
        return type;
    }

    public List<OperatorChild> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Operator operator = (Operator) o;

        if (children != null ? !children.equals(operator.children) : operator.children != null) {
            return false;
        }
        if (type != operator.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "type=" + type +
                ", children=" + children +
                '}';
    }

    public static class Builder {

        private final OperatorType type;

        private final List<OperatorChild> children = Lists.newLinkedList();

        private Builder(OperatorType type) {
            this.type = type;
        }

        public Builder addPredicate(Predicate predicate) {
            children.add(new OperatorChild(predicate));
            return this;
        }

        public Builder addOperator(Operator operator) {
            children.add(new OperatorChild(operator));
            return this;
        }

        public Operator build() {
            Preconditions.checkArgument(!children.isEmpty(), "Operator must contain at least one predicate or operator child");
            Preconditions.checkArgument(
                    (type != OperatorType.NOT || children.size() == 1),
                    "A \"not\" operator can have only a single child being either another operator or a predicate"
            );

            return new Operator(type, children);
        }
    }
}
