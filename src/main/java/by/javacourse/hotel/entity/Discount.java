package by.javacourse.hotel.entity;

public class Discount extends Entity {
    private int rate;

    public Discount() {
    }

    public static Builder newBuilder() {
        return new Discount().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setEntityId(long entityId) {
            Discount.this.setEntityId(entityId);
            return this;
        }

        public Builder setRate(int rate) {
            Discount.this.rate = rate;
            return this;
        }

        public Discount build() {
            return Discount.this;
        }
    }


    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        Discount discount = (Discount) obj;
        return rate == discount.rate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + rate;
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Discount [")
                .append("rate=")
                .append(rate)
                .append("]")
                .toString();
    }
}
