package me.Fupery.FuppyMon.Event;

import me.Fupery.FuppyMon.EntityManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by aidenhatcher on 18/05/2016.
 */
public class PlayerStatusListener implements Listener {
    private EntityManager manager;

    public PlayerStatusListener(EntityManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        manager.clearPlayerPets(event.getPlayer());
        manager.updateOnlinePlayerCache();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        manager.updateOnlinePlayerCache();
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        manager.changeWorld(event.getPlayer());
    }
}
