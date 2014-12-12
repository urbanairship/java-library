package com.urbanairship.api.location.model;

public final class BoundedBox {

    private final Point cornerOne;
    private final Point cornerTwo;

    public BoundedBox(Point cornerOne, Point cornerTwo) {
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
    }

    public Point getCornerOne() {
        return cornerOne;
    }

    public Point getCornerTwo() {
        return cornerTwo;
    }

    public boolean isValid() {
        return cornerOne.isValid() && cornerTwo.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoundedBox that = (BoundedBox) o;

        if (cornerOne != null ? !cornerOne.equals(that.cornerOne) : that.cornerOne != null) {
            return false;
        }
        if (cornerTwo != null ? !cornerTwo.equals(that.cornerTwo) : that.cornerTwo != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = cornerOne != null ? cornerOne.hashCode() : 0;
        result = 31 * result + (cornerTwo != null ? cornerTwo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BoundedBox{" +
                "cornerOne=" + cornerOne +
                ", cornerTwo=" + cornerTwo +
                '}';
    }
}