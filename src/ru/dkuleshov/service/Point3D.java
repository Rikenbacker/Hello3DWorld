package ru.dkuleshov.service;

import com.threed.jpct.SimpleVector;

/**
 * Created by dkuleshov3 on 13.06.2017.
 */
public class Point3D
{
    public float x;
    public float y;
    public float z;

    public Point3D(float _x, float _y, float _z)
    {
        x = _x;
        y = _y;
        z = _z;
    }

    public Point3D(SimpleVector _vector)
    {
        x = _vector.x;
        y = _vector.y;
        z = _vector.z;
    }

    public Point3D(Point3D _vector)
    {
        this.x = _vector.x;
        this.y = _vector.y;
        this.z = _vector.z;
    }

    public SimpleVector convertToSimpleVector()
    {
        return new SimpleVector(x, y, z);
    }

    public Point3D calcSub(Point3D sec)
    {
        return new Point3D(this.x - sec.x, this.y - sec.y, this.z - sec.z);
    }

    public float distance(Point3D var1)
    {
        float dX = -this.x + var1.x;
        float dY = -this.y + var1.y;
        float dZ = -this.z + var1.z;
        return (float)Math.sqrt((double)(dX * dX + dY * dY + dZ * dZ));
    }

    public void add(Point3D var1)
    {
        this.x += var1.x;
        this.y += var1.y;
        this.z += var1.z;
    }

    public Point3D calcAdd(Point3D var1)
    {
        return new Point3D(this.x + var1.x, this.y + var1.y, this.z + var1.z);
    }

    public float length()
    {
        return (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
    }

    public void div(float d)
    {
        x = x / d;
        y = y / d;
        z = z / d;
    }

    public Point3D calcDiv(float d)
    {
        return new Point3D(x / d, y / d, z / d);
    }


    public void mult(float d)
    {
        x *= d;
        y *= d;
        z *= d;
    }

    public Point3D calcMult(float d)
    {
        return new Point3D(x * d, y * d, z * d);
    }

    public boolean equals(Object var1)
    {
        if(!(var1 instanceof Point3D))
        {
            return false;
        } else
        {
            Point3D var2 = (Point3D)var1;
            return var2.x == this.x && var2.y == this.y && var2.z == this.z;
        }
    }

    public float calcAngle(Point3D var1)
    {
        return (float)Math.acos((double)this._calcAngle(var1));
    }

    private float _calcAngle(Point3D var1)
    {
        float var2 = this.x * var1.x + this.y * var1.y + this.z * var1.z;
        float var3 = (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
        float var4 = (float)Math.sqrt((double)(var1.x * var1.x + var1.y * var1.y + var1.z * var1.z));
        var2 /= var3 * var4;
        if(var2 < -1.0F) {
            var2 = -1.0F;
        }

        if(var2 > 1.0F) {
            var2 = 1.0F;
        }

        return var2;
    }
}
