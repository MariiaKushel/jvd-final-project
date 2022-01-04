package by.javacourse.hotel.entity;

public class Discount extends Entity{
    private int rate;

    public Discount() {
    }

    public Discount(long id, int rate) {
        this.setEntityId(id);
        this.rate = rate;
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
        if(getClass() != obj.getClass())
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
