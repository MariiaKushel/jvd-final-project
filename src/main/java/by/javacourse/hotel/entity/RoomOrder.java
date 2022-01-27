package by.javacourse.hotel.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RoomOrder extends Entity {
    public enum Status {
        NEW, CONFIRMED, IN_PROGRESS, CANCELED_BY_CLIENT, CANCELED_BY_ADMIN, COMPLETED
    }

    private long userId;
    private long roomId;
    private LocalDate date;
    private LocalDate from;
    private LocalDate to;
    private BigDecimal amount;
    private Status status;
    private boolean prepayment;

    private RoomOrder() {
        this.status = Status.NEW;
    }

    public static Builder newBuilder() {
        return new RoomOrder().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setEntityId(long entityId) {
            RoomOrder.this.setEntityId(entityId);
            return this;
        }

        public Builder setUserId(long userId) {
            RoomOrder.this.userId = userId;
            return this;
        }

        public Builder setRoomId(long roomId) {
            RoomOrder.this.roomId = roomId;
            return this;
        }

        public Builder setDate(LocalDate date) {
            RoomOrder.this.date = date;
            return this;
        }

        public Builder setFrom(LocalDate from) {
            RoomOrder.this.from = from;
            return this;

        }
        public Builder setTo(LocalDate to) {
            RoomOrder.this.to = to;
            return this;
        }

        public Builder setAmount (BigDecimal amount){
            RoomOrder.this.amount = amount;
            return this;
        }

        public Builder setStatus (Status status){
            RoomOrder.this.status = status;
            return this;
        }

        public Builder setPrepayment(boolean prepayment){
            RoomOrder.this.prepayment = prepayment;
            return this;
        }

        public RoomOrder build(){
            return RoomOrder.this;
        }
    }

    public long getUserId() {
        return userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isPrepayment() {
        return prepayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RoomOrder roomOrder = (RoomOrder) o;

        if (userId != roomOrder.userId) return false;
        if (roomId != roomOrder.roomId) return false;
        if (prepayment != roomOrder.prepayment) return false;
        if (date != null ? !date.equals(roomOrder.date) : roomOrder.date != null) return false;
        if (from != null ? !from.equals(roomOrder.from) : roomOrder.from != null) return false;
        if (to != null ? !to.equals(roomOrder.to) : roomOrder.to != null) return false;
        if (amount != null ? !amount.equals(roomOrder.amount) : roomOrder.amount != null) return false;
        return status == roomOrder.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (roomId ^ (roomId >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (prepayment ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("RoomOrder [")
                .append("userId=")
                .append(userId)
                .append(", roomId=")
                .append(roomId)
                .append(", date=")
                .append(date)
                .append(", from=")
                .append(from)
                .append(", to=")
                .append(to)
                .append(", amount=")
                .append(amount)
                .append(", status=")
                .append(status)
                .append(", prepayment=")
                .append(prepayment)
                .append("]")
                .toString();
    }
}
