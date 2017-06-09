package ru.dkuleshov.gameObject;

import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import ru.dkuleshov.C3DObject.*;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class RailLine
{
    SimpleVector pointOne = null;
    SimpleVector pointTwo = null;

    RailLineConnector connectorOne = null;
    RailLineConnector connectorTwo = null;

    private World world = null;

    public enum Direction {Inside, Outside};
    public enum RealDirection {FromAToB, FromBToA};

    public RailLine(SimpleVector _start, SimpleVector _end, World _world)
    {
        pointOne = _start;
        pointTwo = _end;
        connectorOne = new RailLineConnector(this, _start);
        connectorTwo = new RailLineConnector(this, _end);
        world = _world;
    }

    public RailLine(RailLineConnector _start, SimpleVector _end, World _world) throws Exception
    {
        pointOne = _start.getPoint();
        pointTwo = _end;
        connectorOne = new RailLineConnector(this, _start);
        _start.setLink(connectorOne);
        connectorTwo = new RailLineConnector(this, _end);
        world = _world;
    }

    public RailLine(RailLineConnector _start, RailLineConnector _end, World _world) throws Exception
    {
        pointOne = _start.getPoint();
        pointTwo = _end.getPoint();
        connectorOne = new RailLineConnector(this, _start);
        _start.setLink(connectorOne);
        connectorTwo = new RailLineConnector(this, _end);
        _end.setLink(connectorTwo);
        world = _world;
    }

    public RailLineConnector getConnectorOne()
    {
        return connectorOne;
    }

    public RailLineConnector getConnectorTwo()
    {
        return connectorTwo;
    }

    public void create()
    {
        float distance = pointOne.distance(pointTwo);
        SimpleVector direction = new SimpleVector((pointTwo.x - pointOne.x)/distance, (pointTwo.y - pointOne.y)/distance, (pointTwo.z - pointOne.z)/distance);

        SimpleVector tmpSV = new SimpleVector(0f, 0f, 0f);
        tmpSV.sub(direction);
        float rotateAngle = new SimpleVector(1f, 0f, 0f).calcAngle(tmpSV);

        SimpleVector now = new SimpleVector(pointOne);
        for (float f = 0; f < distance; f++)
        {
            Rail rail = new Rail(world);
            rail.create();
            rail.translate(now.x, now.y + 0.8f, now.z);
            rail.rotate(0, rotateAngle, 0);
            now.add(direction);
        }
    }

    public SimpleVector getMoveVector(SimpleVector position, RealDirection direction, float meters)
    {
        return new SimpleVector(-1f * meters, 0 * meters, -1f * meters);
    }

    /**
     * Возвращает длину линии
     */
    public float getLineLength()
    {
        return pointOne.distance(pointTwo);
    }

    /**
     * Возвращает координаты точки на линии в глобальном измерении
     *
     * @param position точка на линни
     * @return Координаты точки
     */
    public SimpleVector getPositionVector(float positon)
    {
        SimpleVector direction = pointTwo.calcSub(pointOne);
        direction.scalarMul(positon / pointOne.distance(pointTwo));
        return pointOne.calcAdd(direction);
    }

    /**
     * Возвращает коннектор подключенную к коннктору A
     *
     * @return Коннектор
     */
    public RailLineConnector getLinkFromConnectorA()
    {
        return connectorOne.getConnecteвLink();
    }

    /**
     * Возвращает коннектор подключенную к коннктору B
     *
     * @return Коннектор
     */
    public RailLineConnector getLinkFromConnectorB()
    {
        return connectorTwo.getConnecteвLink();
    }
}
