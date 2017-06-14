package ru.dkuleshov.service;

import com.threed.jpct.SimpleVector;

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
        public Point2D() {x = 0; y = 0;}
        public Point2D(SimpleVector _v) {x = _v.x; y = _v.z;}

        public float distance(Point2D s)
        {
            float var2 = -this.x + s.x;
            float var3 = -this.y + s.y;
            return (float)Math.sqrt((double)(var2 * var2 + var3 * var3));
        }
    }

    static float  det (float a, float b, float c, float d)
    {
        return a * d - b * c;
    }

    static float  det(float a11, float a12, float a13, float a21, float a22, float a23, float a31, float a32, float a33)
    {
        return a11 * a22 * a33 + a13 * a21 * a32 + a31 * a12 * a23 - a13 * a22 * a31 - a11 * a23 * a12 - a33 * a12 * a21;
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

    public static Point2D calc3DotBezierPoint(Point2D points[], float t)
    {
        Point2D ret = new Point2D();
        float t1 = 1f - t;

        ret.x = t1 * t1 *points[0].x + 2 * t * t1 * points[1].x + t * t * points[2].x;
        ret.y = t1 * t1 *points[0].y + 2 * t * t1 * points[1].y + t * t * points[2].y;

        return ret;
    }

    public static Point3D calc3DotBezierPoint(Point3D points[], float t)
    {
        Point3D ret = new Point3D(0f, 0f, 0f);
        float t1 = 1f - t;

        ret.x = t1 * t1 *points[0].x + 2 * t * t1 * points[1].x + t * t * points[2].x;
        ret.y = t1 * t1 *points[0].y + 2 * t * t1 * points[1].y + t * t * points[2].y;
        ret.z = t1 * t1 *points[0].z + 2 * t * t1 * points[1].z + t * t * points[2].z;

        return ret;
    }

    public static float calc3DotBezierLength(Point2D points[], int accuracy)
    {
        Point2D prev = points[0];
        float ret = 0;
        for (int i = 1; i <= accuracy; i++)
        {
            Point2D next = calc3DotBezierPoint(points, (float)i / (float)accuracy);
            ret += prev.distance(next);
            prev = next;
        }

        return ret;
    }

    public static float calc3DotBezierLength(Point3D points[], int accuracy)
    {
        Point3D prev = points[0];
        float ret = 0;
        for (int i = 1; i <= accuracy; i++)
        {
            Point3D next = calc3DotBezierPoint(points, (float)i / (float)accuracy);
            ret += prev.distance(next);
            prev = next;
        }

        return ret;
    }

    /**
     * Проверка принадлежности точки checkedPoint к линии start-direction
     * @param start точка начала прямой
     * @param direction направление прямой
     * @param checkedPoint точка которую необходимо проеверить
     * @param checkedDirection направление
     * @return true если все три точки на одной прямой
     */
    public static boolean isPointOnLine(Point2D start, Point2D direction, Point2D checkedPoint, Point2D checkedDirection)
    {
        boolean ret = false;

        float x;
        if (start.x + direction.x == 0)
            x = 0;
        else
            x = checkedPoint.x / (start.x + direction.x);

        float x2;
        if (checkedDirection.x == 0)
            x2 = 0;
        else
            x2 = direction.x / checkedDirection.x;

        if ((start.y + direction.y) * x == checkedPoint.y && direction.y == checkedDirection.y * x2)
            ret = true;

        return ret;
    }
}
