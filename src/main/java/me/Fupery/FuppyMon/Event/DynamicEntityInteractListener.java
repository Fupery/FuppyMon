package me.Fupery.FuppyMon.Event;

import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class DynamicEntityInteractListener implements Listener {

    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if (event.getRightClicked().getCustomName().equals(DynamicEntity.PART_TAG)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPortalTravel(EntityPortalEvent event) {
        Entity entity = event.getEntity();
        if (DynamicEntity.PART_TAG.equals(entity.getCustomName())
                || entity.hasMetadata(DynamicEntity.TRACKER_TAG)) {
            event.setCancelled(true);
        }
    }
}