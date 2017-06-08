package ru.dkuleshov.gameObject;

import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import ru.dkuleshov.C3DObject.*;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public class RailLine
{
    SimpleVector start = null;
    SimpleVector end = null;
    private World world = null;

    public RailLine(SimpleVector _start, SimpleVector _end, World _world)
    {
        start = _start;
        end = _end;
        world = _world;
    }

    public void create()
    {
        float distance = start.distance(end);
        SimpleVector direction = new SimpleVector((end.x - start.x)/distance, (end.y - start.y)/distance, (end.z - start.z)/distance);

        SimpleVector tmpSV = new SimpleVector(0f, 0f, 0f);
        tmpSV.sub(direction);
        float rotateAngle = new SimpleVector(1f, 0f, 0f).calcAngle(tmpSV);

        SimpleVector now = new SimpleVector(start);
        for (float f = 0; f < distance; f++)
        {
            Rail rail = new Rail(world);
            rail.create();
            rail.translateTo(now.x, now.y + 0.8f, now.z);
            rail.rotate(0, rotateAngle, 0);
            now.add(direction);
        }
    }
}
