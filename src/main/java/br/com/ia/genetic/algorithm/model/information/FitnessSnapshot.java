package br.com.ia.genetic.algorithm.model.information;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.ia.genetic.algorithm.model.Chromosome;

public class FitnessSnapshot
{
    private final int generation;
    private final double maximumFitness;
    private final double minimumFitness;
    private final double averageFitness;

    private FitnessSnapshot(
        final int generation,
        final double maximumFitness,
        final double minimumFitness,
        final double averageFitness )
    {
        this.generation = generation;
        this.maximumFitness = maximumFitness;
        this.minimumFitness = minimumFitness;
        this.averageFitness = averageFitness;
    }

    public static FitnessSnapshot from(
        final int generation,
        final Collection<Chromosome> population )
    {
        final Optional<Chromosome> maximumFitnessChromosome = population.stream().collect( Collectors.maxBy( Chromosome.MAX_FITNESS ) );
        final Optional<Chromosome> minimumFitnessChromosome = population.stream().collect( Collectors.minBy( Chromosome.MAX_FITNESS ) );
        final Optional<Double> fitnessSum = population.stream()
            .map( Chromosome::getFitness )
            .reduce( Double::sum );
        return new FitnessSnapshot(
            generation,
            maximumFitnessChromosome.isPresent() ? maximumFitnessChromosome.get().getFitness() : 0d,
            minimumFitnessChromosome.isPresent() ? minimumFitnessChromosome.get().getFitness() : 0d,
            ( fitnessSum.isPresent() ? fitnessSum.get() : 0d ) / population.size() );
    }

    public int getGeneration()
    {
        return generation;
    }

    public double getMaximumFitness()
    {
        return maximumFitness;
    }

    public double getMinimumFitness()
    {
        return minimumFitness;
    }

    public double getAverageFitness()
    {
        return averageFitness;
    }

    @Override
    public String toString()
    {
        return generation + ": " + maximumFitness + " | " + minimumFitness + " | " + averageFitness;
    }
}
