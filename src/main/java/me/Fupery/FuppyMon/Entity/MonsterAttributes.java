package me.Fupery.FuppyMon.Entity;

import me.Fupery.FuppyMon.Combat.Type;
import me.Fupery.FuppyMon.Entity.API.EntityAttributes;
import me.Fupery.FuppyMon.Entity.API.Tracker;
import org.bukkit.Sound;

/**
 * Created by aidenhatcher on 26/07/2016.
 */
public class MonsterAttributes extends EntityAttributes {
    private int health, damage, accuracy, evasiveness;
    private Type type;

    public MonsterAttributes(Class<? extends Tracker> entityType, float sizeX, float sizeY,
                             double movementSpeed, Sound ambient, Sound hurt, Sound death,
                             int baseHealth, int baseDamage, int baseAccuracy, int baseEvasiveness) {
        super(entityType, sizeX, sizeY, movementSpeed, ambient, hurt, death);
    }

}
