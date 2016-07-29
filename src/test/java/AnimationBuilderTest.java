import me.Fupery.FuppyMon.Animation.AnimationBuilder;
import me.Fupery.FuppyMon.Animation.EntityAnimation;
import me.Fupery.FuppyMon.Animation.Frame;
import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.Entity.Parts.StandPart;
import me.Fupery.FuppyMon.Pose.ArmourStandLimb;
import me.Fupery.FuppyMon.Pose.Pose;
import me.Fupery.FuppyMon.Pose.PoseSet;
import org.junit.Test;

public class AnimationBuilderTest {

    @Test
    public void AdvancedPoseTest() {
        StandPart part = new StandPart(60, 70, 80, 90f, 100f);

        AnimationBuilder builder = new AnimationBuilder(new EntityPart[]{part});
        AnimationBuilder.KeyFrame keyFrame = builder.newKeyFrame(1);

        PoseSet poseSet1 = keyFrame.getPoses();
        keyFrame.setRotation(part, 10, 15, 20);

        String string1 = poseSet1.getAdvancedPose(part).toString();

        keyFrame.setPartRotation(part, ArmourStandLimb.BODY, 5, 10, 20);
        keyFrame.setPartRotation(part, ArmourStandLimb.LEFT_ARM, 25, 30, 35);
        keyFrame.setPartRotation(part, ArmourStandLimb.RIGHT_ARM, 25, 30, 35);

        String string2 = poseSet1.getAdvancedPose(part).toString();

        System.out.println("PRE:"  + string1);
        System.out.println();
        System.out.println("POST:"  + string2);
        System.out.println();
        System.out.println("EQUAL:"  + string1.equals(string2));
        System.out.println();

        EntityAnimation animation = builder.buildAnimation();

        Frame frame = animation.next();
        PoseSet poseSet2 = frame.getPoses();

        String string3 = poseSet2.getAdvancedPose(part).toString();

        System.out.println("POST-POST:"  + string3);
        System.out.println();
        System.out.println("EQUAL:"  + string1.equals(string3));
        System.out.println();
        System.out.println("POST-PROCESSING EQUAL:"  + string2.equals(string3));
        System.out.println();
    }

    @Test
    public void advancedPoseSaveTest() {
        StandPart part = new StandPart(60, 70, 80, 90f, 100f);

        AnimationBuilder builder = new AnimationBuilder(new EntityPart[]{part});
        AnimationBuilder.KeyFrame keyFrame1 = builder.newKeyFrame(5);

        keyFrame1.setPartRotation(part, ArmourStandLimb.BODY, 0, 15, 30);

        AnimationBuilder.KeyFrame keyFrame2 = builder.newKeyFrame(5);
        keyFrame2.setPartRotation(part, ArmourStandLimb.BODY, 30, 45, 60);

        EntityAnimation animation = builder.buildAnimation();

        for (int i = 0; i < 10; i++) {
            Frame frame = animation.next();
            Pose pose = frame.getPoses().getPose(part.getId());
            System.out.println(pose.getClass());
        }

    }
}
