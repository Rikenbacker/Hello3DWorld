package ru.dkuleshov.C3DObject;

import com.threed.jpct.SimpleVector;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public interface C3DObject
{
    void create();

    SimpleVector getTransformedCenter();
}