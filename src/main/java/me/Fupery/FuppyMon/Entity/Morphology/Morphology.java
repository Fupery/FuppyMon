package me.Fupery.FuppyMon.Entity.Morphology;

import me.Fupery.FuppyMon.Animation.EntityAnimation;
import me.Fupery.FuppyMon.Animation.EntityState;
import me.Fupery.FuppyMon.Combat.Type;
import me.Fupery.FuppyMon.Entity.CustomEntities.EntityFlightlessBird;
import me.Fupery.FuppyMon.Entity.MonsterAttributes;
import me.Fupery.FuppyMon.Entity.MonsterFactory;
import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.Entity.API.Tracker;
import org.bukkit.Sound;

/**
 * Created by aidenhatcher on 26/07/2016.
 */
public abstract class Morphology {

    private Class<? extends Tracker> entityType;
    private float sizeX, sizeY;
    private double movementSpeed;
    private Sound ambient, hurt, death;
    private int health;
    private int damage;
    private int accuracy;
    private int evasiveness;
    private Type type;

    public Morphology(Type type, int baseHealth, int baseDamage, int baseAccuracy, int baseEvasiveness) {
        this.type = type;
        this.health = baseHealth;
        this.damage = baseDamage;
        this.accuracy = baseAccuracy;
        this.evasiveness = baseEvasiveness;
        entityType = EntityFlightlessBird.class;
        sizeX = .5f;
        sizeY = .2f;
        movementSpeed = .25d;
        ambient = null;
        hurt = null;
        death = null;
    }

    protected abstract EntityPart[] getParts();

    public MonsterFactory getFactory() {
        MonsterAttributes attrib = new MonsterAttributes(entityType, sizeX, sizeY,
                movementSpeed, ambient, hurt, death, health, damage, accuracy, evasiveness);
        MonsterFactory fac = new MonsterFactory(attrib, getParts());
        fac.addAnimation(EntityState.IDLE, getIdleAnimation());
        fac.addAnimation(EntityState.WALK, getWalkAnimation());
        fac.addAnimation(EntityState.FALL, getFallAnimation());
        return fac;
    }

    protected abstract EntityAnimation getIdleAnimation();

    protected abstract EntityAnimation getWalkAnimation();

    protected abstract EntityAnimation getFallAnimation();

//    protected abstract EntityAnimation getAttackAnimation();

    public void setEntityType(Class<? extends Tracker> entityType) {
        this.entityType = entityType;
    }

    public void setSizeX(float sizeX, float sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setAmbientSound(Sound ambient) {
        this.ambient = ambient;
    }

    public void setHurtSound(Sound hurt) {
        this.hurt = hurt;
    }

    public void setDeathSound(Sound death) {
        this.death = death;
    }
}
