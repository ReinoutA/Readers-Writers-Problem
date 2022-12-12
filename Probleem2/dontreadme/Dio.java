package lab4.dontreadme;


import java.util.concurrent.atomic.AtomicInteger;

import lab4.RWConfig;


public class Dio
{
    private static Dio brando = null;

    private AtomicInteger zaWarudo;

    private long start;
    private long end;


    private Dio()
    {
        zaWarudo = new AtomicInteger(RWConfig.N_READERS + RWConfig.N_WRITERS);        

        start = -1;
        end = -1;
    }

    public static synchronized Dio getInstance()
    {
        if (brando == null)
            brando = new Dio();
        return brando;
    }

    public synchronized void signalReady()
    {
        if (zaWarudo.decrementAndGet() > 0)
        {
            try 
            {
                wait();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            start = System.nanoTime();
            RWLogger.debugf("All threads ready, we're off to the races!");
            notifyAll();
        }
    }

    public synchronized void signalFinished()
    {
        if (zaWarudo.decrementAndGet() == 0)
            notifyAll();
    }

    public boolean shouldStopTheWorld()
    {
        return zaWarudo.get() != 0;
    }

    public synchronized void signalStopTheWorld()
    {
        zaWarudo.set(RWConfig.N_READERS + RWConfig.N_WRITERS);
        end = System.nanoTime();
    }

    public long actualElapsedTime()
    {
        return end - start;
    }
}
