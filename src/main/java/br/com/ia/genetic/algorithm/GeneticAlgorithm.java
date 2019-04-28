package br.com.ia.genetic.algorithm;

import static br.com.ia.genetic.algorithm.model.converter.BinaryMultiDimensionalConverter.convert;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import br.com.ia.genetic.algorithm.messages.AbstractSubject;
import br.com.ia.genetic.algorithm.messages.algorithm.AlgorithmResult;
import br.com.ia.genetic.algorithm.messages.algorithm.BestChromosome;
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
            postGeneticOperatorsChanges( population, mutatedPopulation );
            fitnessData.add( FitnessSnapshot.from( generation, mutatedPopulation ) );
            population = mutatedPopulation;
            generation++;
        }
        notifyObservers( asList(
            AlgorithmResult.from( currentThread().getName(), problem, algorithm, population, fitnessData ),
            BestChromosome.from( problem, population ) ) );
    }

    private void postGeneticOperatorsChanges(
        final List<Chromosome> oldPopulation,
        final List<Chromosome> newPopulation )
    {
        algorithm.getPropagationStrategy().propagate( algorithm.getPropagationQuantity(), oldPopulation, newPopulation );
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
            final List<Binary> binaries = convert( chromosome.getBinary(), problem.getNumberOfDimensions() );
            final List<Double> realValues = binaries.stream()
                .map( binary -> BinaryDoubleConverter.convert( binary, problem ) )
                .collect( toList() );
            chromosome.setFitness( problem.getFunction().getValue( realValues ) );
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
            .anyMatch( fitness -> problem.getErrorFunction().test( fitness ) );
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
            if( random.nextDouble() > algorithm.getCrossoverProbability() || individual == population.size() - 1 ) {
                newPopulation.add( population.get( individual ) );
                continue;
            }
            final int secondParent = getRandomSecondParent( random, population.size(), individual );
            final Pair<Chromosome,Chromosome> pair = new Pair<>( population.get( individual ), population.get( secondParent ) );
            final Pair<Chromosome,Chromosome> newPair = algorithm.getCrossoverStrategy().apply( pair );
            final List<Chromosome> parents = asList( newPair.getFirst(), newPair.getSecond() );
            computeFitness( parents );
            newPopulation.addAll( parents );
            individual++;
        }
        return newPopulation;
    }

    private static int getRandomSecondParent(
        final Random random,
        final int populationSize,
        final int firstParent )
    {
        int secondParent = random.nextInt( populationSize );
        while( secondParent == firstParent ) {
            secondParent = random.nextInt( populationSize );
        }
        return secondParent;
    }

    private List<Chromosome> applyMutation(
        final Collection<Chromosome> population )
    {
        final Random random = new Random();
        final List<Chromosome> newPopulation = new ArrayList<>( population.size() );
        for( final Chromosome chromosome : population ) {
            if( random.nextDouble() > algorithm.getMutationProbability() ) {
                newPopulation.add( chromosome );
                continue;
            }
            final Chromosome newChromosome = algorithm.getMutationStrategy().apply( chromosome );
            newPopulation.add( newChromosome );
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