package ru.dkuleshov.service;

import com.threed.jpct.Camera;
import com.threed.jpct.Matrix;
import com.threed.jpct.World;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class WorldCamera
{
    public enum State {Free, Follow};

    private State state = State.Free;
    private World world = null;
    private Camera camera = null;

    private float xAngle = 0;

    public WorldCamera(World _world)
    {
        world = _world;
        camera = world.getCamera();
        camera.setPosition(0, 0f, 0f);
    }

    public void rotateMouse(MouseMapper mmapper, long ticks)
    {
        if (state == State.Follow)
            return;

        if (state == State.Free)
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
        };
    }
}
