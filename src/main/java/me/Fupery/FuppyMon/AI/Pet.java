package me.Fupery.FuppyMon.AI;

import net.minecraft.server.v1_10_R1.ControllerLook;
import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityLiving;
import net.minecraft.server.v1_10_R1.PathType;

/**
 * Interface for PathfinderGoalPetFollowOwner to interact with any entity
 */
public interface Pet {

    ControllerLook getControllerLook();

    EntityLiving getOwner();

    void addPath(PathType pathType, float value);

    float addPath(PathType pathType);

    double getDistanceBetween(Entity entity);

    void setPositionRotation(double x, double y, double z, float yaw, float pitch);

    float getBodyYaw();

    float getBodyPitch();
}
