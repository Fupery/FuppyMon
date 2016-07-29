package me.Fupery.FuppyMon.Utils;

import me.Fupery.FuppyMon.Entity.API.DynamicEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.util.EulerAngle;

public class WrappedArmorStand {

    private ArmorStand stand;

    public ArmorStand spawn(Location location) {
        stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setCustomName(DynamicEntity.PART_TAG);
        stand.setCustomNameVisible(false);
        return stand;
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        if (getExists()) ((CraftEntity) stand).getHandle().setLocation(x, y, z, yaw, pitch);
    }

    public void setHeadPose(EulerAngle angle) {
        if (getExists()) stand.setHeadPose(angle);
    }

    public void setBodyPose(EulerAngle angle) {
        if (getExists()) stand.setBodyPose(angle);
    }

    public void setLeftArmPose(EulerAngle angle) {
        if (getExists()) stand.setLeftArmPose(angle);
    }

    public void setRightArmPose(EulerAngle angle) {
        if (getExists()) stand.setRightArmPose(angle);
    }

    public void setLeftLegPose(EulerAngle angle) {
        if (getExists()) stand.setLeftLegPose(angle);
    }

    public void setRightLegPose(EulerAngle angle) {
        if (getExists()) stand.setRightLegPose(angle);
    }

    private boolean getExists() {
        return stand != null && stand.isValid();
    }

    public void remove() {
        if (stand != null) stand.remove();
    }
}
