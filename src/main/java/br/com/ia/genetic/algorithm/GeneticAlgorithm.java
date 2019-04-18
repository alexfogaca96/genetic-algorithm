package br.com.ia.genetic.algorithm;

import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import br.com.ia.genetic.algorithm.messages.AbstractSubject;
import br.com.ia.genetic.algorithm.messages.algorithm.AlgorithmResult;
import br.com.ia.genetic.algorithm.messages.algorithm.PopulationSnapshot;
import br.com.ia.genetic.algorithm.model.Binary;
import br.com.ia.genetic.algorithm.model.Chromosome;
import br.com.ia.genetic.algorithm.model.Pair;
import br.com.ia.genetic.algorithm.model.converter.BinaryDoubleConverter;
import br.com.ia.genetic.algorithm.model.information.Algorithm;
import br.com.ia.genetic.algorithm.model.information.FitnessSnapshot;
import br.com.ia.genetic.algorithm.model.information.Problem;

final class GeneticAlgorithm
    extends
        AbstractSubject
    implements
        Runnable
{
    private final Problem problem;
    private final Algorithm algorithm;
    private final Collection<FitnessSnapshot> fitnessData;

    @Override
    public void run()
    {
        int generation = 0;
        List<Chromosome> population = initializeRandomPopulation();
        computeFitness( population );
        while( ! stopConditionSatisfied( generation, population ) ) {
            notifyObservers( PopulationSnapshot.of( generation, population ) );
            final List<Chromosome> newPopulation = applySurvivalStrategy( population );
            final List<Chromosome> crossoveredPopulation = applyCrossoverOrCloning( newPopulation );
            final List<Chromosome> mutatedPopulation = applyMutation( crossoveredPopulation );
            fitnessData.add( FitnessSnapshot.from( generation, mutatedPopulation ) );
            population = mutatedPopulation;
            generation++;
        }
        notifyObservers( AlgorithmResult.from( currentThread().getName(), problem, algorithm, population, fitnessData ) );
    }

    private List<Chromosome> initializeRandomPopulation()
    {
        final List<Chromosome> population = new ArrayList<>( algorithm.getPopulationSize() );
        for( int individual = 0; individual < algorithm.getPopulationSize(); individual++ ) {
            final Binary binary = Binary.random( problem.getNumberOfBits() );
            population.add( new Chromosome( binary, 0d ) );
        }
        return population;
    }

    private void computeFitness(
        final Iterable<Chromosome> population )
    {
        for( final Chromosome chromosome : population ) {
            final double realValue = BinaryDoubleConverter.convert( chromosome.getBinary(), problem );
            chromosome.setFitness( problem.getFunction().getValue( realValue ) );
        }
    }

    private boolean stopConditionSatisfied(
        final int generation,
        final Collection<Chromosome> population )
    {
        if( generation > algorithm.getMaximumGenerations() ) {
            return true;
        }
        return population.stream()
            .map( Chromosome::getFitness )
            .anyMatch( fitness -> Math.abs( fitness ) <= problem.getAcceptableError() );
    }

    private List<Chromosome> applySurvivalStrategy(
        final List<Chromosome> population )
    {
        return algorithm.getMatingSelectionStrategy().apply( population );
    }

    private List<Chromosome> applyCrossoverOrCloning(
        final List<Chromosome> population )
    {
        final Random random = new Random();
        final List<Chromosome> newPopulation = new ArrayList<>( population.size() );
        for( int individual = 0; individual < population.size(); individual++ ) {
            if( random.nextDouble() <= algorithm.getCrossoverProbability() ) {
                int secondParent = random.nextInt( population.size() );
                while( secondParent == individual ) {
                    secondParent = random.nextInt( population.size() );
                }
                final Pair<Chromosome,Chromosome> pair = new Pair<>( population.get( individual ), population.get( secondParent ) );
                final Pair<Chromosome,Chromosome> newPair = algorithm.getCrossoverStrategy().apply( pair );
                final List<Chromosome> parents = asList( newPair.getFirst(), newPair.getSecond() );
                computeFitness( parents );
                newPopulation.addAll( parents );
                individual++;
            } else {
                newPopulation.add( population.get( individual ) );
            }
        }
        return newPopulation;
    }

    private List<Chromosome> applyMutation(
        final Collection<Chromosome> population )
    {
        final Random random = new Random();
        final List<Chromosome> newPopulation = new ArrayList<>( population.size() );
        for( final Chromosome chromosome : population ) {
            if( random.nextDouble() <= algorithm.getMutationProbability() ) {
                final Chromosome newChromosome = algorithm.getMutationStrategy().apply( chromosome );
                newPopulation.add( newChromosome );
            } else {
                newPopulation.add( chromosome );
            }
        }
        computeFitness( newPopulation );
        return newPopulation;
    }

    GeneticAlgorithm(
        final Problem problem,
        final Algorithm algorithm )
    {
        this.problem = problem;
        this.algorithm = algorithm;
        fitnessData = new LinkedList<>();
    }
}
