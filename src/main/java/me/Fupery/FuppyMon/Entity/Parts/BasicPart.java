package me.Fupery.FuppyMon.Entity.Parts;

import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import me.Fupery.FuppyMon.Pose.Pose;
import me.Fupery.FuppyMon.Utils.EntityPosition;
import me.Fupery.FuppyMon.Utils.MathUtils;
import me.Fupery.FuppyMon.Utils.WrappedArmorStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;

/**
 * A basic and flexible EntityPart implementation; spawns a single invisible armour stand,
 * with a head that can be set and rotated. The head can be set to any block or a playerhead.
 */
public class BasicPart extends EntityPart {
    private final WrappedArmorStand stand;
    private ItemStack item;
    private boolean small;
    private boolean head = false;

    public BasicPart(double x, double y, double z, float yaw, float pitch) {
        super(x, y, z, yaw, pitch);
        stand = new WrappedArmorStand();
    }

    public BasicPart(EntityPart part) {
        super(part);
        stand = new WrappedArmorStand();
    }

    @Override
    public void updatePosition(DynamicEntity parent, Pose pose) {
        EntityPosition parentPos = parent.getPosition();
        float yaw = this.offsetYaw + pose.getYaw();
        float pitch = this.offsetPitch + pose.getPitch();
        boolean trackHead = isHead() || parentPos.isInMotion() || parentPos.headIsHyperRotated();

        float yawRotation = trackHead ? parentPos.getHeadYaw() : parentPos.getBodyYaw();
        yaw += (trackHead) ? parentPos.getHeadYaw() : parentPos.getBodyYaw();
        pitch += (trackHead) ? parentPos.getHeadPitch() : parentPos.getBodyYaw();

        double[] pt = MathUtils.rotatePointAroundAxis(offsetX + pose.getX(), offsetZ + pose.getZ(), yawRotation);

        double x = parentPos.getX() + pt[0];
        double y = parentPos.getY() + this.offsetY + pose.getY();
        double z = parentPos.getZ() + pt[1];
        double adjustedRotX = pose.getRotX();

        if (isHead()) adjustedRotX += Math.toRadians(pitch);

        stand.setLocation(x, y, z, yaw, pitch);
        stand.setHeadPose(new EulerAngle(adjustedRotX, pose.getRotY(), pose.getRotZ()));
    }

    @Override
    public void remove() {
        stand.remove();
    }

    public BasicPart setSmall(boolean small) {
        this.small = small;
        return this;
    }

    public BasicPart setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public BasicPart setItem(Material material) {
        this.item = new ItemStack(material);
        return this;
    }

    public BasicPart setItem(Material material, short durability) {
        this.item = new ItemStack(material, 1, durability);
        return this;
    }

    public BasicPart setPlayerHead(String playerName) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(playerName);
        head.setItemMeta(meta);
        item = head;
        this.head = true;
        return this;
    }

    @Override
    public void spawn(DynamicEntity parent, Location location) {
        Location offset = new Location(location.getWorld(), offsetX, offsetY, offsetZ, offsetYaw, offsetPitch);
        ArmorStand standInstance = this.stand.spawn(location.clone().add(offset));
        standInstance.setBasePlate(false);
        standInstance.setGravity(false);
        standInstance.setVisible(false);
        standInstance.setHelmet(item);
        standInstance.setSmall(small);
    }

    @Override
    public BasicPart clone() {
        BasicPart part = new BasicPart(this);
        if (item != null) part.item = item.clone();
        part.small = small;
        part.head = head;
        return part;
    }

    private boolean isHead() {
        return head;
    }
}
