package com.example.cosine.myapplication.maze;

import static java.lang.Math.abs;

public final class MazeUtil {
    private MazeUtil() {}

    // collision algorithm derived from https://stackoverflow.com/a/402010
    public static boolean circleRectCollision(float circleXCentre, float circleYCentre, float circleRad,
                                       float rectXCentre, float rectYCentre, float rectHalfWidth, float rectHalfHeight) {
        float dx = abs(circleXCentre - rectXCentre);
        float dy = abs(circleYCentre - rectYCentre);

        if ((dx > circleRad + rectHalfWidth) || (dy >  circleRad + rectHalfHeight)) {
            return false;
        }
        if ((dx <= rectHalfWidth) || (dy <= rectHalfHeight)) {
            return true;
        }

        float d1 = dx - rectHalfWidth;
        float d2 = dy - rectHalfHeight;
        return (d1 * d1) + (d2 * d2) <= (circleRad * circleRad);
    }
}
