package by.javacourse.hotel.entity;

import java.time.LocalDate;

/**
 * {@code Review} class represent a review
 * @see Entity
 */
public class Review extends Entity {

    private LocalDate date;
    private int roomMark;
    private String reviewContent;
    private boolean hidden;
    private String author;

    private Review() {

    }

    /**
     * {@code newBuilder} method to get {@link Builder}
     * @return {@link Builder}
     */
    public static Builder newBuilder() {
        return new Review().new Builder();
    }

    /**
     * {@code Builder} class to build {@link Review}
     */
    public class Builder {
        private Builder() {
        }

        public Builder setEntityId(long entityId) {
            Review.this.setEntityId(entityId);
            return this;
        }

        public Builder setDate(LocalDate date) {
            Review.this.date = date;
            return this;
        }

        public Builder setRoomMark(int roomMark) {
            Review.this.roomMark = roomMark;
            return this;
        }

        public Builder setReviewContent(String reviewContent) {
            Review.this.reviewContent = reviewContent;
            return this;
        }

        public Builder setHidden(boolean hidden) {
            Review.this.hidden = hidden;
            return this;
        }

        public Builder setAuthor(String author) {
            Review.this.author = author;
            return this;
        }

        public Review build() {
            return Review.this;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public int getRoomMark() {
        return roomMark;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getAuthor() {
        return author;
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
        Review review = (Review) o;
        if (roomMark != review.roomMark)
            return false;
        if (hidden != review.hidden)
            return false;
        if (date != null ? !date.equals(review.date) : review.date != null) return false;
        if (reviewContent != null ? !reviewContent.equals(review.reviewContent) : review.reviewContent != null)
            return false;
        return author != null ? author.equals(review.author) : review.author == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (date != null ? date.hashCode() : 0);
        result = prime * result + roomMark;
        result = prime * result + (reviewContent != null ? reviewContent.hashCode() : 0);
        result = prime * result + (hidden ? 1 : 0);
        result = prime * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Review [")
                .append("date=")
                .append(date)
                .append(", roomMark=")
                .append(roomMark)
                .append(", reviewContent=")
                .append(reviewContent)
                .append(", hidden=")
                .append(hidden)
                .append(", author=")
                .append(author)
                .append("]")
                .toString();
    }
}
