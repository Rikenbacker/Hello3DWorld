package ru.dkuleshov.Events;

/**
 * Created by dkuleshov3 on 09.06.2017.
 */
public interface IChangeRailLineSender
{
    void addChangeRailLineListener(IChangeRailLineListener listener);
    IChangeRailLineListener[] getChangeRailLineListener();
    void removeChangeRailLineListener(IChangeRailLineListener listener);
}
