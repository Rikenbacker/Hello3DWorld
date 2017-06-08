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
            rail.translateTo(now.x, now.y + 0.8f, now.z);
            rail.rotate(0, rotateAngle, 0);
            now.add(direction);
        }
    }
}
