package br.com.ia.genetic.algorithm.model.information;

import br.com.ia.genetic.algorithm.strategies.Crossover;
import br.com.ia.genetic.algorithm.strategies.MatingSelection;
import br.com.ia.genetic.algorithm.strategies.Mutation;

public class Algorithm
{
    /**
     * Variáveis default
     */
    private static final int DEF_MAXIMUM_GENERATIONS = 100;
    private static final int DEF_POPULATION_SIZE = 20;
    private static final double DEF_CROSSOVER_PROBABILITY = 0.6d;
    private static final double DEF_MUTATION_PROBABILITY = 0.2d;
    private static final MatingSelection DEF_MATING_SELECTION_STRATEGY = MatingSelection.FITNESS_PROPORTIONATE;
    private static final Crossover DEF_CROSSOVER_STRATEGY = Crossover.SINGLE_POINT;
    private static final Mutation DEF_MUTATION_STRATEGY = Mutation.ONE_GENE;

    private final int maximumGenerations;
    private final int populationSize;
    private final double crossoverProbability;
    private final double mutationProbability;
    private final MatingSelection matingSelectionStrategy;
    private final Crossover crossoverStrategy;
    private final Mutation mutationStrategy;

    public static Builder builder()
    {
        return new Builder();
    }

    public int getMaximumGenerations()
    {
        return maximumGenerations;
    }

    public int getPopulationSize()
    {
        return populationSize;
    }

    public double getCrossoverProbability()
    {
        return crossoverProbability;
    }

    public double getMutationProbability()
    {
        return mutationProbability;
    }

    public MatingSelection getMatingSelectionStrategy()
    {
        return matingSelectionStrategy;
    }

    public Crossover getCrossoverStrategy()
    {
        return crossoverStrategy;
    }

    public Mutation getMutationStrategy()
    {
        return mutationStrategy;
    }

    public static class Builder
    {
        private int maximumGenerations;
        private int populationSize;
        private double crossoverProbability;
        private double mutationProbability;
        private MatingSelection matingSelectionStrategy;
        private Crossover crossoverStrategy;
        private Mutation mutationStrategy;

        private Builder()
        {
        }

        public Builder maximumGenerations(
            final int maximumGenerations )
        {
            this.maximumGenerations = maximumGenerations;
            return this;
        }

        public Builder populationSize(
            final int populationSize )
        {
            this.populationSize = populationSize;
            return this;
        }

        public Builder crossoverProbability(
            final double crossoverProbability )
        {
            this.crossoverProbability = getNormalizedProbability( crossoverProbability );
            return this;
        }

        public Builder mutationProbability(
            final double mutationProbability )
        {
            this.mutationProbability = getNormalizedProbability( mutationProbability );
            return this;
        }

        public Builder matingSelectionStrategy(
            final MatingSelection matingSelectionStrategy )
        {
            this.matingSelectionStrategy = matingSelectionStrategy;
            return this;
        }

        public Builder crossoverStrategy(
            final Crossover crossoverStrategy )
        {
            this.crossoverStrategy = crossoverStrategy;
            return this;
        }

        public Builder mutationStrategy(
            final Mutation mutationStrategy )
        {
            this.mutationStrategy = mutationStrategy;
            return this;
        }

        private static double getNormalizedProbability(
            final double probability )
        {
            if( probability < 0d ) {
                return 0d;
            }
            if( probability > 1d ) {
                return 1d;
            }
            return probability;
        }

        public Algorithm build()
        {
            return new Algorithm(
                maximumGenerations == 0 ? DEF_MAXIMUM_GENERATIONS : maximumGenerations,
                populationSize == 0 ? DEF_POPULATION_SIZE : populationSize,
                crossoverProbability == 0d ? DEF_CROSSOVER_PROBABILITY : crossoverProbability,
                mutationProbability == 0d ? DEF_MUTATION_PROBABILITY : mutationProbability,
                matingSelectionStrategy == null ? DEF_MATING_SELECTION_STRATEGY : matingSelectionStrategy,
                crossoverStrategy == null ? DEF_CROSSOVER_STRATEGY : crossoverStrategy,
                mutationStrategy == null ? DEF_MUTATION_STRATEGY : mutationStrategy );
        }
    }

    private Algorithm(
        final int maximumGenerations,
        final int populationSize,
        final double crossoverProbability,
        final double mutationProbability,
        final MatingSelection matingSelectionStrategy,
        final Crossover crossoverStrategy,
        final Mutation mutationStrategy )
    {
        this.maximumGenerations = maximumGenerations;
        this.populationSize = populationSize;
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.matingSelectionStrategy = matingSelectionStrategy;
        this.crossoverStrategy = crossoverStrategy;
        this.mutationStrategy = mutationStrategy;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "G.A. (Genetic Algorithm)\n" );
        sb.append( " >  population:       " + populationSize + "\n" );
        sb.append( " >  max generations:  " + maximumGenerations + "\n" );
        sb.append( " >  crossover:        " + crossoverProbability * 100 + "% - " + crossoverStrategy + "\n" );
        sb.append( " >  mutation:         " + mutationProbability * 100 + "% - " + mutationStrategy + "\n" );
        sb.append( " >  clone:            " + ( 1d - crossoverProbability ) * 100 + "% - " + matingSelectionStrategy + "\n" );
        return sb.toString();
    }
}
