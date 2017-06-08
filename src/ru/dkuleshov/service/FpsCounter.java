package ru.dkuleshov.service;

/**
 * Created by dkuleshov3 on 07.06.2017.
 */
public class FpsCounter
{
    private int fps = 0;
    private int fps_tmp = 0;
    private long time;
    public FpsCounter()
    {
        time = System.nanoTime()/1000000L;
    }

    public void Tick()
    {
        fps_tmp++;
        long now = System.nanoTime()/1000000L;
        if (now - time >= 1000)
        {
            time = now;
            fps = fps_tmp;
            fps_tmp=0;
        }
    }

    public int getFps()
    {
        return fps;
    }

}
