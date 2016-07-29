package me.Fupery.FuppyMon.Entity;


import me.Fupery.FuppyMon.Animation.AnimationProtocol;
import me.Fupery.FuppyMon.Combat.Type;
import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.EntityManager;
import org.bukkit.Location;

import java.util.Random;

public abstract class Monster extends DynamicEntity {

    protected int level;
    protected int health;
    protected int damage;
    protected int accuracy;
    protected int evasiveness;
    protected Type type;
    protected Random random = new Random();

    Monster(EntityManager manager, MonsterAttributes attributes, EntityPart[] parts, AnimationProtocol protocol) {
        super(manager, attributes, parts, protocol);
    }

    public abstract boolean spawn(Location location, SpawnReason reason);

    public abstract boolean despawn(DespawnReason reason);

    public abstract void startAttacking(Monster target);

    public abstract void stopAttacking(Monster target);

    public abstract Monster getTarget(Monster target);

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getEvasiveness() {
        return evasiveness;
    }

    public Type getType() {
        return type;
    }

    public double getAttackRoll() {
        return random.nextDouble();
    }
}
