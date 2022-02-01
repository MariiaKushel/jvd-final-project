package by.javacourse.hotel.entity;

/**
 * {@code Description} class represent a description of room
 * @see Entity
 */
public class Description extends Entity {

    private String descriptionRu;
    private String descriptionEn;

    private Description() {

    }

    /**
     * {@code newBuilder} method to get {@link Builder}
     * @return {@link Builder}
     */
    public static Builder newBuilder() {
        return new Description().new Builder();
    }

    /**
     * {@code Builder} class to build {@link Description}
     */
    public class Builder {
        private Builder() {
        }

        public Builder setEntityId(long entityId) {
            Description.this.setEntityId(entityId);
            return this;
        }

        public Builder setDescriptionRu(String descriptionRu) {
            Description.this.descriptionRu = descriptionRu;
            return this;
        }

        public Builder setDescriptionEn(String descriptionEn) {
            Description.this.descriptionEn = descriptionEn;
            return this;
        }

        public Description build() {
            return Description.this;
        }
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public String getDescriptionEn() {
        return descriptionEn;
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
        Description that = (Description) o;
        if (descriptionRu != null ? !descriptionRu.equals(that.descriptionRu) : that.descriptionRu != null)
            return false;
        return descriptionEn != null ? descriptionEn.equals(that.descriptionEn) : that.descriptionEn == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (descriptionRu != null ? descriptionRu.hashCode() : 0);
        result = prime * result + (descriptionEn != null ? descriptionEn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Description [")
                .append("descriptionRu=")
                .append(descriptionRu)
                .append(", descriptionEn=")
                .append(descriptionEn)
                .append("]")
                .toString();
    }
}
