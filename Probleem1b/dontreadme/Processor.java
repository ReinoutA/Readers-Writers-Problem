package lab4.dontreadme;


import java.util.concurrent.atomic.AtomicInteger;

import lab4.Proxy;


public class Processor extends Thread
{    
    public int tid;
    public Proxy proxy;
    public SharedResource sharedResource;
    public Dio dio;
    public AtomicInteger num_ops;


    public Processor(int tid)
    {
        this.tid = tid;
        proxy = Proxy.getInstance();
        sharedResource = SharedResource.getInstance();
        dio = Dio.getInstance();
        num_ops = new AtomicInteger(0);
    }

    public int getTid()
    {
        return tid;
    }

    public int getOps()
    {
        return num_ops.get();
    }
}
