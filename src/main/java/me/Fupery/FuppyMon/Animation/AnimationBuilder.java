package me.Fupery.FuppyMon.Animation;

import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.Entity.Parts.StandPart;
import me.Fupery.FuppyMon.Pose.AdvancedPose;
import me.Fupery.FuppyMon.Pose.ArmourStandLimb;
import me.Fupery.FuppyMon.Pose.Pose;
import me.Fupery.FuppyMon.Pose.PoseSet;

import java.util.ArrayList;

/**
 * Builds an EntityAnimation object from keyframes referencing stand parts
 * EntityAnimations can be registered at a corresponding EntityFactory.
 */
public class AnimationBuilder {

    private final ArrayList<KeyFrame> timeline;
    private final EntityPart[] entityParts;
    private boolean loopEnd = true;

    public AnimationBuilder(EntityPart[] parts) {
        this.timeline = new ArrayList<>();
        entityParts = parts;
    }

    private static Frame[] concatenate(Frame[] a, Frame[] b) {
        int aLength = a.length;
        int bLength = b.length;
        Frame[] c = new Frame[aLength + bLength];
        System.arraycopy(a, 0, c, 0, aLength);
        System.arraycopy(b, 0, c, aLength, bLength);
        return c;
    }

    /**
     * @param loopEnd If true, the last frame is looped back to the first. If false, last frame's length is clipped.
     *                Defaults to true.
     */
    public void setLoopEnd(boolean loopEnd) {
        this.loopEnd = loopEnd;
    }

    /**
     * @return A compiled EntityAnimation, to feed into an EntityFactory
     */
    public EntityAnimation buildAnimation() {
        Frame[] frames = new Frame[0];
        if (timeline.size() == 0) {
            timeline.add(new KeyFrame(new PoseSet(entityParts), 1));
        }
        int endFrame = timeline.size() - 1;
        if (!loopEnd) {
            timeline.get(endFrame).length = 1;
        }
        for (int i = 0; i < endFrame; i++) {
            Frame[] nextTween = timeline.get(i).tween(timeline.get(i + 1));
            frames = concatenate(frames, nextTween);
        }
        if (loopEnd) {
            Frame[] tail = timeline.get(endFrame).tween(timeline.get(0));
            frames = concatenate(frames, tail);
        }
        return new EntityAnimation(frames);
    }

    public KeyFrame newKeyFrame(int length) {
        KeyFrame frame = new KeyFrame(new PoseSet(entityParts), length);
        timeline.add(frame);
        return frame;
    }

    /**
     * Specialized frame used to build arrays of frames
     */
    public class KeyFrame extends Frame {
        private int length;

        private KeyFrame(PoseSet poses, int length) {
            super(poses);
            this.length = length;
        }

        public KeyFrame setTranslation(double x, double y, double z) {
            for (EntityPart entityPart : entityParts) {
                setTranslation(entityPart, x, y, z);
            }
            return this;
        }

        public KeyFrame setTranslation(EntityPart part, double x, double y, double z) {
            Pose pose = poses.getPose(part);
            pose.setTranslation(x, y, z);
            poses.setPose(pose);
            return this;
        }

        public KeyFrame setRotation(EntityPart part, double x, double y, double z) {
            Pose pose = poses.getPose(part);
            pose.setRotation(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z));
            poses.setPose(pose);
            return this;
        }

        public KeyFrame setPartRotation(StandPart part, ArmourStandLimb limb, double x, double y, double z) {
            double rotX = Math.toRadians(x);
            double rotY = Math.toRadians(y);
            double rotZ = Math.toRadians(z);
            AdvancedPose pose = poses.getAdvancedPose(part);
            switch (limb) {
                case HEAD:
                    pose.setRotation(rotX, rotY, rotZ);
                    break;
                case BODY:
                    pose.setBodyRotation(rotX, rotY, rotZ);
                    break;
                case LEFT_ARM:
                    pose.setLeftArmRotation(rotX, rotY, rotZ);
                    break;
                case RIGHT_ARM:
                    pose.setRightArmRotation(rotX, rotY, rotZ);
                    break;
                case LEFT_LEG:
                    pose.setLeftLegRotation(rotX, rotY, rotZ);
                    break;
                case RIGHT_LEG:
                    pose.setRightLegRotation(rotX, rotY, rotZ);
                    break;
            }
            poses.setPose(pose);
            return this;
        }

        public KeyFrame setDirection(EntityPart part, float pitch, float yaw) {
            Pose pose = poses.getPose(part);
            pose.setDirection(pitch, yaw);
            poses.setPose(pose);
            return this;
        }

        /**
         * @param nextKeyFrame The next keyframe in the sequence
         * @return An interpolated array of frames between this keyframe and the next, inclusive of this frame
         */
        Frame[] tween(KeyFrame nextKeyFrame) {
            Frame[] frames = new Frame[length];
            if (length < 1) {
                return new Frame[]{this};
            } else if (length == 2) {
                return new Frame[]{this, nextKeyFrame};
            }
            //Find the distance between poses per frame for each part, between this keyframe and the next
            PoseSet displacement = new PoseSet(entityParts);
            for (EntityPart part : entityParts) {
                Pose pose1 = poses.getPose(part);
                Pose pose2 = nextKeyFrame.poses.getPose(part);

                if (!nextKeyFrame.poses.containsPart(part)) {
                    nextKeyFrame.poses.setPose(pose1);
                    displacement.setPose(new Pose(part));
                    continue;
                }
                displacement.setPose(pose2.clone().subtract(pose1).divideBy(length));
            }
            frames[0] = this;
            //Each part in each frame is incremented by the part displacement, calculated above
            for (int i = 1; i < length; i++) {
                frames[i] = new Frame(new PoseSet(entityParts));
                for (Pose dis : displacement) {
                    Pose prevPose = frames[i - 1].poses.getPose(dis.getPartId()).clone();
                    frames[i].poses.setPose(prevPose.add(dis));
                }
            }
            return frames;
        }
    }
}
