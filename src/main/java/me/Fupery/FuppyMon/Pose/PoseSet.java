package me.Fupery.FuppyMon.Pose;

import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.Entity.Parts.StandPart;

import java.util.Iterator;

public class PoseSet implements Iterable<Pose> {
    private Pose[] poses;

    public PoseSet(EntityPart[] parts) {
        poses = new Pose[parts.length];
        for (int i = 0; i < parts.length; i++) {
            EntityPart part = parts[i];
            if (part instanceof StandPart) {
                poses[i] = new AdvancedPose(parts[i]);
            } else {
                poses[i] = new Pose(parts[i]);
            }
        }
    }

    private PoseSet(int size) {
        poses = new Pose[size];
    }

    public Pose getPose(int entityPartId) {
        Pose pose = findPoseFor(entityPartId);
        return pose != null ? pose : new Pose(entityPartId);
    }

    private Pose findPoseFor(int partId) {
        for (int i = 0; i < poses.length; i++) {
            Pose pose = poses[i];
            if (pose != null && pose.getPartId() == partId) return pose;
        }
        return null;
    }

    public Pose getPose(EntityPart part) {
        Pose pose = getPose(part.getId());
        return part instanceof StandPart ? AdvancedPose.from(pose) : pose; // TODO: 27/07/2016  
    }

    public AdvancedPose getAdvancedPose(StandPart part) {
        Pose pose = getPose(part.getId());
        return pose instanceof AdvancedPose ? ((AdvancedPose) pose) : AdvancedPose.from(pose);
    }


    public boolean containsPart(int partId) {
        return findPoseFor(partId) != null;
    }

    public boolean containsPart(EntityPart part) {
        return containsPart(part.getId());
    }

    public void setPose(Pose pose) {
        setPose(pose, false);
    }

    /**
     * @param pose    The pose to define in this list.
     * @param combine If true, any value already assigned to this part is added to the pose supplied.
     */
    public void setPose(Pose pose, boolean combine) {
        for (int i = 0; i < poses.length; i++) {
            if (poses[i] != null && poses[i].getPartId() == pose.getPartId()) {
                Pose currentPose = poses[i];
                poses[i] = combine ? currentPose.add(pose) : pose;
            }
        }
    }

    public int size() {
        return poses.length;
    }

    @Override
    public Iterator<Pose> iterator() {
        return new Iterator<Pose>() {
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < poses.length;
            }

            @Override
            public Pose next() {
                return (hasNext()) ? poses[count++] : null;
            }
        };
    }

    @Override
    public PoseSet clone() {
        PoseSet clone = new PoseSet(poses.length);
        for (int i = 0; i < poses.length; i++) {
            if (poses[i] != null) {
                clone.poses[i] = poses[i].clone();
            }
        }
        return clone;
    }
}
