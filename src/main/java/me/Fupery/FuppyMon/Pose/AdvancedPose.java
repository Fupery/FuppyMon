package me.Fupery.FuppyMon.Pose;

import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.Utils.WrappedArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

/**
 * Created by aidenhatcher on 26/07/2016.
 */
public class AdvancedPose extends Pose {

    private PartPose body = new PartPose(), leftArm = new PartPose(), rightArm = new PartPose(),
            leftLeg = new PartPose(), rightLeg = new PartPose();

    public AdvancedPose(int partId) {
        super(partId);
    }

    public AdvancedPose(EntityPart part) {
        super(part);
    }

    public static AdvancedPose from(Pose pose) {
        AdvancedPose result = new AdvancedPose(pose.partId);
        result.setTranslation(pose.x, pose.y, pose.z);
        result.setRotation(pose.rotX, pose.rotY, pose.rotZ);
        result.setDirection(pose.yaw, pose.pitch);
        return result;
    }

    public AdvancedPose add(AdvancedPose pose) {
        AdvancedPose result = new AdvancedPose(getPartId());
        result.setTranslation(x + pose.x, y + pose.y, z + pose.z);
        result.setRotation(rotX + pose.rotX, rotY + pose.rotY, rotZ + pose.rotZ);
        result.setDirection(yaw + pose.yaw, pitch + pose.pitch);
        result.body = body.add(pose.body);
        result.leftArm = leftArm.add(pose.leftArm);
        result.rightArm = rightArm.add(pose.rightArm);
        result.rightLeg = rightLeg.add(pose.rightLeg);
        result.leftLeg = leftLeg.add(pose.leftLeg);
        return result;
    }

    public AdvancedPose subtract(AdvancedPose pose) {
        AdvancedPose result = new AdvancedPose(partId);
        result.setTranslation(x - pose.x, y - pose.y, z - pose.z);
        result.setRotation(rotX - pose.rotX, rotY - pose.rotY, rotZ - pose.rotZ);
        result.setDirection(yaw - pose.yaw, pitch - pose.pitch);
        result.body = body.subtract(pose.body);
        result.leftArm = leftArm.subtract(pose.leftArm);
        result.rightArm = rightArm.subtract(pose.rightArm);
        result.rightLeg = rightLeg.subtract(pose.rightLeg);
        result.leftLeg = leftLeg.subtract(pose.leftLeg);
        return result;
    }

    public AdvancedPose divideBy(int number) {
        AdvancedPose result = new AdvancedPose(partId);
        if (number == 0) {
            number = 1;
        }
        result.setTranslation(x / number, y / number, z / number);
        result.setRotation(rotX / number, rotY / number, rotZ / number);
        result.setDirection(yaw / number, pitch / number);
        result.body = body.divideBy(number);
        result.leftArm = leftArm.divideBy(number);
        result.rightArm = rightArm.divideBy(number);
        result.rightLeg = rightLeg.divideBy(number);
        result.leftLeg = leftLeg.divideBy(number);
        return result;
    }

    public void setBodyRotation(double x, double y, double z) {
        body.setRotation(x, y, z);
    }
    public void setLeftArmRotation(double x, double y, double z) {
        leftArm.setRotation(x, y, z);
    }
    public void setRightArmRotation(double x, double y, double z) {
        rightArm.setRotation(x, y, z);
    }
    public void setLeftLegRotation(double x, double y, double z) {
        leftLeg.setRotation(x, y, z);
    }
    public void setRightLegRotation(double x, double y, double z) {
        rightLeg.setRotation(x, y, z);
    }

    private class PartPose {
        private double rotX, rotY, rotZ;

        public PartPose() {
            this(0, 0, 0);
        }

        public PartPose(double rotX, double rotY, double rotZ) {
            this.rotX = rotX;
            this.rotY = rotY;
            this.rotZ = rotZ;
        }

        void setRotation(double rotX, double rotY, double rotZ) {
            this.rotX = rotX;
            this.rotY = rotY;
            this.rotZ = rotZ;
        }

        public PartPose add(PartPose pose) {
            return new PartPose(rotX + pose.rotX, rotY + pose.rotY, rotZ + pose.rotZ);
        }

        public PartPose subtract(PartPose PartPose) {
            return new PartPose(rotX - PartPose.rotX, rotY - PartPose.rotY, rotZ - PartPose.rotZ);
        }

        public PartPose divideBy(int number) {
            if (number == 0) {
                number = 1;
            }
            return new PartPose(rotX / number, rotY / number, rotZ / number);
        }
    }
    public void applyTo(WrappedArmorStand stand) {
        stand.setBodyPose(new EulerAngle(body.rotX, body.rotY, body.rotZ));
        stand.setLeftArmPose(new EulerAngle(leftArm.rotX, leftArm.rotY, leftArm.rotZ));
        stand.setRightArmPose(new EulerAngle(rightArm.rotX, rightArm.rotY, rightArm.rotZ));
        stand.setLeftLegPose(new EulerAngle(leftLeg.rotX, leftLeg.rotY, leftLeg.rotZ));
        stand.setRightLegPose(new EulerAngle(rightLeg.rotX, rightLeg.rotY, rightLeg.rotZ));
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s : %s, %s, %s : %s, %s {BODY: %s, %s, %s}, {LEFT_ARM: %s, %s, %s}, " +
                        "{RIGHT_ARM: %s, %s, %s}, {LEFT_LEG: %s, %s, %s}, {RIGHT_LEG: %s, %s, %s}}",
                x, y, z, rotX, rotY, rotZ, yaw, pitch, body.rotX, body.rotY, body.rotZ,
                leftArm.rotX, leftArm.rotY, leftArm.rotZ, rightArm.rotX, rightArm.rotY, rightArm.rotZ,
                leftLeg.rotX, leftLeg.rotY, leftLeg.rotZ, rightLeg.rotX, rightLeg.rotY, rightLeg.rotZ);

        //whew.
    }

    @Override
    public AdvancedPose clone() {
        AdvancedPose result = new AdvancedPose(partId);
        result.setTranslation(x, y, z);
        result.setRotation(rotX, rotY, rotZ);
        result.setDirection(yaw, pitch);
        result.setBodyRotation(body.rotX, body.rotY, body.rotZ);
        result.setLeftArmRotation(leftArm.rotX, leftArm.rotY, leftArm.rotZ);
        result.setRightArmRotation(rightArm.rotX, rightArm.rotY, rightArm.rotZ);
        result.setLeftLegRotation(leftLeg.rotX, leftLeg.rotY, leftLeg.rotZ);
        result.setRightLegRotation(rightLeg.rotX, rightLeg.rotY, rightLeg.rotZ);

        return result;
    }
}
