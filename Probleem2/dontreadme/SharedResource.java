package lab4.dontreadme;


import java.util.concurrent.atomic.AtomicInteger;

import lab4.RWConfig;
import lab4.dontreadme.RWLogger;
import lab4.dontreadme.Util;


public class SharedResource
{
    private static SharedResource instance = null;

    private AtomicInteger n_readers;
    private AtomicInteger n_writers;


    private SharedResource ()
    {
        n_readers = new AtomicInteger(0);
        n_writers = new AtomicInteger(0);
    }

    public static synchronized SharedResource getInstance()
    {
        if (instance == null)
            instance = new SharedResource();
        return instance;
    }

    public void read(int pid)
    {        
        n_readers.incrementAndGet();
        int writers = n_writers.get();
        if (n_writers.get() != 0)
        {
            RWLogger.warnf("read check failed. Expected no writers to be accessing resource, %d writers are currently accessing instead", writers);
            System.exit(-1);
        }
        RWLogger.debugf("<%d> started reading...", pid);

        try
        {
            Thread.sleep(Util.boundedNextInt(RWConfig.READER_PROCESSING_MIN, RWConfig.READER_PROCESSING_MAX));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
            RWLogger.warnf("<%d> encountered InterruptedException", pid);
        }

        n_readers.decrementAndGet();
        RWLogger.debugf("<%d> done reading!", pid);
    }

    public void write(int pid)
    {
        int readers = n_readers.get();
        if (readers != 0)
        {
            RWLogger.warnf("write check failed. Expected no readers to be accessing resource, %d readers are currently accessing instead", readers);
            System.exit(-1);
        }
        int writers = n_writers.incrementAndGet();
        if (writers != 1)
        {
            RWLogger.warnf("writer check failed. Expected only this writer to be accessing resource, %d writers are currently accessing instead", writers);
            System.exit(-1);
        }
        
        RWLogger.debugf("<%d> started writing...", pid);

        try
        {
            Thread.sleep(Util.boundedNextInt(RWConfig.WRITER_PROCESSING_MIN, RWConfig.WRITER_PROCESSING_MAX));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
            RWLogger.warnf("<%d> encountered InterruptedException", pid);
        }

        writers = n_writers.decrementAndGet();
        if (writers != 0)
        {
            RWLogger.warnf("writer check failed. Expected no writer to be accessing resource, %d writers are currently accessing instead", writers);
            System.exit(-1);
        }
        RWLogger.debugf("<%d> done writing!", pid);
    }
}
