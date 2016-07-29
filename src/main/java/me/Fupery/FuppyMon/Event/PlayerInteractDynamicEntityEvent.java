package me.Fupery.FuppyMon.Event;

import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by aidenhatcher on 20/05/2016.
 */
public class PlayerInteractDynamicEntityEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final DynamicEntity entity;
    private final Player player;
    private final Action interactType;
    private boolean playerIsOwner;

    public PlayerInteractDynamicEntityEvent(DynamicEntity entity, Player player, Action interactType) {
        this.entity = entity;
        this.player = player;
        this.interactType = interactType;
    }
    // FIXME: 26/07/2016

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public DynamicEntity getEntity() {
        return entity;
    }

    public Action getInteractType() {
        return interactType;
    }

    public boolean isPlayerIsOwner() {
        return playerIsOwner;
    }

    private enum Action {
        LEFT_CLICK, RIGHT_CLICK, SNEAK_RIGHT_CLICK;
    }
}
