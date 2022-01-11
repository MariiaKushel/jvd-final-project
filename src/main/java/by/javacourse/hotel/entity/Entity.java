package by.javacourse.hotel.entity;

public abstract class Entity {
    private long entityId;

    public Entity() {
    }

    public long getEntityId() {
        return entityId;
    }

    void setEntityId(long entityId) {
        this.entityId = entityId;
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
        return entityId == entity.entityId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(entityId);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Entity [")
                .append("id=")
                .append(entityId)
                .append("]")
                .toString();
    }
}
