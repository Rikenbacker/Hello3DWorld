package ru.dkuleshov.Events;
import java.util.ArrayList;
import java.util.EventObject;
/**
 * Created by dkuleshov3 on 09.06.2017.
 */
public class ChangeRailLineEvent extends EventObject
{
    private String message;

    public ChangeRailLineEvent(Object source, String message)
    {
        super(source);
        this.message = message;
    }

    public ChangeRailLineEvent(Object source)
    {
        this(source, "");
    }

    public ChangeRailLineEvent(String s)
    {
        this(null, s);
    }

    public String getMessage()
    {
        return message;
    }

    @Override
    public String toString()
    {
        return getClass().getName() + "[source = " + getSource() + ", message = " + message + "]";
    }
}
