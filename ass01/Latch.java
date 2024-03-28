package ass01;

public class Latch {
    private final int states;
    private int counter;
    public Latch(int states) {
        this.states = states;
        this.counter = states;
    }

    public synchronized void countDown() {
        this.counter--;
        if(this.counter == 0) {
            notifyAll();
        }
    }

    public synchronized void await() throws InterruptedException {
        while(this.counter > 0) {
            wait();
        }
    }

    public synchronized boolean started() {
        return this.counter != states;
    }

    public synchronized boolean ended() {
        return this.counter == 0;
    }

    public synchronized void reset() {
        this.counter = states;
    }
}
