package me.Fupery.FuppyMon.AI;

import net.minecraft.server.v1_10_R1.ControllerMove;
import net.minecraft.server.v1_10_R1.EntityInsentient;

/**
 * Created by aidenhatcher on 26/07/2016.
 */
public class WrappedMoveController extends ControllerMove {
    public WrappedMoveController(EntityInsentient entityInsentient) {
        super(entityInsentient);
    }
    public WrappedMoveController(EntityInsentient entityInsentient,  ControllerMove controller) {
        super(entityInsentient);
        controller.a(b, c, d, e);

    }
    public ControllerMove.Operation getCurrentOperation() {
        return h;
    }
}
