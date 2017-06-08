package ru.dkuleshov.C3DObject;

import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Primitives;
import com.threed.jpct.World;

import java.util.ArrayList;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class Rail implements C3DObject
{
    private World world = null;
    private Object3D rail = null;

    public Rail(World _world)
    {
        world = _world;
    }

    @Override
    public void create()
    {
        Object3D box = new Object3D(12);

        SimpleVector upperLeftFront=new SimpleVector(-1,-1,-1);
        SimpleVector upperRightFront=new SimpleVector(-0.8,-1,-1);
        SimpleVector lowerLeftFront=new SimpleVector(-1,-0.8,-1);
        SimpleVector lowerRightFront=new SimpleVector(-0.8,-0.8,-1);

        SimpleVector upperLeftBack = new SimpleVector( -1, -1, 0);
        SimpleVector upperRightBack = new SimpleVector(-0.8, -1, 0);
        SimpleVector lowerLeftBack = new SimpleVector( -1, -0.8, 0);
        SimpleVector lowerRightBack = new SimpleVector(-0.8, -0.8, 0);

        box.addTriangle(upperLeftFront,0,0, lowerLeftFront,0,1, upperRightFront,1,0);
        box.addTriangle(upperRightFront,1,0, lowerLeftFront,0,1, lowerRightFront,1,1);
        box.addTriangle(upperLeftBack,0,0, upperRightBack,1,0, lowerLeftBack,0,1);
        box.addTriangle(upperRightBack,1,0, lowerRightBack,1,1, lowerLeftBack,0,1);
        box.addTriangle(upperLeftBack,0,0, upperLeftFront,0,1, upperRightBack,1,0);
        box.addTriangle(upperRightBack,1,0, upperLeftFront,0,1, upperRightFront,1,1);
        box.addTriangle(lowerLeftBack,0,0, lowerRightBack,1,0, lowerLeftFront,0,1);
        box.addTriangle(lowerRightBack,1,0, lowerRightFront,1,1, lowerLeftFront,0,1);
        box.addTriangle(upperLeftFront,0,0, upperLeftBack,1,0, lowerLeftFront,0,1);
        box.addTriangle(upperLeftBack,1,0, lowerLeftBack,1,1, lowerLeftFront,0,1);
        box.addTriangle(upperRightFront,0,0, lowerRightFront,0,1, upperRightBack,1,0);
        box.addTriangle(upperRightBack,1,0, lowerRightFront, 0,1, lowerRightBack,1,1);

        Object3D railPart = new Object3D(box);

        ArrayList<Object3D> meshes = new ArrayList<Object3D>();
        meshes.add(railPart);

        railPart = new Object3D(box);
        railPart.translate(0.6f, 0f, 0f);
        railPart.translateMesh();
        meshes.add(railPart);

        railPart = new Object3D(box);
        railPart.translate(-0.1f, 0.1f, -1.8f);
        railPart.rotateY((float)Math.PI / 2f);
        railPart.rotateMesh();
        railPart.translateMesh();
        meshes.add(railPart);

        railPart = new Object3D(box);
        railPart.translate(-0.1f, 0.1f, -1.3f);
        railPart.rotateY((float)Math.PI / 2f);
        railPart.rotateMesh();
        railPart.translateMesh();
        meshes.add(railPart);

        rail = Object3D.mergeAll(meshes.toArray(new Object3D[meshes.size()]));
        rail.setTexture("box");
        rail.setEnvmapped(Object3D.ENVMAP_ENABLED);

        rail.build();
        SimpleVector railCenter = new SimpleVector(-0.5f, 0f, -1f);
        rail.setCenter(railCenter);
        rail.setRotationPivot(railCenter);
        world.addObject(rail);
    }

    @Override
    public SimpleVector getTransformedCenter()
    {
        if (rail == null)
            return null;

        return rail.getTransformedCenter();
    }

    public void rotate(float x, float y, float z)
    {
        if (rail == null)
            return;

        rail.rotateX(x);
        rail.rotateY(y);
        rail.rotateZ(z);
    }

    public void translateTo(float x, float y, float z)
    {
        rail.translate(x, y, z);
    }
}
