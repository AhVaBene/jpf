package ass01;

public class CarAgent extends Worker {
    private final Barrier barrier;
    private final Latch carsLatch;
    private final Counter counter;
    private final Latch totalLatch;

    public CarAgent(String id, Barrier barrier, Latch carsLatch, Counter counter, Latch totalLatch) {
        super(id);
        this.barrier = barrier;
        this.carsLatch = carsLatch;
        this.counter = counter;
        this.totalLatch = totalLatch;
    }

    @Override
    public void run() {
        while (!totalLatch.started()) {
            try {
                carsLatch.await();
                this.log("Sense");
                this.barrier.hitAndWaitAll1();
                carsLatch.reset();
                this.log("Decide");
                this.barrier.hitAndWaitAll2();
                this.log("Act");
                this.barrier.hitAndWaitAll3();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        totalLatch.countDown();
    }
}
