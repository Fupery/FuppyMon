package me.Fupery.FuppyMon.Entity.Generic;

import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class LargeBlock extends EntityFallingBlock {
    private IBlockData block;
    private boolean f;

    private ArmorStand stand;

    public LargeBlock(World world) {
        super(world);
    }

    public LargeBlock(World world, double x, double y, double z, IBlockData iblockdata) {
        super(world, x, y, z, iblockdata);
    }

    public static LargeBlock createBlock(org.bukkit.Material material, Location loc) {
        World world = ((CraftWorld) loc.getWorld()).getHandle();
        IBlockData data = Block.getById(material.getId()).fromLegacyData((byte) 0);
        return new LargeBlock(world, loc.getX(), loc.getY(), loc.getZ(), data);
    }

    @Override
    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        setYawPitch(yaw, pitch);
        ((CraftArmorStand) stand).getHandle().setLocation(x, y, z, yaw, pitch);
    }

    public void spawn(Location loc) {
        stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setInvulnerable(true);
        stand.setPassenger(getBukkitEntity());
        world.addEntity(this, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    public void remove() {
        getBukkitEntity().remove();
        stand.remove();
    }

    @Override
    public void aw() {//get riding
        fallDistance = 0.0F;
        motX = 0.0D;
        motY = 0.0D;
        motZ = 0.0D;
        m();
    }

    @Override
    protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
        fallDistance = 0.0F;
        //prevent fall
    }

    public void m() {
        //prevent drop
    }

    public void e(float f, float f1) {
        //prevent damage
    }

    protected void b(NBTTagCompound nbttagcompound) {
        //prevent nbt write
    }

    protected void a(NBTTagCompound nbttagcompound) {
        //prevent reload
        this.die();
    }

    public void a(boolean flag) {
        this.hurtEntities = flag;
    }

    public void appendEntityCrashDetails(CrashReportSystemDetails crashreportsystemdetails) {
        super.appendEntityCrashDetails(crashreportsystemdetails);
        if (this.block != null) {
            Block block = this.block.getBlock();
            crashreportsystemdetails.a("Immitating block ID", Block.getId(block));
            crashreportsystemdetails.a("Immitating block data", block.toLegacyData(this.block));
        }
    }

    public ArmorStand getStand() {
        return stand;
    }
}