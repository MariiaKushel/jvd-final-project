package by.javacourse.hotel.model.entity;

import java.math.BigDecimal;

public class User extends Entity {

    public enum Role {
        ADMIN, CLIENT
    }

    public enum Status {
        NEW, ACTIVE, LOCKED
    }

    private String login;
    private String password;
    private Role role;
    private String name;
    private String phoneNumber;
    private Status status;
    private long discountId;
    private BigDecimal balance;

    public User() {
    }

    public User(long id, String login, String password, Role role, String name, String phoneNumber, Status status, long discountId, BigDecimal balance) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.discountId = discountId;
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if(getClass()!= obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        User user = (User) obj;
        if (discountId != user.discountId)
            return false;
        if (login != null ? !login.equals(user.login) : user.login != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
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
        result = prime * result + (login != null ? login.hashCode() : 0);
        result = prime * result + (password != null ? password.hashCode() : 0);
        result = prime * result + (role != null ? role.hashCode() : 0);
        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = prime * result + (status != null ? status.hashCode() : 0);
        result = prime * result + (int) (Long.hashCode(discountId));
        result = prime * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("User [")
                .append("login=")
                .append(login)
                .append(", password=")
                .append(password)
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
