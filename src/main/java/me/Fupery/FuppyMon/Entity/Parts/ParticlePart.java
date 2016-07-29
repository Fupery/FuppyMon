package me.Fupery.FuppyMon.Entity.Parts;

import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import me.Fupery.FuppyMon.Pose.Pose;
import me.Fupery.FuppyMon.Utils.EntityPosition;
import me.Fupery.FuppyMon.Utils.MathUtils;
import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ParticlePart extends EntityPart {

    private EnumParticle particle;
    private float oX, oY, oZ;
    private int speed, amount;

    public ParticlePart(double x, double y, double z) {
        super(x, y, z, 0, 0);
    }

    private ParticlePart(EntityPart part) {
        super(part);
    }

    public ParticlePart setParticle(EnumParticle particle, float offsetX, float offsetY, float offsetZ,
                                    int speed, int amount) {
        this.particle = particle;
        this.oX = offsetX;
        this.oY = offsetY;
        this.oZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        return this;
    }

    @Override
    public void updatePosition(DynamicEntity parent, Pose pose) {
        EntityPosition parentPos = parent.getPosition();
        double[] pt
                = MathUtils.rotatePointAroundAxis(offsetX + pose.getX(), offsetZ + pose.getZ(), parentPos.getBodyYaw());

        double x = parentPos.getX() + pt[0];
        double y = parentPos.getY() + this.offsetY + pose.getY();
        double z = parentPos.getZ() + pt[1];

        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(
                particle, true, (float) x, (float) y, (float) z, oX, oY, oZ, speed, amount, null);
        for (Player player : parent.getViewingPlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
        }
    }

    @Override
    public void spawn(DynamicEntity parent, Location location) {
    }

    @Override
    public void remove() {
    }

    @Override
    public EntityPart clone() {
        ParticlePart clone = new ParticlePart(this);
        clone.setParticle(particle, oX, oY, oZ, speed, amount);
        return clone;
    }
}
