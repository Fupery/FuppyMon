package me.Fupery.FuppyMon.Utils;

import org.bukkit.Location;

public class EntityPosition {
    private double x, y, z;
    private float bodyYaw, bodyPitch;
    private float headYaw, headPitch;
    private boolean motion;

    public EntityPosition(double x, double y, double z,
                          float bodyYaw, float bodyPitch, float headYaw, float headPitch, boolean motion) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.bodyPitch = bodyYaw;
        this.bodyYaw = bodyYaw;
        this.headPitch = headPitch;
        this.headYaw = headYaw;
        this.motion = motion;
    }

    public Location add(Location loc) {
        return new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y,
                loc.getZ() + z, loc.getYaw() + headYaw, loc.getPitch() + headPitch);
    }

    public boolean headIsHyperRotated() {
        return Math.abs(headYaw - bodyYaw) > 60;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getBodyYaw() {
        return bodyYaw;
    }

    public float getBodyPitch() {
        return bodyPitch;
    }

    public float getHeadYaw() {
        return headYaw;
    }

    public float getHeadPitch() {
        return headPitch;
    }

    public boolean isInMotion() {
        return motion;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s : %s, %s}",
                x, y, z, headYaw, headPitch);
    }
}
