package lab4;

import java.util.concurrent.Semaphore;

public class Proxy {
    private static Proxy instance = null;

    private final Semaphore roomEmpty;
    private final Semaphore mutex;
    private int readers;

    private Proxy() {
        this.roomEmpty = new Semaphore(1);
        this.mutex = new Semaphore(1);
        int readers = 0;
    }

    public static synchronized Proxy getInstance() {
        if (instance == null)
            instance = new Proxy();
        return instance;
    }

    public void preRead(int pid) throws InterruptedException {
        // TODO: synchronization before read goes here
        mutex.acquire();
        readers += 1;
        if (readers == 1) {
            // De eerste binnen lockt
            roomEmpty.acquire();
        }
        mutex.release();
    }

    public void postRead(int pid) throws InterruptedException {
        // TODO: synchronization after read goes here
        mutex.acquire();
        readers -= 1;
        if (readers == 0) {
            // De laatste buiten unlockt
            roomEmpty.release();
        }
        mutex.release();
    }

    public void preWrite(int pid) throws InterruptedException {
        // TODO: synchronization before write goes here
        roomEmpty.acquire();
    }

    public void postWrite(int pid) {
        // TODO: synchronization after write goes here
        roomEmpty.release();
    }
}
