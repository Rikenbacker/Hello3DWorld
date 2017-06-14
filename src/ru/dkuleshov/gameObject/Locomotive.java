package ru.dkuleshov.gameObject;

import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import ru.dkuleshov.C3DObject.*;
import ru.dkuleshov.Events.ChangeRailLineEvent;
import ru.dkuleshov.Events.IChangeRailLineListener;
import ru.dkuleshov.Events.IChangeRailLineSender;
import ru.dkuleshov.service.Point3D;

import java.util.ArrayList;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class Locomotive implements IChangeRailLineSender
{
    public enum AccelerationType {Breaking, MoveForward, MoveBackward}

    private C3DObject body = null;
    private World world;
    private RailPosition position = null;

    private float speed = 0;
    private float maxSpeed = 10;
    private float acceleration = 0;
    private AccelerationType accType = AccelerationType.Breaking;

    private ArrayList<IChangeRailLineListener> railLineChangedListeners = new ArrayList<IChangeRailLineListener>();

    public Locomotive(RailLineConnector _rlConn, RailLine.Direction _direction, World _world)
    {
        position = _rlConn.getPosition(_direction);
        world = _world;
    }

    public void create()
    {
        body = new  Rail(world);
        body.create();
        Point3D pos = position.getPositionVector();
        body.translate(pos.x, pos.y - 0.5f, pos.z);
    }

    public void setSpeed(float _speed)
    {
        speed = _speed;
    }

    public void move(float secundes)
    {
        recalcSpeed(secundes);

        RailLine tmpLine = position.getLine();

        position.move(speed * secundes);
        body.setPosition(position.getPositionVector().convertToSimpleVector());

        if (tmpLine != position.getLine())
            doChangeRailLineEvent("ХУЙ");
    }

    public C3DObject getC3DObject()
    {
        return body;
    }

    public void setAcceleration(float _acceleration, AccelerationType _accType)
    {
        if (_acceleration < 0)
            _acceleration = _acceleration * -1;

        acceleration = _acceleration;
        accType = _accType;
    }

    public void setMaxSpeed(float _maxSpeed)
    {
        maxSpeed = _maxSpeed;
    }

    private void recalcSpeed(float secundes)
    {
        if (speed == 0 && accType == AccelerationType.Breaking)
            return;

        if (speed >= maxSpeed && accType == AccelerationType.MoveForward)
        {
            speed = maxSpeed;
            return;
        }

        if (speed <= -maxSpeed && accType == AccelerationType.MoveBackward)
        {
            speed = -maxSpeed;
            return;
        }

        float addSpeed = acceleration * secundes;

        switch (accType)
        {
            case Breaking:
                if (Math.abs(speed) <= addSpeed)
                    speed = 0;
                else
                    speed = speed + (addSpeed * (float)((speed > 0 )? -1f : 1f));
                break;
            case MoveForward:
                if (speed + addSpeed >= maxSpeed)
                    speed = maxSpeed;
                else
                    speed = speed + addSpeed;
                break;
            case MoveBackward:
                if (speed - addSpeed <= -maxSpeed)
                    speed = -maxSpeed;
                else
                    speed = speed - addSpeed;
                break;
        }
    }

    public RailLine getRailLine()
    {
        return position.getLine();
    }

    @Override
    public void addChangeRailLineListener(IChangeRailLineListener listener)
    {
        railLineChangedListeners.add(listener);
    }

    @Override
    public IChangeRailLineListener[] getChangeRailLineListener()
    {
        return railLineChangedListeners.toArray(new IChangeRailLineListener[railLineChangedListeners.size()]);
    }

    @Override
    public void removeChangeRailLineListener(IChangeRailLineListener listener)
    {
        railLineChangedListeners.remove(listener);
    }

    protected void doChangeRailLineEvent(String message)
    {
        ChangeRailLineEvent ev = new ChangeRailLineEvent(this, message);
        for (IChangeRailLineListener listener : railLineChangedListeners)
        {
            listener.RailLineChanched(ev);
        }
    }
}
