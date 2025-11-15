import java.util.*;

public class GA {
    private final FireStation fireStation;
    private final Random rand;
    private final int totalGeneration;
    private final int maxPopulationSize;
    private final double mutationRate;
    private final double crossOverRate;
    private final double elitismPct;
    private final double CONVERGENCE_THRESHOLD = 0.005;
    private final int CONVERGENCE_WINDOW = 6;

    public GA(FireStation fireStation, int totalGeneration, int maxPopulationSize, double mutationRate, double crossOverRate, double elitismPct, Random rand) {
        this.fireStation = fireStation;
        this.totalGeneration = totalGeneration;
        this.maxPopulationSize = maxPopulationSize;
        this.mutationRate = mutationRate;
        this.crossOverRate = crossOverRate;
        this.elitismPct = elitismPct;
        this.rand = rand;
    }

    public Individual runGenAlgo() {
        Population population = new Population(fireStation, rand, maxPopulationSize, elitismPct);
        population.initPopulation();
        population.evaluatePopulationCost();
        population.sortPopulation();

        List<Double> generationFitness = new ArrayList<>();

        double[] recentFitness = new double[CONVERGENCE_WINDOW];
        int convergenceCounter = 0;

        for (int generation = 1; generation <= totalGeneration; generation++) {
            Population nextPop = population.initPopulationWithElitism();
            nextPop.initPopulationWithElitism();

            while (nextPop.getPopulationSize() < maxPopulationSize) {
                Individual parent1 = population.selectParent();
                Individual parent2 = population.selectParent();

                if (rand.nextDouble() < crossOverRate) {
                    Individual[] children = parent1.crossover(parent2);

                    children[0].mutate(fireStation.getEmptyPosition(), mutationRate);
                    children[1].mutate(fireStation.getEmptyPosition(), mutationRate);

                    children[0].repairChromosome(fireStation.getFireStationsCount());
                    children[1].repairChromosome(fireStation.getFireStationsCount());

                    nextPop.addIndividual(children[0]);
                    if (nextPop.getPopulationSize() < maxPopulationSize) {
                        nextPop.addIndividual(children[1]);
                    }
                }
            }

            population = nextPop;
            population.evaluatePopulationCost();
            population.sortPopulation();
            System.out.printf("Generation: %d best distance: %.5f\n", generation, population.getBestIndividual().getCost());

            double meanPopulFitness = population.getMeanPopulationCost();
            generationFitness.add(meanPopulFitness);

            // Check convergence
            recentFitness[convergenceCounter % CONVERGENCE_WINDOW] = meanPopulFitness;
            convergenceCounter++;

            if (convergenceCounter >= CONVERGENCE_WINDOW) {
                double minFitness = Double.MAX_VALUE;
                double maxFitness = Double.MIN_VALUE;

                for (double fitness : recentFitness) {
                    minFitness = Math.min(minFitness, fitness);
                    maxFitness = Math.max(maxFitness, fitness);
                }

                if (maxFitness - minFitness < CONVERGENCE_THRESHOLD) {
                    System.out.println("Converged at generation " + generation);
                    break;
                }
            }
        }
        return population.getBestIndividual();
    }
}
