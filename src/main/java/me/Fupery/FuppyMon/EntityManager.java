package me.Fupery.FuppyMon;

import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import me.Fupery.FuppyMon.Entity.API.EntityFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Handles pet spawning, removal, and animations. Handles interaction
 * between synchronous events and the asynchronous DynamicEntity AnimationLoop.
 */
public class EntityManager {
    private final FuppyMon plugin;
    private final HashMap<String, EntityFactory> pets = new HashMap<>();
    private final ConcurrentHashMap<DynamicEntity, UUID> entities;
    private AnimationLoop clock = null;
    private WeakReference<ArrayList<Player>> players;

    EntityManager(FuppyMon plugin) {
        this.plugin = plugin;
        entities = new ConcurrentHashMap<>();
    }

    public void updateOnlinePlayerCache() {
        ArrayList<Player> onlinePlayers = new ArrayList<>();
        onlinePlayers.addAll(Bukkit.getOnlinePlayers());
        players = new WeakReference<>(onlinePlayers);
    }

    public void changeWorld(Player player) {
        ArrayList<DynamicEntity> pets = getPlayerPets(player);
        for (DynamicEntity pet : pets) {
            entities.remove(pet);
            pet.safeRemove();
            pet.spawn(player, player.getLocation());
            pet.syncRespawn(player);
            entities.put(pet, player.getUniqueId());
        }
    }

    public Collection<Player> getPlayersViewing(DynamicEntity entity) {
        if (players == null || players.get() == null) updateOnlinePlayerCache();
        ArrayList<Player> viewingPlayers = new ArrayList<>();
        Location location = entity.getLocation();

        viewingPlayers.addAll(players.get().stream().filter(player ->
                player.getLocation().distanceSquared(location) <= 256).collect(Collectors.toList()));
        return viewingPlayers;
    }

    private ArrayList<DynamicEntity> getPlayerPets(Player player) {
        ArrayList<DynamicEntity> pets = new ArrayList<>();
        for (DynamicEntity pet : entities.keySet()) {
            if (entities.get(pet).equals(player.getUniqueId())) pets.add(pet);
        }
        return pets;
    }

    public void clearPlayerPets(Player player) {
        getPlayerPets(player).forEach(this::removePet);
    }

    private void spawnPet(EntityFactory fac, Player owner, Location location) {
        DynamicEntity entity = fac.buildEntity(this);
        entity.spawn(owner, location);
        entities.put(entity, owner.getUniqueId());
    }

    public void removePet(DynamicEntity entity) {
        entities.remove(entity);
        entity.safeRemove();
    }

    void registerPet(String name, EntityFactory factory) {
        pets.put(name, factory);
    }

    public void spawn(String petName, Player owner, Location location) {
        EntityFactory fac = pets.get(petName);
        if (fac == null) {
            owner.sendMessage("No entities could be found by this name!");
            return;
        }
        if (clock == null) clock = new AnimationLoop();
        spawnPet(fac, owner, location);
    }

    public void runTask(Runnable runnable, boolean sync) {
        if (sync) Bukkit.getScheduler().runTask(plugin, runnable);
        else Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public void runTask(Runnable runnable, boolean sync, int delay) {
        if (sync) Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
        else Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    public void stop() {
        if (clock != null) clock.cancel();
        for (DynamicEntity entity : entities.keySet()) {
            entity.forceRemove();
        }
    }

    public void addMetadataTag(Entity entity, String tag) {
        entity.setMetadata(tag, new FixedMetadataValue(plugin, tag));
    }

    public void removeMetdataTag(Entity entity, String tag) {
        entity.removeMetadata(tag, plugin);
    }

    private class AnimationLoop extends BukkitRunnable {

        private AnimationLoop() {
            runTaskTimerAsynchronously(plugin, 2, 2);
        }

        @Override
        public void run() {
            entities.keySet().forEach(this::updateEntity);
        }

        private void updateEntity(final DynamicEntity entity) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                if (!entity.exists()) {
                    entities.remove(entity);
                    return;
                }
                entity.update();
            });
        }
    }
}
