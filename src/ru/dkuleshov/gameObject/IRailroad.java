package ru.dkuleshov.gameObject;

import ru.dkuleshov.service.Point3D;

/**
 * Created by dkuleshov3 on 14.06.2017.
 */
public interface IRailroad
{
    enum Direction {Inside, Outside}
    enum RealDirection {FromAToB, FromBToA}

    /**
     * Возвращает координаты точки на линии в глобальном измерении
     *
     * @param position точка на линни
     * @return Координаты точки
     */
    Point3D getPositionVector(float position);

    /**
     * Возвращает начальный коннектор участка пути. Для стрелки должен возвращать начальный участок активного пути по стрелке
     * @return коннектор
     */
    RailLineConnector getConnectorA();

    /**
     * Возвращает конечный коннектор участка пути. Для стрелки должен возвращать конечный участок активного пути по стрелке
     * @return коннектор
     */
    RailLineConnector getConnectorB();

    /**
     * Возвращает длину линии. Для стрелки должен возвращать длину активного пути по стрелке
     */
    float getLineLength();
}
