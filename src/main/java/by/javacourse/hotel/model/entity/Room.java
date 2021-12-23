package by.javacourse.hotel.model.entity;

import java.math.BigDecimal;

public class Room extends Entity {
    private int number;
    private int sleepingPlace;
    private BigDecimal pricePerDay;
    private BigDecimal raiting;
    private boolean show;

    public Room() {
    }

    public Room(long id, int number, int sleepingPlace, BigDecimal pricePerDay, BigDecimal raiting, boolean show) {
        super(id);
        this.number = number;
        this.sleepingPlace = sleepingPlace;
        this.pricePerDay = pricePerDay;
        this.raiting = raiting;
        this.show = show;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSleepingPlace() {
        return sleepingPlace;
    }

    public void setSleepingPlace(int sleepingPlace) {
        this.sleepingPlace = sleepingPlace;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public BigDecimal getRaiting() {
        return raiting;
    }

    public void setRaiting(BigDecimal raiting) {
        this.raiting = raiting;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
    
}
