package me.Fupery.FuppyMon.Entity.API;

import me.Fupery.FuppyMon.Animation.AnimationProtocol;
import me.Fupery.FuppyMon.Animation.EntityAnimation;
import me.Fupery.FuppyMon.Animation.EntityState;
import me.Fupery.FuppyMon.Animation.Frame;
import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.EntityManager;
import me.Fupery.FuppyMon.Pose.Pose;
import me.Fupery.FuppyMon.Utils.EntityPosition;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DynamicEntity {
    public static final String TRACKER_TAG = "%EntityTracker%";
    public static final String PART_TAG = "%EntityPart%";
    private static AtomicInteger idCounter = new AtomicInteger(0);
    private final EntityAttributes attributes;
    private final int id;
    private final EntityPart[] parts;
    private final EntityManager manager;
    private final AtomicBoolean exists;
    private Tracker tracker;
    private AnimationProtocol protocol;
    private EntityState currentState;
    private EntityPosition lastPosition;

    protected DynamicEntity(EntityManager manager, EntityAttributes attributes, EntityPart[] parts, AnimationProtocol protocol) {
        id = nextID();
        this.manager = manager;
        this.attributes = attributes;
        this.protocol = protocol;
        this.parts = parts;
        exists = new AtomicBoolean(false);
        tracker = null;
    }

    private static synchronized int nextID() {
        return idCounter.incrementAndGet();
    }

    private EntityPart getPart(int partId) {
        if (!exists()) return null;
        for (EntityPart part : parts) {
            if (part.getId() == partId) return part;
        }
        return null;
    }

    public World getWorld() {
        return exists() ? tracker.getBukkitWorld() : null;
    }

    public EntityPosition getPosition() {
        return lastPosition;
    }

    public Location getLocation() {
        if (!exists()) return null;
        EntityPosition pos = lastPosition;
        return new Location(getWorld(), pos.getX(), pos.getY(), pos.getZ(), pos.getBodyYaw(), pos.getBodyPitch());
    }

    public Collection<Player> getViewingPlayers() {
        return manager.getPlayersViewing(this);
    }

    /**
     * Called by the Animation clock - updates this stand's position and parts.
     * Note that this method will always be called on an async thread. Calls to
     * the bukkit api should be piped through the PetManager's task methods.
     */
    public void update() {
        if (!exists()) return;
        EntityState state;
        //Ensure tracker isn't dead
        if (tracker == null || (state = tracker.getCurrentState()) == EntityState.INVALID) {
            manager.runTask(this::safeRemove, true);
            return;
        }
        lastPosition = tracker.getPosition();
        //Get current state
        if (state != currentState) {
            currentState = state;
        }
        Frame frame = getCurrentAnimation().next();
        for (Pose pose : frame.getPoses()) {
            EntityPart part = getPart(pose.getPartId());
            if (part != null) part.updatePosition(this, pose);
        }
    }

    private EntityAnimation getCurrentAnimation() {
        return exists() ? protocol.getAnimationFor(currentState) : null;
    }

    public void syncRespawn(Player owner) {
        if (exists()) {
            setExists(false);
            tracker.remove();
            for (EntityPart part : parts) part.remove();
        }
        tracker = attributes.newInstance(manager, owner, owner.getLocation());
        manager.addMetadataTag(tracker.getBukkitEntity(), TRACKER_TAG);
        if (tracker == null) return;
        for (EntityPart part : parts) {
            part.spawn(this, owner.getLocation());
        }
        setExists(true);
    }

    public void spawn(Player owner, Location location) {
        if (exists()) return;
        tracker = attributes.newInstance(manager, owner, location);
        manager.addMetadataTag(tracker.getBukkitEntity(), TRACKER_TAG);
        if (tracker == null) return;
        for (EntityPart part : parts) {
            part.spawn(this, location);
        }
        setExists(true);
    }

    /**
     * Removes the entity; must be called from the main thread.
     */
    public void forceRemove() {
        tracker.remove();
        for (EntityPart part : parts) part.remove();
    }

    public void safeRemove() {
        if (!exists()) return;
        setExists(false);
        manager.runTask(this::forceRemove, true);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DynamicEntity && ((DynamicEntity) obj).id == id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    public synchronized boolean exists() {
        return exists.get();
    }

    private synchronized void setExists(boolean value) {
        exists.set(value);
    }
}
