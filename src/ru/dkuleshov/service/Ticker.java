package ru.dkuleshov.service;

/**
 * Created by dkuleshov3 on 08.06.2017.
 */
public  class Ticker
{
    private int rate;
    private long s2;
    private int last_ticks;
    private long last_milisecundes;

    public static long getTime()
    {
        return System.currentTimeMillis();
    }

    public Ticker(int tickrateMS)
    {
        rate = tickrateMS;
        s2 = Ticker.getTime();
    }

    public int getTicks()
    {
        long i = Ticker.getTime();
        last_milisecundes = i - s2;
        last_ticks = 0;
        if (last_milisecundes > rate)
        {
            last_ticks = (int) ((i - s2) / (long) rate);
            s2 += (long) rate * last_ticks;
        }
        return last_ticks;
    }

    public int getLastTicks()
    {
        return last_ticks;
    }

    public float getLastSecundes()
    {
        return (float)last_milisecundes / 1000f;
    }
}