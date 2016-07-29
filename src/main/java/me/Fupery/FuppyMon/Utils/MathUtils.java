package me.Fupery.FuppyMon.Utils;

import java.awt.geom.AffineTransform;

public class MathUtils {

    public static double[] rotatePointAroundAxis(double pointX, double pointZ, float axisYaw) {
        double[] pt = {pointX, pointZ};
        AffineTransform.getRotateInstance(Math.toRadians(axisYaw), 0, 0).transform(pt, 0, pt, 0, 1);
        return pt;
    }
}
