package ru.dkuleshov.service;

import com.threed.jpct.SimpleVector;
import javafx.util.Pair;
import org.lwjgl.util.Point;

/**
 * Created by dkuleshov3 on 09.06.2017.
 */
public class SimpleMath
{
    public static class Point2D
    {
        public float x;
        public float y;
        public Point2D(float _x, float _y) {x = _x; y = _y;}
        public Point2D(SimpleVector _v) {x = _v.x; y = _v.z;}
    }

    static float  det (float a, float b, float c, float d)
    {
        return a * d - b * c;
    }

    public static Point2D calcIntersection (Point2D a, Point2D b, Point2D c, Point2D d)
    {
        float A1 = a.y-b.y;
        float B1 = b.x-a.x;
        float C1 = -A1*a.x - B1*a.y;
        float A2 = c.y-d.y;
        float B2 = d.x-c.x;
        float C2 = -A2*c.x - B2*c.y;
        float zn = det (A1, B1, A2, B2);
        if (zn != 0)
        {
            float x = - det (C1, B1, C2, B2) * 1f / zn;
            float y = - det (A1, C1, A2, C2) * 1f / zn;
            return new Point2D(x, y);
        }
        return null;
    }
}
