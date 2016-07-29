package me.Fupery.FuppyMon.Animation;

import java.util.HashMap;

/**
 * Stores animations for different DynamicEntity actions
 */
public class AnimationProtocol {

    private HashMap<EntityState, EntityAnimation> protocol;

    public AnimationProtocol(HashMap<EntityState, EntityAnimation> protocol) {
        this.protocol = protocol;
    }

    public EntityAnimation getAnimationFor(EntityState action) {
        return (action != null && protocol.containsKey(action)) ? protocol.get(action) : getDefaultAnimation();
    }

    private EntityAnimation getDefaultAnimation() {
        return protocol.get(EntityState.IDLE);
    }

    @Override
    public AnimationProtocol clone() {
        HashMap<EntityState, EntityAnimation> clonedProtocol = new HashMap<>();
        for (EntityState state : protocol.keySet()) {
            clonedProtocol.put(state, protocol.get(state).clone());
        }
        return new AnimationProtocol(clonedProtocol);
    }

}
