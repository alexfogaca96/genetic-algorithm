package br.com.ia.genetic.algorithm.strategies;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import br.com.ia.genetic.algorithm.model.Binary;
import br.com.ia.genetic.algorithm.model.Chromosome;
import br.com.ia.genetic.algorithm.model.Pair;

/**
 * Algoritmos de seleção de cromossomos para sofrerem <i>clonagem</i> ou
 * <i>crossover</i>.
 */
public enum MatingSelection
{
    FITNESS_PROPORTIONATE
    {
        @Override
        public List<Chromosome> apply(
            final List<Chromosome> population )
        {
            final List<Pair<Double,Chromosome>> roulette = new LinkedList<>();
            double fitnessSum = 0;
            for( final Chromosome chromosome : population ) {
                fitnessSum += chromosome.getFitness();
                roulette.add( new Pair<>( fitnessSum, chromosome ) );
            }
            final Random random = new Random();
            final List<Chromosome> newPopulation = new ArrayList<>();
            for( int individual = 0; individual < population.size(); individual++ ) {
                final double randomDouble = random.nextDouble() * fitnessSum;
                for( final Pair<Double,Chromosome> pair : roulette ) {
                    if( randomDouble <= pair.getFirst() ) {
                        newPopulation.add( pair.getSecond() );
                        break;
                    }
                }
            }
            return newPopulation;
        }
    },
    INVERSE_FITNESS_PROPORTIONATE
    {
        @Override
        public List<Chromosome> apply(
            final List<Chromosome> population )
        {
            final Optional<Double> maxFitness = population.stream()
                .map( Chromosome::getFitness )
                .reduce( Double::max );
            final List<Chromosome> populationInverseFitness = new ArrayList<>( population.size() );
            for( int chromosome = 0; chromosome < population.size(); chromosome++ ) {
                final Chromosome currentChromosome = population.get( chromosome );
                if( currentChromosome.getFitness() == 0d ) {
                    populationInverseFitness.add( new Chromosome( currentChromosome.getBinary(), Integer.MAX_VALUE ) );
                } else {
                    final double fitness = maxFitness.isPresent() ? maxFitness.get() : 0d / currentChromosome.getFitness();
                    populationInverseFitness.add( new Chromosome( currentChromosome.getBinary(), fitness ) );
                }
            }
            final Map<Binary,Double> binaryToRawFitness = new HashMap<>();
            for( final Chromosome chromosome : population ) {
                if( ! binaryToRawFitness.containsKey( chromosome.getBinary() ) ) {
                    binaryToRawFitness.put( chromosome.getBinary(), chromosome.getFitness() );
                }
            }
            return FITNESS_PROPORTIONATE.apply( populationInverseFitness ).stream()
                .map( chromosome -> new Chromosome( chromosome.getBinary(), binaryToRawFitness.get( chromosome.getBinary() ) ) )
                .collect( toList() );
        }
    },
    TOURNAMENT
    {
        @Override
        public List<Chromosome> apply(
            final List<Chromosome> population )
        {
            throw new UnsupportedOperationException( "Still not implemented." );
        }
    },
    STOCHASTIC_UNIVERSAL
    {
        @Override
        public List<Chromosome> apply(
            final List<Chromosome> population )
        {
            throw new UnsupportedOperationException( "Still not implemented." );
        }
    },
    REWARD_BASED
    {
        @Override
        public List<Chromosome> apply(
            final List<Chromosome> population )
        {
            throw new UnsupportedOperationException( "Still not implemented." );
        }
    },
    TRUNCATION
    {
        @Override
        public List<Chromosome> apply(
            final List<Chromosome> population )
        {
            throw new UnsupportedOperationException( "Still not implemented." );
        }
    };
	
	public abstract List<Chromosome> apply(
		List<Chromosome> population );
}