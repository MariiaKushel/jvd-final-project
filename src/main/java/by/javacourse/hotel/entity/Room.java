package by.javacourse.hotel.entity;

import by.javacourse.hotel.util.ImageEncoder;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class Room extends Entity {

    private int number;
    private int sleepingPlace;
    private BigDecimal pricePerDay;
    private BigDecimal rating;
    private boolean visible;
    private String description;

    public Room() {
    }

    public static Builder newBuilder() {
        return new Room().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setEntityId(long entityId) {
            Room.this.setEntityId(entityId);
            return this;
        }

        public Builder setNumber(int number) {
            Room.this.number = number;
            return this;
        }

        public Builder setSleepingPlace(int sleepingPlace) {
            Room.this.sleepingPlace = sleepingPlace;
            return this;
        }

        public Builder setPricePerDay(BigDecimal pricePerDay) {
            Room.this.pricePerDay = pricePerDay;
            return this;
        }

        public Builder setRating(BigDecimal rating) {
            Room.this.rating = rating;
            return this;
        }

        public Builder setVisible(boolean visible) {
            Room.this.visible = visible;
            return this;
        }

        public Builder setDescription(String description) {
            Room.this.description = description;
            return this;
        }

        public Room build() {
            return Room.this;
        }
    }

    public int getNumber() {
        return number;
    }

    public int getSleepingPlace() {
        return sleepingPlace;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Room room = (Room) o;
        if (number != room.number)
            return false;
        if (sleepingPlace != room.sleepingPlace)
            return false;
        if (visible != room.visible)
            return false;
        if (pricePerDay != null ? !pricePerDay.equals(room.pricePerDay) : room.pricePerDay != null)
            return false;
        if (rating != null ? !rating.equals(room.rating) : room.rating != null)
            return false;
        return description != null ? description.equals(room.description) : room.description == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + number;
        result = prime * result + sleepingPlace;
        result = prime * result + (pricePerDay != null ? pricePerDay.hashCode() : 0);
        result = prime * result + (rating != null ? rating.hashCode() : 0);
        result = prime * result + (visible ? 1 : 0);
        result = prime * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Room [")
                .append("number=")
                .append(number)
                .append(", sleepingPlace=")
                .append(sleepingPlace)
                .append(", pricePerDay=")
                .append(pricePerDay)
                .append(", rating=")
                .append(rating)
                .append(", visible=")
                .append(visible)
                .append(", description=")
                .append(description)
                .append("]")
                .toString();
    }
}
