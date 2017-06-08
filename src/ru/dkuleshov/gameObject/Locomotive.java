package ru.dkuleshov.gameObject;

import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import ru.dkuleshov.C3DObject.*;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class Locomotive
{
    private C3DObject body = null;
    private RailLine.RealDirection direction = null;
    private SimpleVector position = null;
    private World world;
    private RailLine railLine = null;
    private float speed = 0;

    public Locomotive(RailLineConnector _rlConn, RailLine.Direction _direction, World _world)
    {
        position = _rlConn.getPoint();
        direction = _rlConn.getRealDirection(_direction);
        railLine = _rlConn.getParent();
        world = _world;
    }

    public void create()
    {
        body = new  Rail(world);
        body.create();
        body.translate(position.x, position.y - 0.5f, position.z);
    }

    public void setSpeed(float _speed)
    {
        speed = _speed;
    }

    public void move(float secundes)
    {
        body.translate(railLine.getMoveVector(position, direction, speed * secundes));
    }
}
