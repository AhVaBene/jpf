package ass01;

public class Counter {
    private int acc;
    private final int max;

    public Counter(int max) {
        this.acc = 0;
        this.max = max;
    }

    public synchronized void inc() {
        acc++;
    }
    public synchronized int getAcc() {
        return acc;
    }
    public synchronized boolean isMax() {
        return acc == max;
    }
}
