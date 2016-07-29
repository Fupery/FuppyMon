package me.Fupery.FuppyMon.Entity.Parts;

import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import me.Fupery.FuppyMon.Pose.AdvancedPose;
import me.Fupery.FuppyMon.Pose.Pose;
import me.Fupery.FuppyMon.Utils.EntityPosition;
import me.Fupery.FuppyMon.Utils.MathUtils;
import me.Fupery.FuppyMon.Utils.WrappedArmorStand;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class StandPart extends EntityPart {

    private WrappedArmorStand stand = new WrappedArmorStand();
    private boolean arms;
    private boolean visible;
    private boolean small;
    private ItemStack head;
    private ItemStack chest;
    private ItemStack leggings;
    private ItemStack boots;

    public StandPart(double offsetX, double offsetY, double offsetZ, float offsetYaw, float offsetPitch) {
        super(offsetX, offsetY, offsetZ, offsetYaw, offsetPitch);
        arms = false;
        visible = false;
        small = false;
        head = null;
        chest = null;
        leggings = null;
        boots = null;
    }

    protected StandPart(EntityPart part) {
        super(part);
    }

    @Override
    public void updatePosition(DynamicEntity parent, Pose pose) {
        EntityPosition parentPos = parent.getPosition();
        float yaw = this.offsetYaw + pose.getYaw();
        float pitch = this.offsetPitch + pose.getPitch();
        boolean trackHead = hasHead() || parentPos.isInMotion() || parentPos.headIsHyperRotated();

        float yawRotation = trackHead ? parentPos.getHeadYaw() : parentPos.getBodyYaw();
        yaw += (trackHead) ? parentPos.getHeadYaw() : parentPos.getBodyYaw();
        pitch += (trackHead) ? parentPos.getHeadPitch() : parentPos.getBodyYaw();

        double[] pt = MathUtils.rotatePointAroundAxis(offsetX + pose.getX(), offsetZ + pose.getZ(), yawRotation);

        double x = parentPos.getX() + pt[0];
        double y = parentPos.getY() + this.offsetY + pose.getY();
        double z = parentPos.getZ() + pt[1];
        double adjustedRotX = pose.getRotX();

        if (hasHead()) adjustedRotX += Math.toRadians(pitch);

        stand.setLocation(x, y, z, yaw, pitch);
        stand.setHeadPose(new EulerAngle(adjustedRotX, pose.getRotY(), pose.getRotZ()));

        if (pose instanceof AdvancedPose) {
            AdvancedPose adv = (AdvancedPose) pose;
            adv.applyTo(stand);
        }
    }

    private boolean hasHead() {
        return head != null;
    }

    @Override
    public void spawn(DynamicEntity parent, Location location) {
        Location offset = new Location(location.getWorld(), offsetX, offsetY, offsetZ, offsetYaw, offsetPitch);
        ArmorStand standInstance = this.stand.spawn(location.clone().add(offset));
        standInstance.setBasePlate(false);
        standInstance.setGravity(false);
        standInstance.setVisible(visible);
        standInstance.setArms(arms);
        standInstance.setSmall(small);
        if (head != null) standInstance.setHelmet(head);
        if (chest != null) standInstance.setChestplate(chest);
        if (leggings != null) standInstance.setLeggings(leggings);
        if (boots != null) standInstance.setBoots(boots);
    }

    @Override
    public void remove() {
        stand.remove();
    }

    @Override
    public EntityPart clone() {
        StandPart part = new StandPart(this);
        part.head = head;
        part.chest = chest;
        part.leggings = leggings;
        part.boots = boots;
        part.stand = new WrappedArmorStand();
        part.arms = arms;
        part.visible = visible;
        part.small = small;
        part.head = head;
        return part;
    }

    public StandPart setArms(boolean arms) {
        this.arms = arms;
        return this;
    }

    public StandPart setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public StandPart setSmall(boolean small) {
        this.small = small;
        return this;
    }

    public void setHead(ItemStack head) {
        this.head = head;
    }

    public void setChest(ItemStack chest) {
        this.chest = chest;
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }
}
