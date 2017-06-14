package ru.dkuleshov.gameObject;

import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import ru.dkuleshov.C3DObject.*;
import ru.dkuleshov.service.*;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class RailLine
{
    /** Начальное положение пути*/
    private Ray rayA = null;
    /** Конечное положение пути*/
    private Ray rayB = null;
    /** Тип построения пути*/
    private LineType type = LineType.Line;

    Point3D additionalPointOne = null;

    RailLineConnector connectorA = null;
    RailLineConnector connectorB = null;

    private float length;

    private World world = null;

    public enum Direction {Inside, Outside}
    public enum RealDirection {FromAToB, FromBToA}
    private enum LineType {Line, BezierCurve}

    public RailLine(Ray _start, Ray _end, World _world) throws Exception
    {
        rayA = _start;
        rayB = _end;
        connectorA = new RailLineConnector(this, rayA);
        connectorB = new RailLineConnector(this, rayB);
        world = _world;

        calcType();
    }

    public RailLine(RailLineConnector _start, Ray _end, World _world) throws Exception
    {
        rayA = _start.getRay();
        rayB = _end;
        connectorA = new RailLineConnector(this, _start);
        _start.setLink(connectorA);
        connectorB = new RailLineConnector(this, rayB);
        world = _world;

        calcType();
    }

    public RailLine(Point3D _start, Point3D _end, World _world) throws Exception
    {
        rayA = new Ray(_start, _start.calcSub(_end));
        rayB = new Ray(_end, _end.calcSub(_start));
        connectorA = new RailLineConnector(this, rayA);
        connectorB = new RailLineConnector(this, rayB);
        world = _world;

        calcType();
    }

    public RailLine(RailLineConnector _start, Point3D _end, World _world) throws Exception
    {
        rayA = _start.getRay();
        rayB = new Ray(_end, _end.calcSub(rayA.getPoint()));
        connectorA = new RailLineConnector(this, _start);
        _start.setLink(connectorA);
        connectorB = new RailLineConnector(this, rayB);
        world = _world;

        calcType();
    }

    public RailLine(RailLineConnector _start, RailLineConnector _end, World _world) throws Exception
    {
        rayA = _start.getRay();
        rayB = _end.getRay();
        connectorA = new RailLineConnector(this, _start);
        _start.setLink(connectorA);
        connectorB = new RailLineConnector(this, _end);
        _end.setLink(connectorB);
        world = _world;

        calcType();
    }

    private void calcType() throws Exception
    {
        if (rayA.isMatch(rayB))
        {
            type = LineType.Line;
            length = rayA.getPoint().distance(rayB.getPoint());
        } else
            prepareBezier(rayA, rayB);
    }

    private void prepareBezier(Ray start, Ray end) throws Exception
    {
        /*
        1. Подсчитать длину кривой безье по трем точкам (по двум входным векторам)
        2. Если построить по двум векторам не выходит, разбить на необходимое количество кусков (Или ещё лучше увеличить количество точек кривой безье)
        3. Построить из кусков.
        */
        Point3D tmpPoint = start.calcIntersection(end);
        if (tmpPoint == null)
            throw new Exception("Lines not intersected");

        type = LineType.BezierCurve;

        Point3D[] tmpArr = {start.getPoint(), tmpPoint, end.getPoint()};
        length = SimpleMath.calc3DotBezierLength(tmpArr, 1000);
        additionalPointOne = tmpPoint;

        // 2.
        // Для теста один кусок

    }

    public RailLineConnector getConnectorA()
    {
        return connectorA;
    }

    public RailLineConnector getConnectorB()
    {
        return connectorB;
    }

    public void create()
    {
        if (type == LineType.Line)
        {
            Point3D direction = rayB.getPoint().calcSub(rayA.getPoint()).calcDiv(length);
            Point3D tmpSV = new Point3D(0f, 0f, 0f).calcSub(direction);
            float rotateAngle = new Point3D(1f, 0f, 0f).calcAngle(tmpSV);

            Point3D now = rayA.getPoint();
            for (float f = 0; f < length; f++)
            {
                Rail rail = new Rail(world);
                rail.create();
                rail.translate(now.x, now.y + 0.8f, now.z);
                rail.rotate(0, rotateAngle, 0);
                now.add(direction);
            }
        } else if (type == LineType.BezierCurve)
        {
            Point3D[] tmpArr = {rayA.getPoint(), additionalPointOne, rayB.getPoint()};
            for (float f = 0; f < length; f++)
            {
                Point3D tmpNow = SimpleMath.calc3DotBezierPoint(tmpArr, f / length);
                SimpleVector now = tmpNow.convertToSimpleVector();
                Rail rail = new Rail(world);
                rail.create();
                rail.translate(now.x, now.y + 0.8f, now.z);
            }
        }
    }

    public Point3D getMoveVector(Point3D position, RealDirection direction, float meters)
    {
        return new Point3D(-1f * meters, 0 * meters, -1f * meters);
    }

    /**
     * Возвращает длину линии
     */
    public float getLineLength()
    {
        return length;
    }

    /**
     * Возвращает координаты точки на линии в глобальном измерении
     *
     * @param position точка на линни
     * @return Координаты точки
     */
    public Point3D getPositionVector(float position)
    {
        if (type == LineType.Line)
        {
            Point3D direction = rayB.getPoint().calcSub(rayA.getPoint());
            direction.mult(position / rayA.getPoint().distance(rayB.getPoint()));
            return rayA.getPoint().calcAdd(direction);
        } else if (type == LineType.BezierCurve)
        {
            Point3D[] tmpArr = {rayA.getPoint(), additionalPointOne, rayB.getPoint()};
            Point3D tmpNow = SimpleMath.calc3DotBezierPoint(tmpArr, position / length);
            return tmpNow;
        }

        return null;
    }

    /**
     * Возвращает коннектор подключенную к коннктору A
     *
     * @return Коннектор
     */
    public RailLineConnector getLinkFromConnectorA()
    {
        return connectorA.getConnecteвLink();
    }

    /**
     * Возвращает коннектор подключенную к коннктору B
     *
     * @return Коннектор
     */
    public RailLineConnector getLinkFromConnectorB()
    {
        return connectorB.getConnecteвLink();
    }
}
