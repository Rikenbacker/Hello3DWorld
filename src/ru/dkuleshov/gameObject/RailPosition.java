package ru.dkuleshov.gameObject;

import com.threed.jpct.SimpleVector;
import ru.dkuleshov.service.Point3D;

/**
 * Created by dkuleshov3 on 09.06.2017.
 */
public class RailPosition
{
    private RailLine line = null;
    private float position = 0;
    private RailLine.RealDirection direction = null;

    public RailPosition(RailLine _line, float _position, RailLine.RealDirection _direction)
    {
        line = _line;
        position = _position;
        direction = _direction;
    }

    public Point3D getPositionVector()
    {
        return line.getPositionVector(position);
    }

    /**
     * Сдвигает себя по линии на указанное количество метров.
     *
     * @param meters количество метров на которые необходимо сдвинуть
     */
    public void move(float meters)
    {
        float newPosition;
        if (direction == RailLine.RealDirection.FromAToB)
            newPosition = position + meters;
        else
            newPosition = position - meters;

        float lineLength = line.getLineLength();

        //Обработка случаев когда новая позиция выходит за рамки текущей линии
        while (line != null && (newPosition < 0 || newPosition > lineLength))
        {
            RailLineConnector conn = null;

            if (newPosition < 0)
                conn = line.getLinkFromConnectorA();

            if (newPosition > lineLength)
                conn = line.getLinkFromConnectorB();

            if (conn == null)
            {
                position = newPosition < 0 ? 0 : lineLength;
                return;
            }

            //Получаю параметры новой линии
            RailPosition tmpPos = conn.getPosition(RailLine.Direction.Outside);

            line = tmpPos.getLine();
            direction = tmpPos.getDirection();
            position = tmpPos.getPosition();

            if (direction == RailLine.RealDirection.FromAToB && newPosition > 0)
                newPosition = newPosition - lineLength;
            else if (direction == RailLine.RealDirection.FromBToA && newPosition > 0)
                newPosition = line.getLineLength() - (newPosition - lineLength);
            else if (direction == RailLine.RealDirection.FromAToB && newPosition < 0)
            {
                direction = RailLine.RealDirection.FromBToA;
                newPosition = newPosition * -1f;
            }
            else if (direction == RailLine.RealDirection.FromBToA && newPosition < 0)
            {
                direction = RailLine.RealDirection.FromAToB;
                newPosition = line.getLineLength() + newPosition;
            };

            lineLength = line.getLineLength();
        }

        position = newPosition;
    }

    public RailLine getLine()
    {
        return line;
    }

    public RailLine.RealDirection getDirection()
    {
        return direction;
    }

    public float getPosition()
    {
        return position;
    }
}
