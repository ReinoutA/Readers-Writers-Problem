package lab4.dontreadme;


import lab4.RWConfig;
import lab4.dontreadme.Processor;
import lab4.dontreadme.SharedResource;


public class Writer extends Processor
{
    public Writer(int tid)
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
                proxy.preWrite(tid);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sharedResource.write(tid);
            proxy.postWrite(tid);
            
            try
            {
                Thread.sleep(Util.boundedNextInt(RWConfig.WRITER_WAITING_MIN, RWConfig.WRITER_WAITING_MAX));
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
