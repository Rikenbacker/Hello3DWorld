package ru.dkuleshov.service;

/**
 * Created by dkuleshov3 on 13.06.2017.
 */
public class Ray
{
    private Point3D position = new Point3D(0f, 0f, 0f);
    private Point3D direction = new Point3D(0f, 0f, 0f);

    private final float Epsilon = 0.00001f;

    public Ray(float _x, float _y, float _z, float _dX, float _dY, float _dZ)
    {
        position.x = _x;
        position.y = _y;
        position.z = _z;
        direction.x = _dX;
        direction.y = _dY;
        direction.z = _dZ;

        bringDirectionToOne();
    }

    public Ray(Point3D _position, Point3D _direction)
    {
        position = new Point3D(_position);
        direction = new Point3D(_direction);

        bringDirectionToOne();
    }

    public Ray(Ray _ray)
    {
        position = _ray.getPoint();
        direction = _ray.getDirection();

    }

    private void bringDirectionToOne()
    {
        float length = 1f / direction.length();
        direction.mult(length);
    }

    public Point3D getPoint()
    {
        return new Point3D(position);
    }

    public Point3D getDirection()
    {
        return new Point3D(direction);
    }

    /**
     * Проверка совпадения лучей. Они должны лежать на одной линии
     * @param checkedRay СРавниваемый луч
     * @return true если лучи на одной линии
     */
    public boolean isMatch(Ray checkedRay)
    {
        // Направления должны быть кратны
        float tmp = checkedRay.getDirection().x / this.getDirection().x;
        float dirMult = this.getDirection().calcMult(tmp).distance(checkedRay.getDirection());
        float dirMultN = this.getDirection().calcMult(-tmp).distance(checkedRay.getDirection());

        if (dirMult > Epsilon * 10)
            if (dirMultN > Epsilon * 10)
                return false;

        //Линия проведеннвя между точками тоже должна быть кратна направлениям
        Point3D dem = checkedRay.getPoint().calcSub(this.getPoint());
        tmp = dem.x / this.getDirection().x;
        dirMult = this.getDirection().calcMult(tmp).distance(dem);
        dirMultN = this.getDirection().calcMult(-tmp).distance(dem);
        if (dirMult > Epsilon * 10)
            if (dirMultN > Epsilon * 10)
                return false;
        return true;
    }

    /**
     * Поиск пересечения двух прямых
     * @param second вторая прямая
     * @return Точка пересечения или null если не пересекаются
     */
    public Point3D calcIntersection(Ray second)
    {
        // Algorithm is ported from the C algorithm of
        // Paul Bourke at http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline3d/
        Point3D resultSegmentPoint1 = new Point3D(0f, 0f,0f);
        Point3D resultSegmentPoint2 = new Point3D(0f, 0f,0f);

        Point3D p1 = this.getPoint();
        Point3D p2 = p1.calcAdd(this.getDirection());
        Point3D p3 = second.getPoint();
        Point3D p4 = p3.calcAdd(second.getDirection());
        Point3D p13 = p1.calcSub(p3);
        Point3D p43 = p4.calcSub(p3);

        if (p4.distance(p3) < Epsilon)
            return null;

        Point3D p21 = p2.calcSub(p1);
        if (p2.distance(p1) < Epsilon)
            return null;

        float d1343 = p13.x * p43.x + p13.y * p43.y + p13.z * p43.z;
        float d4321 = p43.x * p21.x + p43.y * p21.y + p43.z * p21.z;
        float d1321 = p13.x * p21.x + p13.y * p21.y + p13.z * p21.z;
        float d4343 = p43.x * p43.x + p43.y * p43.y + p43.z * p43.z;
        float d2121 = p21.x * p21.x + p21.y * p21.y + p21.z * p21.z;

        float denom = d2121 * d4343 - d4321 * d4321;
        if (Math.abs(denom) < Epsilon)
            return null;

        float numer = d1343 * d4321 - d1321 * d4343;

        float mua = numer / denom;
        float mub = (d1343 + d4321 * (mua)) / d4343;

        resultSegmentPoint1.x = (float)(p1.x + mua * p21.x);
        resultSegmentPoint1.y = (float)(p1.y + mua * p21.y);
        resultSegmentPoint1.z = (float)(p1.z + mua * p21.z);
        resultSegmentPoint2.x = (float)(p3.x + mub * p43.x);
        resultSegmentPoint2.y = (float)(p3.y + mub * p43.y);
        resultSegmentPoint2.z = (float)(p3.z + mub * p43.z);

        if (resultSegmentPoint1.distance(resultSegmentPoint2) > Epsilon * 10)
            return null;

        return resultSegmentPoint1;
    }
}
