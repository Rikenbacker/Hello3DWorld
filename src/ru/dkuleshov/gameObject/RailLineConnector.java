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

    /**
     * Возвращает своё положение на линии пути
     *
     * @param _direction Направление входа в соединение
     * @return Положение объекта на пути
     */
    public RailPosition getPosition(RailLine.Direction _direction)
    {
        return new RailPosition(parent, parent.getConnectorOne() == this ? 0f : parent.getLineLength(), getRealDirection(_direction));
    }

    public void setLink(RailLineConnector _link) throws Exception
    {
        if (_link.getPoint() != point)
            throw new Exception("Connectors placed in different places.");

        link = _link;
    }

    public RailLine.RealDirection getRealDirection(RailLine.Direction direction)
    {
        RailLine.RealDirection ret;

        if (parent.getConnectorOne() == this)
        {
            ret = direction == RailLine.Direction.Outside ? RailLine.RealDirection.FromAToB : RailLine.RealDirection.FromBToA;
        } else
        {
            ret = direction == RailLine.Direction.Outside ? RailLine.RealDirection.FromBToA : RailLine.RealDirection.FromAToB;
        };

        return ret;
    }

    public RailLine getParent()
    {
        return  parent;
    }

    /**
     * Возвращает подключенный коннектор от другой линии
     *
     * @return Положение объекта на пути
     */
    public RailLineConnector getConnecteвLink()
    {
        return link;
    }
}
