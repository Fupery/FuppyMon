package me.Fupery.FuppyMon.Entity.API;

import me.Fupery.FuppyMon.Animation.AnimationBuilder;
import me.Fupery.FuppyMon.Animation.AnimationProtocol;
import me.Fupery.FuppyMon.Animation.EntityAnimation;
import me.Fupery.FuppyMon.Animation.EntityState;
import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.EntityManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class EntityFactory {

    private final EntityPart[] parts;
    private EntityAttributes attributes;
    private HashMap<EntityState, AnimationBuilder> builders = new HashMap<>();
    private HashMap<EntityState, EntityAnimation> protocol = new HashMap<>();
    private WeakReference<AnimationProtocol> protocolRef = null;
    private ItemStack displayItem;

    public EntityFactory(EntityAttributes attributes, EntityPart[] parts) {
        this.attributes = attributes;
        this.parts = parts;
        this.displayItem = new ItemStack(Material.MONSTER_EGG);
    }

    public void setUIDisplayItem(Material material, short durability) {
        displayItem = new ItemStack(material, 1, durability);
    }

    public void setUIDisplayEgg(EntityType type) {
        displayItem = new ItemStack(Material.MONSTER_EGG, 1, type.getTypeId());
        displayItem.setData(new SpawnEgg(type));
    }

    public DynamicEntity buildEntity(EntityManager manager) {
        return new DynamicEntity(manager, attributes, getParts(), getProtocol());
    }

    private EntityPart[] getParts() {
        EntityPart[] partList = new EntityPart[this.parts.length];
        for (int i = 0; i < partList.length; i++) {
            partList[i] = parts[i].clone();
        }
        return partList;
    }

    private AnimationProtocol getProtocol() {
        if (protocolRef == null || protocolRef.get() == null) {
            for (EntityState entityState : builders.keySet()) {
                protocol.put(entityState, builders.get(entityState).buildAnimation());
            }
            protocolRef = new WeakReference<>(new AnimationProtocol(protocol));
        }
        return protocolRef.get().clone();
    }

    public void addAnimation(EntityState state, EntityAnimation animation) {
        protocol.put(state, animation.clone());
    }

    public AnimationBuilder addAnimation(EntityState entityState) {
        AnimationBuilder builder = new AnimationBuilder(parts);
        builders.put(entityState, builder);
        return builder;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }
}
