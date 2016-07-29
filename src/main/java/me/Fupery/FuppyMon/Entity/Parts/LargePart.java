package me.Fupery.FuppyMon.Entity.Parts;

import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import me.Fupery.FuppyMon.Entity.Generic.LargeBlock;
import me.Fupery.FuppyMon.Pose.Pose;
import me.Fupery.FuppyMon.Utils.EntityPosition;
import me.Fupery.FuppyMon.Utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * A large falling block entity. Cannot be rotated.
 */
public class LargePart extends EntityPart {

    private LargeBlock block;
    private Material material;

    public LargePart(Material material, double offsetX, double offsetY, double offsetZ) {
        super(offsetX, offsetY, offsetZ, 0, 0);
        this.material = material;
    }

    private LargePart(EntityPart part) {
        super(part);
    }

    @Override
    public void updatePosition(DynamicEntity parent, Pose pose) {
        EntityPosition parentPos = parent.getPosition();
        double[] pt = MathUtils.rotatePointAroundAxis(
                offsetX + pose.getX(), offsetZ + pose.getZ(), parentPos.getBodyYaw());

        double x = parentPos.getX() + pt[0];
        double y = parentPos.getY() + this.offsetY + pose.getY();
        double z = parentPos.getZ() + pt[1];

        block.setPosition(x, y, z);
    }

    @Override
    public void spawn(DynamicEntity parent, Location loc) {
        Location offset = new Location(parent.getWorld(), offsetX, offsetY, offsetZ, offsetYaw, offsetPitch);
        Location spawnLoc = loc.clone().add(offset);
        block = LargeBlock.createBlock(material, spawnLoc);
        block.spawn(spawnLoc);
    }

    @Override
    public void remove() {
        block.remove();
    }

    @Override
    public LargePart clone() {
        LargePart clone = new LargePart(this);
        clone.material = material;
        return clone;
    }
}
