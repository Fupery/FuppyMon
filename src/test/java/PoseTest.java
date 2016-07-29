import me.Fupery.FuppyMon.Pose.AdvancedPose;
import me.Fupery.FuppyMon.Pose.Pose;
import org.junit.Test;

public class PoseTest {

    @Test
    public void PoseCloneTest() {
        Pose pose = new Pose(1);
        pose.setRotation(120, 42, 21);
        pose.setDirection(12f, 35f);
        pose.setTranslation(12555, 21, 231);
        String check1 = pose.toString();

        Pose clonedPose = pose.clone();
        String check2 = clonedPose.toString();

        System.out.println("PRE-CLONE: " + check1);
        System.out.println("POST-CLONE: " + check2);
        System.out.println("EQUAL-CHECK: " + check1.equals(check2));
        System.out.println();
    }

    @Test
    public void AdvancedPoseCloneTest() {
        AdvancedPose pose = new AdvancedPose(1);
        pose.setBodyRotation(53, 26, 12);
        pose.setLeftArmRotation(124, 235.2, 12);
        pose.setRightArmRotation(124, 235.2, 12);
        pose.setLeftLegRotation(235, 325, 32);
        pose.setRightLegRotation(124, 235.2, 12);
        pose.setRotation(20, 30, 40);
        String check1 = pose.toString();

        AdvancedPose clonedPose = pose.clone();
        String check2 = clonedPose.toString();

        System.out.println("PRE-CLONE: " + check1);
        System.out.println("POST-CLONE: " + check2);
        System.out.println("EQUAL-CHECK: " + check1.equals(check2));
        System.out.println();
    }
}
