package me.Fupery.FuppyMon.Entity.CustomEntities;

import me.Fupery.FuppyMon.AI.PathfinderGoalPetFollowOwner;
import me.Fupery.FuppyMon.AI.Pet;
import me.Fupery.FuppyMon.AI.WrappedMoveController;
import me.Fupery.FuppyMon.Animation.EntityState;
import me.Fupery.FuppyMon.Entity.API.EntityAttributes;
import me.Fupery.FuppyMon.Entity.API.Tracker;
import me.Fupery.FuppyMon.EntityManager;
import me.Fupery.FuppyMon.Utils.EntityPosition;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftSound;
import org.bukkit.craftbukkit.v1_10_R1.SpigotTimings;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static me.Fupery.FuppyMon.Utils.Reflection.Accessor;

public class EntityBiped extends EntityChicken implements Tracker, Pet {
    private SoundEffect ambientSound = null, hurtSound = null, deathSound = null;
    private EntityPlayer owner;

    @Deprecated
    public EntityBiped(World world) {
        super(world);
        clearDefaultGoals();
        this.bE = false;//isChickenJockey
        this.moveController = new WrappedMoveController(this);
        addGoal(0, new PathfinderGoalFloat(this));
        addGoal(1, new PathfinderGoalRandomStroll(this, 1.0D));
        addGoal(2, new PathfinderGoalRandomLookaround(this));
        addGoal(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0f));
        addGoal(4, new PathfinderGoalTargetNearestPlayer(this));
        addGoal(5, new PathfinderGoalPetFollowOwner(this, world, getNavigation(), 1.0D, 5F, 2.0F));
    }
    private WrappedMoveController getController() {
        if (!(moveController instanceof WrappedMoveController)) {
            moveController = new WrappedMoveController(this, getControllerMove());
        }
        return ((WrappedMoveController) this.moveController);
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
        else if (motX != 0.0 || motY != -0.0784000015258789 || motZ != 0.0) return EntityState.WALK;
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
//        if (!onGround && !inWater) {
//            bC += 10;
//            this.motY -=.15;
//        }
//        //insentient
//        if(this.bC > 0) {
//            --this.bC;
//        }
//
//        if(this.bi > 0 && !this.bA()) {
//            double d0 = this.locX + (this.bj - this.locX) / (double)this.bi;
//            double d1 = this.locY + (this.bk - this.locY) / (double)this.bi;
//            double d2 = this.locZ + (this.bl - this.locZ) / (double)this.bi;
//            double d3 = MathHelper.g(this.bm - (double)this.yaw);
//            this.yaw = (float)((double)this.yaw + d3 / (double)this.bi);
//            this.pitch = (float)((double)this.pitch + (this.bn - (double)this.pitch) / (double)this.bi);
//            --this.bi;
//            this.setPosition(d0, d1, d2);
//            this.setYawPitch(this.yaw, this.pitch);
//        } else if(!this.ct()) {
//            this.motX *= 0.98D;
//            this.motY *= 0.98D;
//            this.motZ *= 0.98D;
//        }
//
//        if(Math.abs(this.motX) < 0.003D) {
//            this.motX = 0.0D;
//        }
//
//        if(Math.abs(this.motY) < 0.003D) {
//            this.motY = 0.0D;
//        }
//
//        if(Math.abs(this.motZ) < 0.003D) {
//            this.motZ = 0.0D;
//        }
//
//        this.world.methodProfiler.a("ai");
//        SpigotTimings.timerEntityAI.startTiming();
//        if(this.cj()) {
//            this.be = false;
//            this.bf = 0.0F;
//            this.bg = 0.0F;
//            this.bh = 0.0F;
//        } else if(this.ct()) {
//            this.world.methodProfiler.a("newAi");
//            this.doTick();
//            this.world.methodProfiler.b();
//        }
//
//        SpigotTimings.timerEntityAI.stopTiming();
//        this.world.methodProfiler.b();
//        this.world.methodProfiler.a("jump");
//        if(this.be) {
//            if(this.isInWater()) {
//                this.cm();
//            } else if(this.ao()) {
//                this.cn();
//            } else if(this.onGround && this.bC == 0) {
//                this.cl();
//                this.bC = 10;
//            }
//        } else {
//            this.bC = 0;
//        }
//
//        this.world.methodProfiler.b();
//        this.world.methodProfiler.a("travel");
//        this.bf *= 0.98F;
//        this.bg *= 0.98F;
//        this.bh *= 0.9F;
//        this.r();
//        SpigotTimings.timerEntityAIMove.startTiming();
//        this.g(this.bf, this.bg);
//        SpigotTimings.timerEntityAIMove.stopTiming();
//        this.world.methodProfiler.b();
//        this.world.methodProfiler.a("push");
//        SpigotTimings.timerEntityAICollision.startTiming();
//        this.cs();
//        SpigotTimings.timerEntityAICollision.stopTiming();
//        this.world.methodProfiler.b();
//
//        //living
//        this.world.methodProfiler.a("looting");
//        if(!this.world.isClientSide && this.cR() && !this.aV && this.world.getGameRules().getBoolean("mobGriefing")) {
//            List list = this.world.a(EntityItem.class, this.getBoundingBox().grow(1.0D, 0.0D, 1.0D));
//            Iterator iterator = list.iterator();
//
//            while(iterator.hasNext()) {
//                EntityItem entityitem = (EntityItem)iterator.next();
//                if(!entityitem.dead && entityitem.getItemStack() != null && !entityitem.t()) {
//                    this.a(entityitem);
//                }
//            }
//        }
//
//        this.world.methodProfiler.b();

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
