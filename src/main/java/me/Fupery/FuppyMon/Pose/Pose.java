package me.Fupery.FuppyMon.Pose;

import me.Fupery.FuppyMon.Entity.Parts.EntityPart;

public class Pose {
    protected final int partId;
    protected double x, y, z;
    protected double rotX, rotY, rotZ;
    protected float yaw, pitch;

    public Pose(int partId) {
        this.partId = partId;
    }

    public Pose(EntityPart part) {
        this.partId = part.getId();
    }

    public int getPartId() {
        return partId;
    }

    public Pose add(Pose pose) {
        Pose result = new Pose(partId);
        result.setTranslation(x + pose.x, y + pose.y, z + pose.z);
        result.setRotation(rotX + pose.rotX, rotY + pose.rotY, rotZ + pose.rotZ);
        result.setDirection(yaw + pose.yaw, pitch + pose.pitch);
        return result;
    }

    public Pose subtract(Pose pose) {
        Pose result = new Pose(partId);
        result.setTranslation(x - pose.x, y - pose.y, z - pose.z);
        result.setRotation(rotX - pose.rotX, rotY - pose.rotY, rotZ - pose.rotZ);
        result.setDirection(yaw - pose.yaw, pitch - pose.pitch);
        return result;
    }

    public Pose divideBy(int number) {
        Pose result = new Pose(partId);
        if (number == 0) {
            number = 1;
        }
        result.setTranslation(x / number, y / number, z / number);
        result.setRotation(rotX / number, rotY / number, rotZ / number);
        result.setDirection(yaw / number, pitch / number);
        return result;
    }

    public void setTranslation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(double x, double y, double z) {
        rotX = x;
        rotY = y;
        rotZ = z;
    }

    public void setDirection(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public Pose clone() {
        Pose result = new Pose(partId);
        result.setTranslation(x, y, z);
        result.setRotation(rotX, rotY, rotZ);
        result.setDirection(yaw, pitch);
        return result;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s : %s, %s, %s : %s, %s}",
                x, y, z, rotX, rotY, rotZ, yaw, pitch);
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

    public double getRotX() {
        return rotX;
    }

    public double getRotY() {
        return rotY;
    }

    public double getRotZ() {
        return rotZ;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
