package me.Fupery.FuppyMon.AI;

import net.minecraft.server.v1_10_R1.*;

/**
 * Causes the pet to follow their owner, or teleport if they get too far away.
 */
public class PathfinderGoalPetFollowOwner extends PathfinderGoal {
    private Pet pet;
    private EntityLiving owner;
    private World world;
    private double f;
    private NavigationAbstract navigation;
    private int h;
    private float b;
    private float minDistance;
    private float i;

    private float n = 40f;

    public PathfinderGoalPetFollowOwner(Pet pet, World world, NavigationAbstract nav,
                                        double var2, float minDistance, float b) {
        this.owner = null;
        this.pet = pet;
        this.world = world;
        this.f = var2;
        this.navigation = nav;
        this.minDistance = minDistance;
        this.b = b;
        this.a(3);
    }

    public boolean a() {
        owner = pet.getOwner();
        return owner != null && owner.isAlive()
                && !(owner instanceof EntityHuman && ((EntityHuman) owner).isSpectator())
                && this.pet.getDistanceBetween(owner) >= (double) (this.minDistance * this.minDistance);
    }

    public boolean b() {
        return !this.navigation.n()
                && this.pet.getDistanceBetween(this.owner) > (double) (this.b * this.b);
    }

    public void c() {
        this.h = 0;
        this.i = this.pet.addPath(PathType.WATER);
        this.pet.addPath(PathType.WATER, 0.0F);
    }

    public void d() {
        this.owner = null;
        this.navigation.o();
        this.pet.addPath(PathType.WATER, this.i);
    }

    private boolean a(BlockPosition var1) {
        IBlockData blockData = this.world.getType(var1);
        Block block = blockData.getBlock();
        return block == Blocks.AIR || !blockData.h();
    }

    public void e() {
        this.pet.getControllerLook().a(this.owner, 10.0F, n);
        if (--this.h > 0) {
            return;
        }
        this.h = 10;
        if (this.navigation.a(this.owner, this.f)) {
            return;
        }
        if (this.pet.getDistanceBetween(this.owner) >= 144.0D) {
            int ownerX = MathHelper.floor(this.owner.locX) - 2;
            int ownerZ = MathHelper.floor(this.owner.locZ) - 2;
            int ownerY = MathHelper.floor(this.owner.getBoundingBox().b);

            for (int x = 0; x <= 4; ++x) {
                for (int y = 0; y <= 4; ++y) {

                    if ((x < 1 || y < 1 || x > 3 || y > 3)
                            && this.world.getType(new BlockPosition(ownerX + x, ownerY - 1, ownerZ + y)).q()
                            && this.a(new BlockPosition(ownerX + x, ownerY, ownerZ + y))
                            && this.a(new BlockPosition(ownerX + x, ownerY + 1, ownerZ + y))) {

                        this.pet.setPositionRotation((double) ((float) (ownerX + x) + 0.5F),
                                (double) ownerY, (double) ((float) (ownerZ + y) + 0.5F), this.pet.getBodyYaw(),
                                this.pet.getBodyPitch());

                        this.navigation.o();
                        return;
                    }
                }
            }
        }
    }
}

