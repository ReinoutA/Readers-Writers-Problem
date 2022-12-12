package lab4;

import java.time.Duration;

import lab4.dontreadme.RWLogger;

public class RWConfig {
    /*
     * 
     */
    public static final boolean PERFORMANCE = true;

    /*
     * The duration of the measurement.
     */
    public static final long RUN_DURATION_MS = 10000;

    /*
     * Configuration for reader threads.
     * 
     * Main will start N_READERS reader threads. When a reader performs a read, SharedResource will make them sleep 
     * between READER_WAITING_MIN and READER_WAITING_MAX milliseconds. After a read operation, the reader threads will 
     * be made to sleep between READER_PROCESSING_MIN and READER_PROCESSING_MAX milliseconds.
     */
    public static final int N_READERS             = 10;
    public static final int READER_WAITING_MIN    = 100;
    public static final int READER_WAITING_MAX    = 500;
    public static final int READER_PROCESSING_MIN = 100;
    public static final int READER_PROCESSING_MAX = 500;

    /*
     * Configuration for writer threads.
     * 
     * Main will start N_READERS writer threads. When a writer performs a write, SharedResource will make them sleep 
     * between WRITER_WAITING_MIN and WRITER_WAITING_MAX milliseconds. After a write operation, the writer thread will 
     * be made to sleep between WRITER_PROCESSING_MIN and WRITER_PROCESSING_MAX milliseconds.
     */
    public static final int N_WRITERS             = 5;
    public static final int WRITER_WAITING_MIN    = 500;
    public static final int WRITER_WAITING_MAX    = 600;
    public static final int WRITER_PROCESSING_MIN = 500;
    public static final int WRITER_PROCESSING_MAX = 600;


    public static void printConfig()
    {
        RWLogger.infof("Configuration\n" +
            "====================================================\n" +
            " > duration: %d\n" +
            "----------------------------------------------------\n" +
            " > readers:\n" +
            "   > number: %d\n" +
            "   > minimum wait time while not reading: %d\n" +
            "   > maximum wait time while not reading: %d\n" +
            "   > minimum wait time while reading: %d\n" +
            "   > maximum wait time while reading: %d\n" +
            "----------------------------------------------------\n" +
            " > writers:\n"+
            "   > number: %d\n" +
            "   > minimum wait time while not reading: %d\n" +
            "   > maximum wait time while not reading: %d\n" +
            "   > minimum wait time while reading: %d\n" +
            "   > maximum wait time while reading: %d\n" +
            "====================================================",
            RUN_DURATION_MS,
            N_READERS,
            READER_WAITING_MIN,
            READER_WAITING_MAX,
            READER_PROCESSING_MIN,
            READER_PROCESSING_MAX,
            N_WRITERS,
            WRITER_WAITING_MIN,
            WRITER_WAITING_MAX,
            WRITER_PROCESSING_MIN,
            WRITER_PROCESSING_MAX);
    }
}
