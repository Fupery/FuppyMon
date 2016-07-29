package me.Fupery.FuppyMon.Entity.Parts;

import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import me.Fupery.FuppyMon.Pose.Pose;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a limb or part of a DynamicEntity
 */
public abstract class EntityPart {
    /**
     * PartId is unique for each new part, but is not unique for clones of each part.
     */
    private static AtomicInteger idCounter = new AtomicInteger(0);
    protected final double offsetX, offsetY, offsetZ;
    protected final float offsetYaw, offsetPitch;
    private final int id;

    protected EntityPart(double offsetX, double offsetY, double offsetZ, float offsetYaw, float offsetPitch) {
        this.id = createID();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.offsetYaw = offsetYaw;
        this.offsetPitch = offsetPitch;
    }

    protected EntityPart(EntityPart part) {
        this.id = part.getId();
        this.offsetX = part.getOffsetX();
        this.offsetY = part.getOffsetY();
        this.offsetZ = part.getOffsetZ();
        this.offsetYaw = part.getOffsetYaw();
        this.offsetPitch = part.getOffsetPitch();
    }

    private static synchronized int createID() {
        return idCounter.incrementAndGet();
    }

    public abstract void updatePosition(DynamicEntity parent, Pose pose);

    public abstract void spawn(DynamicEntity parent, Location spawnLoc);

    public abstract void remove();

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityPart && ((EntityPart) obj).id == this.id;
    }

    @Override
    public abstract EntityPart clone();

    public int getId() {
        return id;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    public float getOffsetYaw() {
        return offsetYaw;
    }

    public float getOffsetPitch() {
        return offsetPitch;
    }
}
