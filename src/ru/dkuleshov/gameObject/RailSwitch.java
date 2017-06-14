package ru.dkuleshov.gameObject;

import com.threed.jpct.World;
import ru.dkuleshov.service.Point3D;
import ru.dkuleshov.service.Ray;

import java.util.ArrayList;

/**
 * Created by dkuleshov3 on 14.06.2017.
 */
public class RailSwitch implements IRailroad
{
    /** Список возможных подключений */
    private ArrayList<RailLine> lines = new ArrayList<RailLine>();

    private RailLine activeLine;

    public RailSwitch(RailLineConnector _connectorA, RailLineConnector _connectorB, World _world) throws Exception
    {
        lines.add(new RailLine(_connectorA, _connectorB, _world));
        activeLine = lines.get(0);
        activeLine.create();
    }

    public RailSwitch(RailLineConnector _connectorA, Ray _rayB, World _world) throws Exception
    {
        lines.add(new RailLine(_connectorA, _rayB, _world));
        activeLine = lines.get(0);
        activeLine.create();
    }

    public RailSwitch(RailLineConnector _connectorA, Point3D _pointB, World _world) throws Exception
    {
        lines.add(new RailLine(_connectorA, _pointB, _world));
        activeLine = lines.get(0);
        activeLine.create();
    }

    public void addLine(RailLineConnector _connectorA, RailLineConnector _connectorB, World _world) throws Exception
    {
        RailLine tmpRail = new RailLine(_connectorA, _connectorB, _world);
        lines.add(tmpRail);
        tmpRail.create();
        reconnectActiveLine();
    }

    public void addLine(RailLineConnector _connectorA, Ray _rayB, World _world) throws Exception
    {
        RailLine tmpRail = new RailLine(_connectorA, _rayB, _world);
        lines.add(tmpRail);
        tmpRail.create();
        reconnectActiveLine();
    }

    public void addLine(RailLineConnector _connectorA, Point3D _pointB, World _world) throws Exception
    {
        RailLine tmpRail = new RailLine(_connectorA, _pointB, _world);
        lines.add(tmpRail);
        tmpRail.create();
        reconnectActiveLine();
    }

    private void reconnectActiveLine() throws Exception
    {
        if (activeLine.getConnectorA() != null)
            if (activeLine.getConnectorA().getConnecteвLink() != null)
                activeLine.getConnectorA().getConnecteвLink().setLink(activeLine.getConnectorA());
        if (activeLine.getConnectorB() != null)
            if (activeLine.getConnectorB().getConnecteвLink() != null)
                activeLine.getConnectorB().getConnecteвLink().setLink(activeLine.getConnectorB());
    }

    /**
     * Возвращает координаты точки на линии в глобальном измерении
     *
     * @param position точка на линни
     * @return Координаты точки
     */
    @Override
    public Point3D getPositionVector(float position)
    {
        if (activeLine == null)
            return null;
        return activeLine.getPositionVector(position);
    }

    @Override
    public RailLineConnector getConnectorA()
    {
        if (activeLine == null)
            return null;
        return activeLine.getConnectorA();
    }

    @Override
    public RailLineConnector getConnectorB()
    {
        if (activeLine == null)
            return null;
        return activeLine.getConnectorB();
    }

    @Override
    public float getLineLength()
    {
        if (activeLine == null)
            return 0;
        return activeLine.getLineLength();
    }

    public IRailroad getLine(int i)
    {
        return lines.get(i);
    }

    public void switchLine(int i) throws Exception
    {
        activeLine = lines.get(i);
        reconnectActiveLine();
    }
}
