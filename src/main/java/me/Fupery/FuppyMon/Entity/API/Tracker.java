package me.Fupery.FuppyMon.Entity.API;

import me.Fupery.FuppyMon.Animation.EntityState;
import me.Fupery.FuppyMon.EntityManager;
import me.Fupery.FuppyMon.Utils.EntityPosition;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Provides pathfinding, collision management, and bukkit api interaction for a DynamicEntity. Custom Entities
 * must implement this interface and be registered with the CustomEntityManager to act as a DynamicEntity tracker.
 */
public interface Tracker {
    EntityPosition getPosition();

    EntityState getCurrentState();

    World getBukkitWorld();

    Entity getBukkitEntity();

    void setLocation(Location location);

    Tracker spawn(EntityManager manager, Player owner, Location location);

    void applyAttributes(EntityAttributes attributes);

    void remove();
}
