package me.Fupery.FuppyMon.Animation;

import me.Fupery.FuppyMon.Pose.PoseSet;

public class Frame {
    protected final PoseSet poses;

    Frame(PoseSet poses) {
        this.poses = poses;
    }

    public PoseSet getPoses() {
        return poses;
    }

    @Override
    protected Frame clone() {
        return new Frame(poses.clone());
    }
}
