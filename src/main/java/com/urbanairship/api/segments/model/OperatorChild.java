/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

public final class OperatorChild {

    private final Predicate predicate;
    private final Operator operator;
    private final boolean predicateChild;

    public OperatorChild(Predicate predicate) {
        this(predicate, null, true);
    }

    public OperatorChild(Operator operator) {
        this(null, operator, false);
    }

    private OperatorChild(Predicate predicate, Operator operator, boolean predicateChild) {
        this.predicate = predicate;
        this.operator = operator;
        this.predicateChild = predicateChild;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public Operator getOperator() {
        return operator;
    }

    public boolean isPredicateChild() {
        return predicateChild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperatorChild that = (OperatorChild) o;

        if (predicateChild != that.predicateChild) {
            return false;
        }
        if (operator != null ? !operator.equals(that.operator) : that.operator != null) {
            return false;
        }
        if (predicate != null ? !predicate.equals(that.predicate) : that.predicate != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = predicate != null ? predicate.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (predicateChild ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OperatorChild{" +
                "predicate=" + predicate +
                ", operator=" + operator +
                ", predicateChild=" + predicateChild +
                '}';
    }
}
