package com.urbanairship.api.channel.information.model.ios;


public final class QuietTime {
    public static final QuietTime NULL_INSTANCE = new QuietTime(null, null);

    private final String start;
    private final String end;

    public static Builder newBuilder() {
        return new Builder();
    }

    private QuietTime(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuietTime quietTime = (QuietTime) o;

        if (!end.equals(quietTime.end)) return false;
        if (!start.equals(quietTime.start)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "QuietTime{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }

    public final static class Builder {
        private String start = null;
        private String end = null;

        private Builder() { }

        public Builder setStart(String start){
            this.start = start;
            return this;
        }

        public Builder setEnd(String end){
            this.end = end;
            return this;
        }

        public QuietTime build() {
            return new QuietTime(start, end);
        }
    }
}