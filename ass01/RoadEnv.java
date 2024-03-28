package ass01;

public class RoadEnv extends Worker {
    private final Latch roadsLatch;
    private final Latch carsLatch;
    private final TrafficLight trafficLight;
    private final Latch totalLatch;
    private Simulation simulation;
    private final Counter counter;

    public RoadEnv(Latch roadsLatch, Latch carsLatch, Counter counter, Latch totalLatch) {
        super("env");
        this.roadsLatch = roadsLatch;
        this.carsLatch = carsLatch;
        this.trafficLight = new TrafficLight();
        this.counter = counter;
        this.totalLatch = totalLatch;
    }

    @Override
    public void run() {
        while (!totalLatch.started()) {
            try {
                this.carsLatch.countDown();
                this.roadsLatch.await();
                this.roadsLatch.reset();
                this.trafficLight.produce();
                this.simulation.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        totalLatch.countDown();
    }
    protected void setupSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
}
