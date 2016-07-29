package me.Fupery.FuppyMon.Entity.API;

import me.Fupery.FuppyMon.EntityManager;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EntityAttributes {
    private final Class<? extends Tracker> entityType;
    private final float sizeX, sizeY;
    private final double movementSpeed;
    private final Sound ambient, hurt, death;

    public EntityAttributes(Class<? extends Tracker> entityType, float sizeX, float sizeY, double movementSpeed,
                            Sound ambient, Sound hurt, Sound death) {
        this.entityType = entityType;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.movementSpeed = movementSpeed;
        this.ambient = ambient;
        this.hurt = hurt;
        this.death = death;
    }

    Tracker newInstance(EntityManager manager, Player owner, Location location) {
        World mcWorld = ((CraftWorld) location.getWorld()).getHandle();
        Tracker tracker;
        try {
            Constructor<? extends Tracker> cons = entityType.getDeclaredConstructor(World.class);
            cons.setAccessible(true);
            tracker = cons.newInstance(mcWorld);
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        tracker.applyAttributes(this);
        return tracker.spawn(manager, owner, location);
    }

    public Class<? extends Tracker> getEntityType() {
        return entityType;
    }

    public float getSizeX() {
        return sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public Sound getAmbient() {
        return ambient;
    }

    public Sound getHurt() {
        return hurt;
    }

    public Sound getDeath() {
        return death;
    }
}
