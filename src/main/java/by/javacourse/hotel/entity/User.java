package by.javacourse.hotel.entity;

import java.math.BigDecimal;

public class User extends Entity {

    public enum Role {
        GUEST, ADMIN, CLIENT
    }

    public enum Status {
        NEW, ACTIVE, LOCKED
    }

    public static final long DEFAULT_DISCOUNT_ID = 1L;
    private String email;
    private Role role;
    private String name;
    private String phoneNumber;
    private Status status;
    private long discountId;
    private BigDecimal balance;

    private User() {
        this.role = Role.CLIENT;
        this.status = Status.NEW;
        this.discountId = DEFAULT_DISCOUNT_ID;
        this.balance = new BigDecimal("0.00");
    }

    public static Builder newBuilder() {
        return new User().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setEntityId(long entityId) {
            User.this.setEntityId(entityId);
            return this;
        }

        public Builder setEmail(String email) {
            User.this.email = email;
            return this;
        }

        public Builder setRole(Role role) {
            User.this.role = role;
            return this;
        }

        public Builder setName(String name) {
            User.this.name = name;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            User.this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setStatus(Status status) {
            User.this.status = status;
            return this;
        }

        public Builder setDiscountId(long discountId) {
            User.this.discountId = discountId;
            return this;
        }

        public Builder setBalance(BigDecimal balance) {
            User.this.balance = balance;
            return this;
        }

        public User build() {
            return User.this;
        }
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Status getStatus() {
        return status;
    }

    public long getDiscountId() {
        return discountId;
    }

    public BigDecimal getBalance() {
        return balance;
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
        User user = (User) obj;
        if (discountId != user.discountId)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        if (role != user.role)
            return false;
        if (name != null ? !name.equals(user.name) : user.name != null)
            return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null)
            return false;
        if (status != user.status)
            return false;
        if (balance != null ? !balance.equals(user.balance) : user.balance != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (email != null ? email.hashCode() : 0);
        result = prime * result + (role != null ? role.hashCode() : 0);
        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = prime * result + (status != null ? status.hashCode() : 0);
        result = prime * result + Long.hashCode(discountId);
        result = prime * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("User [")
                .append("email=")
                .append(email)
                .append(", role=")
                .append(role)
                .append(", name=")
                .append(name)
                .append(", phoneNumber=")
                .append(phoneNumber)
                .append(", status=")
                .append(status)
                .append(", discountId=")
                .append(discountId)
                .append(", balance=")
                .append(balance)
                .append("]")
                .toString();
    }
}
