package by.javacourse.hotel.model.entity;

public abstract class Entity {
    private long id;

    public Entity() {
    }

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entity entity = (Entity) obj;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Entity [")
                .append("id=")
                .append(id)
                .append("]")
                .toString();
    }
}
