package ru.dkuleshov.service;

import com.threed.jpct.Camera;
import com.threed.jpct.Matrix;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import ru.dkuleshov.C3DObject.C3DObject;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class WorldCamera
{
    public enum State {Free, Follow};

    private State state = State.Free;
    private World world = null;
    private Camera camera = null;
    private C3DObject followObject = null;
    private SimpleVector prevPositionFollow = null;

    private float xAngle = 0;

    public WorldCamera(World _world)
    {
        world = _world;
        camera = world.getCamera();
        camera.setPosition(0, 0f, 0f);
    }

    private void rotateMouseFree(MouseMapper mmapper, long ticks)
    {
        Matrix rot = camera.getBack();
        int dx = mmapper.getDeltaX();
        int dy = mmapper.getDeltaY();

        float ts = 0.2f * ticks;
        float tsy = ts;

        if (dx != 0)
            ts = dx / 500f;

        if (dy != 0)
            tsy = dy / 500f;

        if (dx != 0)
            rot.rotateAxis(rot.getYAxis(), ts);

        if ((dy > 0 && xAngle < Math.PI / 2) || (dy < 0 && xAngle > -Math.PI / 2))
        {
            rot.rotateX(tsy);
            xAngle += tsy;
        }

        camera.setBack(world.getCamera().getBack().cloneMatrix());
    }

    private void rotateMouseFollow(MouseMapper mmapper, long ticks)
    {
        SimpleVector pos = followObject.getTransformedCenter();
        pos = pos.calcSub(prevPositionFollow);

        camera.setPosition(pos);
        camera.lookAt(followObject.getTransformedCenter());

        int dx = mmapper.getDeltaX();
        int dy = mmapper.getDeltaY();

        float ts = 200f * (float)ticks;

        if (dx != 0)
        {
            float speed = (float)dx / ts;
            camera.moveCamera(dx >= 0 ? Camera.CAMERA_MOVERIGHT : Camera.CAMERA_MOVELEFT, dx >= 0 ? speed : speed * -1f);
        }

        if (dy != 0)
        {
            float speed = (float)dy / ts;
            camera.moveCamera(dy >= 0 ? Camera.CAMERA_MOVEDOWN : Camera.CAMERA_MOVEUP, dy >= 0 ? speed : speed * -1f);
        }

        prevPositionFollow = followObject.getTransformedCenter().calcSub(camera.getPosition());
    }

    public void rotateMouse(MouseMapper mmapper, long ticks)
    {
        switch (state)
        {
            case Free:
                rotateMouseFree(mmapper, ticks);
                break;

            case Follow:
                rotateMouseFollow(mmapper, ticks);
                break;
        }
    }

    public void follow(C3DObject _follow)
    {
        state = State.Follow;
        followObject = _follow;

        SimpleVector pos = followObject.getTransformedCenter();
        pos = pos.calcSub(new SimpleVector(-2f, 2f, -2f));
        camera.setPosition(pos);
        camera.lookAt(followObject.getTransformedCenter());

        prevPositionFollow = followObject.getTransformedCenter().calcSub(camera.getPosition());
    }
}
