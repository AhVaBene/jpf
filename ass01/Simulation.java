package ass01;

import java.util.ArrayList;
import java.util.List;

public class Simulation extends Thread{
    private final Counter counter;
    private final Latch totalLatch;
    /* environment of the simulation */
    private RoadEnv env;
    /* list of the agents */
    private final List<CarAgent> agents;
    /* simulation listeners */
    private final List<SimulationListener> listeners;
    public Simulation(Counter counter, Latch totalLatch) {
        this.listeners = new ArrayList<>();
        this.agents = new ArrayList<>();
        this.counter = counter;
        this.totalLatch = totalLatch;
    }

    @Override
    public void run() {
        this.env.start();
        this.agents.forEach(CarAgent::start);
        while(!totalLatch.ended()) {
            try {
                this.totalLatch.await();
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
        System.out.println("Simulation over in " + counter.getAcc());
    }

    protected void setupEnvironment(RoadEnv env) {
        this.env = env;
    }

    protected void addAgent(CarAgent agent) {
        agents.add(agent);
    }
    public void addSimulationListener(SimulationListener l) {
        this.listeners.add(l);
    }
    public synchronized void produce() throws InterruptedException {
        this.produceListeners();
        this.incrementStep();
    }
    public synchronized void produceListeners() {
        this.listeners.forEach(SimulationListener::produce);
    }
    public synchronized void incrementStep() {
        System.out.println("DONE STEP " + (counter.getAcc()+1));
        this.counter.inc();
        if(counter.isMax()) {
            this.totalLatch.countDown();
        }
    }
}
