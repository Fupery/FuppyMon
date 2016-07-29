package me.Fupery.FuppyMon.Entity.CustomEntities;

import me.Fupery.FuppyMon.AI.PathfinderGoalPetFollowOwner;
import me.Fupery.FuppyMon.AI.Pet;
import me.Fupery.FuppyMon.Animation.EntityState;
import me.Fupery.FuppyMon.Entity.API.EntityAttributes;
import me.Fupery.FuppyMon.Entity.API.Tracker;
import me.Fupery.FuppyMon.EntityManager;
import me.Fupery.FuppyMon.Utils.EntityPosition;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftSound;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Set;

import static me.Fupery.FuppyMon.Utils.Reflection.Accessor;

public class EntityFlightlessBird extends EntityChicken implements Tracker, Pet {
    private SoundEffect ambientSound = null, hurtSound = null, deathSound = null;
    private EntityPlayer owner;

    @Deprecated
    public EntityFlightlessBird(World world) {
        super(world);
        clearDefaultGoals();
        this.bE = false;//isChickenJockey
        addGoal(0, new PathfinderGoalFloat(this));
        addGoal(1, new PathfinderGoalRandomStroll(this, 1.0D));
        addGoal(2, new PathfinderGoalRandomLookaround(this));
        addGoal(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0f));
        addGoal(4, new PathfinderGoalTargetNearestPlayer(this));
        addGoal(5, new PathfinderGoalPetFollowOwner(this, world, getNavigation(), 1.0D, 5F, 2.0F));
    }

    // FIXME: 26/07/2016 remove egg spawning
    @Override
    public Tracker spawn(EntityManager manager, Player owner, Location location) {
        setLocation(location);
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        this.owner = ((CraftPlayer) owner).getHandle();
        manager.runTask(() -> setInvisible(true), true, 2);
        return this;
    }

    @Override
    public void applyAttributes(EntityAttributes attributes) {
        setSize(attributes.getSizeX(), attributes.getSizeY());
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(attributes.getMovementSpeed());
        if (attributes.getAmbient() != null)
            ambientSound = CraftSound.getSoundEffect(CraftSound.getSound(attributes.getAmbient()));
        if (attributes.getHurt() != null)
        hurtSound = CraftSound.getSoundEffect(CraftSound.getSound(attributes.getHurt()));
        if (attributes.getDeath() != null)
        deathSound = CraftSound.getSoundEffect(CraftSound.getSound(attributes.getDeath()));
    }

    @Override
    protected boolean damageEntity0(DamageSource damagesource, float f) {
        return false;
    }

    @Override
    protected void damageArmor(float f) {
    }

    @Override
    public EntityLiving getLastDamager() {
        return null;
    }

    @Override
    public CraftEntity getBukkitEntity() {
        return super.getBukkitEntity();
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
    }

    private void addGoal(int priority, PathfinderGoal goal) {
        goalSelector.a(priority, goal);
    }

    public Location getLocation() {
        return new Location(world.getWorld(), locX, locY, locZ, getHeadRotation(), getHeadHeight());
    }

    public void setLocation(Location location) {
        setLocation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
    }

    public EntityPosition getPosition() {
        return new EntityPosition(locX, locY, locZ, yaw, pitch, getHeadRotation(), getHeadHeight(), velocityChanged);
    }

    @Override
    public EntityState getCurrentState() {
        if (!valid || !isAlive()) return EntityState.INVALID;
        if (!onGround) return EntityState.FALL;
        else if (inWater) return EntityState.SWIM;
        else if (velocityChanged) return EntityState.WALK;
        else return EntityState.IDLE;
    }

    @Override
    public org.bukkit.World getBukkitWorld() {
        return getWorld().getWorld();
    }

    private void clearDefaultGoals() {
        Accessor<Set> goalAccessor = new Accessor<>(goalSelector);
        Accessor<Set> targetAccessor = new Accessor<>(goalSelector);
        goalAccessor.get("b").clear();
        goalAccessor.get("c").clear();
        targetAccessor.get("b").clear();
        targetAccessor.get("c").clear();
    }

    public void remove() {
        getBukkitEntity().remove();
    }

    @Override
    public void n() {
        bD = 2;//cancel egg drops
        super.n();
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Override
    public ItemStack getEquipment(EnumItemSlot enumItemSlot) {
        return null;
    }

    @Override
    public void setSlot(EnumItemSlot enumItemSlot, ItemStack itemStack) {
    }


//    @Override
//    public EnumMainHand cr() {
//        return null;
//    }
    //dunno what that is.

    @Override
    protected boolean playStepSound() {
        return super.playStepSound();
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        //Ignore chicken jockey and egg lay time flags
        this.die();
        //prevent syncRespawn
    }

    protected SoundEffect G() {
        return ambientSound;
    }

    protected SoundEffect bV() {
        return hurtSound;
    }

    protected SoundEffect bW() {
        return deathSound;
    }

    @Override
    public ControllerLook getControllerLook() {
        return super.getControllerLook();
    }

    @Override
    public EntityLiving getOwner() {
        return owner;
    }

    @Override
    public void addPath(PathType pathType, float value) {
        this.a(pathType, value);
    }

    @Override
    public float addPath(PathType pathType) {
        return this.a(pathType);
    }

    @Override
    public double getDistanceBetween(Entity entity) {
        return h(entity);
    }

    @Override
    public void setPositionRotation(double x, double y, double z, float yaw, float pitch) {
        super.setPositionRotation(x, y, z, yaw, pitch);
    }

    @Override
    public float getBodyYaw() {
        return yaw;
    }

    @Override
    public float getBodyPitch() {
        return pitch;
    }
}
