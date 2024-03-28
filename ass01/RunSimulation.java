package ass01;

public class RunSimulation {
    public static void main(String[] args) throws InterruptedException {
        //init simulation (agents, env and listeners)
        int nSteps = 10;
        int nCars = 2;
        Latch roadsLatch = new Latch(3);
        Latch carsLatch = new Latch(1);
        Latch totalLatch = new Latch(nCars+2);
        Barrier barrier = new Barrier(nCars, roadsLatch);
        Counter counter = new Counter(nSteps);
        Simulation simulation = new Simulation(counter, totalLatch);

        //simulation encapsulates env and agents
        RoadEnv env = new RoadEnv(roadsLatch, carsLatch, counter, totalLatch);
        simulation.setupEnvironment(env);
        for (int i = 0; i < nCars; i++) {
            String carId = "car-" + i;
            CarAgent car = new CarAgent(carId, barrier, carsLatch, counter, totalLatch);
            simulation.addAgent(car);
        }
        SimulationListener listener = new SimulationListener();
        simulation.addSimulationListener(listener);
        env.setupSimulation(simulation);

        //start simulation
        simulation.start();
        //simulation:
            //init and start env and agents
            //env:
                //init traffic lights
                //start threads:
            //agents:
                //sense, decide, act
    }
}
