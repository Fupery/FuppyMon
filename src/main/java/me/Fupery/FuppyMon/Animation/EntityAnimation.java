package me.Fupery.FuppyMon.Animation;

import java.util.Iterator;

public class EntityAnimation implements Iterator<Frame> {
    private final Frame[] frames;
    private int currentFrame;

    EntityAnimation(Frame[] timeline) {
        frames = new Frame[timeline.length];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = timeline[i].clone();
        }
        currentFrame = 0;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Frames are final and cannot be overridden.");
    }

    @Override
    public boolean hasNext() {
        return frames != null && frames.length > 0;
    }

    @Override
    public Frame next() {
        if (!hasNext()) {
            return frames[currentFrame];
        }
        Frame frame = frames[currentFrame];

        if (currentFrame == frames.length - 1) {
            currentFrame = 0;
        } else {
            currentFrame++;
        }
        return frame;
    }

    @Override
    public EntityAnimation clone() {
        return new EntityAnimation(frames);
    }
}
