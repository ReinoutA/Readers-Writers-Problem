package lab4.dontreadme;


import lab4.Proxy;
import lab4.RWConfig;
import lab4.dontreadme.Processor;
import lab4.dontreadme.RWLogger;
import lab4.dontreadme.SharedResource;


public class Reader extends Processor
{
    public Reader(int tid)
    {
        super(tid);
    }

    public void run()
    {
        RWLogger.debugf("<%d> signaling...", tid);
        dio.signalReady();
        RWLogger.debugf("<%d> starting...", tid);

        while (!dio.shouldStopTheWorld())
        {
            try {
                proxy.preRead(tid);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sharedResource.read(tid);
            try {
                proxy.postRead(tid);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try
            {
                Thread.sleep(Util.boundedNextInt(RWConfig.READER_WAITING_MIN, RWConfig.READER_WAITING_MAX));
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            num_ops.incrementAndGet();
        }

        dio.signalFinished();
        RWLogger.debugf("<%d> finished!", tid);
    }
}
