package ru.dkuleshov.C3DObject;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class Terrain implements C3DObject
{
    private World world = null;
    private Object3D mesh = null;

    public Terrain(World _world)
    {
        world = _world;
    }

    @Override
    public void create()
    {
        //terrain = Primitives.getBox(20f, 1f);
        mesh = Primitives.getPlane(1, 1000f);
        mesh.rotateX((float)Math.PI / 2f);
        mesh.translate(0f, 500f, 0f);
        mesh.setTexture("grid");
        mesh.enableLazyTransformations();
        world.addObject(mesh);

        mesh.build();
    }

    @Override
    public SimpleVector getTransformedCenter()
    {
        if (mesh != null)
            return mesh.getTransformedCenter();
        else
            return null;
    }
}
