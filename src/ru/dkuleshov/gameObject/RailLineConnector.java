package ru.dkuleshov.gameObject;

import com.threed.jpct.SimpleVector;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class RailLineConnector
{
    private SimpleVector point = null;
    private RailLineConnector link = null;
    private RailLine parent = null;

    public RailLineConnector(RailLine _parent, SimpleVector _point)
    {
        parent = _parent;
        point = _point;
    }

    public RailLineConnector(RailLine _parent, RailLineConnector _link)
    {
        parent = _parent;
        link = _link;
        point = _link.getPoint();
    }

    public SimpleVector getPoint()
    {
        return point;
    }

    public void setLink(RailLineConnector _link) throws Exception
    {
        if (_link.getPoint() != point)
            throw new Exception("Connectors placed in different places.");

        link = _link;
    }
}
