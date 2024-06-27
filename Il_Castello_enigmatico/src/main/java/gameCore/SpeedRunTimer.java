package gameCore;

public class SpeedRunTimer implements Runnable {
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private int seconds = 0;

    public void run() {
        while (running) {
            synchronized (this) {
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            try {
                Thread.sleep(1000);
                if (!paused) {
                    seconds++;
                    System.out.println("Seconds passed:"+seconds);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notify();
    }

    public synchronized void stop() {
        running = false;
        resume();
    }

    public synchronized void reset() {
        seconds = 0;
    }

    public int getSeconds() {
        return seconds;
    }
}