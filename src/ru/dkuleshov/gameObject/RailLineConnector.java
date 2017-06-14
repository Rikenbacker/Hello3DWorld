package ru.dkuleshov.gameObject;

import ru.dkuleshov.service.*;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class RailLineConnector
{
    private Ray ray = null;
    private RailLineConnector link = null;
    private IRailroad parent = null;

    public RailLineConnector(IRailroad _parent, Ray _ray)
    {
        parent = _parent;
        ray = _ray;
    }

    public RailLineConnector(IRailroad _parent, RailLineConnector _link)
    {
        parent = _parent;
        link = _link;
        ray = _link.getRay();
    }

    public Point3D getPoint()
    {
        return ray.getPoint();
    }

    public Ray getRay()
    {
        return new Ray(ray);
    }

    /**
     * Возвращает своё положение на линии пути
     *
     * @param _direction Направление входа в соединение
     * @return Положение объекта на пути
     */
    public RailPosition getPosition(RailLine.Direction _direction)
    {
        return new RailPosition(parent, parent.getConnectorA() == this ? 0f : parent.getLineLength(), getRealDirection(_direction));
    }

    public void setLink(RailLineConnector _link) throws Exception
    {
        if (!_link.getPoint().equals(ray.getPoint()))
            throw new Exception("Connectors placed in different places.");

        link = _link;
    }

    public RailLine.RealDirection getRealDirection(RailLine.Direction direction)
    {
        RailLine.RealDirection ret;

        if (parent.getConnectorA() == this)
        {
            ret = direction == RailLine.Direction.Outside ? RailLine.RealDirection.FromAToB : RailLine.RealDirection.FromBToA;
        } else
        {
            ret = direction == RailLine.Direction.Outside ? RailLine.RealDirection.FromBToA : RailLine.RealDirection.FromAToB;
        };

        return ret;
    }

    public IRailroad getParent()
    {
        return parent;
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

    public Point3D getDirection()
    {
        return ray.getDirection();
    }
}
