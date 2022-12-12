package lab4;

import java.util.concurrent.Semaphore;

class LightSwitch {
    private int counter;
    private final Semaphore mutex;

    public LightSwitch() {
        this.counter = 0;
        this.mutex = new Semaphore(1);
    }

    void lock(Semaphore s) throws InterruptedException {
        mutex.acquire();
        counter += 1;
        if (counter == 1) {
            s.acquire();
        }
        mutex.release();
    }

    void unlock(Semaphore s) throws InterruptedException {
        mutex.acquire();
        counter -= 1;
        if (counter == 0) {
            s.release();
        }
        mutex.release();
    }
}

public class Proxy {
    private static Proxy instance = null;

    private final Semaphore roomEmpty;
    private LightSwitch readLS;

    private Proxy() {
        this.roomEmpty = new Semaphore(1);
        this.readLS = new LightSwitch();
    }

    public static synchronized Proxy getInstance() {
        if (instance == null)
            instance = new Proxy();
        return instance;
    }

    public void preRead(int pid) throws InterruptedException {
        readLS.lock(roomEmpty);
    }

    public void postRead(int pid) throws InterruptedException {
        readLS.unlock(roomEmpty);
    }

    public void preWrite(int pid) throws InterruptedException {
        roomEmpty.acquire();
    }

    public void postWrite(int pid) {
        roomEmpty.release();
    }
}
