package lab4;


import lab4.dontreadme.Dio;
import lab4.dontreadme.Processor;
import lab4.dontreadme.RWLogger;

public class Main
{
    public static void main(String[] args)
    {
        RWConfig.printConfig();

        Dio dio = Dio.getInstance();

        int tid = 0;
        Processor[] readers = new Processor[RWConfig.N_READERS];
        for (int reader_i = 0; reader_i < RWConfig.N_READERS; reader_i++, tid++)
        {
            readers[reader_i] = new lab4.dontreadme.Reader(tid);
            readers[reader_i].start();
        }
        Processor[] writers = new Processor[RWConfig.N_WRITERS];
        for (int writer_i = 0; writer_i < RWConfig.N_WRITERS; writer_i++, tid++)
        {
            writers[writer_i] = new lab4.dontreadme.Writer(tid);
            writers[writer_i].start();
        }

        try
        {
            Thread.sleep(RWConfig.RUN_DURATION_MS);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        RWLogger.debugf("Main stopping world...");
        dio.signalStopTheWorld();
        RWLogger.debugf("Main stopped the world!");

        long actualElapsedTime = dio.actualElapsedTime();
        StringBuilder statsBuilder = new StringBuilder(String.format("Overview: %dms test (ran for %dus)\n",
                RWConfig.RUN_DURATION_MS, actualElapsedTime/1000));
        statsBuilder.append(" > reads - %d\n");
        int total_reads = 0;
        for (Processor reader: readers)
        {
            statsBuilder.append(String.format("   > <%d> - %d\n", reader.getTid(), reader.getOps()));
            total_reads += reader.getOps();
        }
        statsBuilder.append(" > writes - %d\n");
        int total_writes = 0;
        for (Processor writer: writers)
        {
            statsBuilder.append(String.format("   > <%d> - %d\n", writer.getTid(), writer.getOps()));
            total_writes += writer.getOps();
        }
        RWLogger.infof(statsBuilder.toString(), total_reads, total_writes);
        System.exit(0);
    }
}
