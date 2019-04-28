package br.com.ia.genetic.algorithm.messages.algorithm;

import static br.com.ia.genetic.algorithm.model.converter.BinaryDoubleConverter.convert;
import static java.lang.String.format;

import java.util.Collection;

import br.com.ia.genetic.algorithm.messages.PrintableEvent;
import br.com.ia.genetic.algorithm.model.Chromosome;
import br.com.ia.genetic.algorithm.model.information.Problem;

public class BestChromosome
    implements
        PrintableEvent
{
    private final Problem problem;
    private final Collection<Chromosome> population;

    private BestChromosome(
        final Problem problem,
        final Collection<Chromosome> population )
    {
        this.problem = problem;
        this.population = population;
    }

    public static BestChromosome from(
        final Problem problem,
        final Collection<Chromosome> population )
    {
        return new BestChromosome( problem, population );
    }

    @Override
    public void print()
    {
        final Chromosome bestChromosome = getBestChromosome();
        final StringBuilder builder = new StringBuilder();
        builder.append( "==== Best chromosome! ====\n" );
        builder.append( format( "   binary: %s\n", bestChromosome.getBinary() ) );
        builder.append( format( "  fitness: %.3f\n", bestChromosome.getFitness() ) );
        final double realNumber = convert( bestChromosome.getBinary(), problem );
        builder.append( format( "   number: %.3f\n", realNumber ) );
        builder.append( format( "  optimal: %s\n", realNumber == 0d ) );
        System.out.println( builder.toString() );
    }

    private Chromosome getBestChromosome()
    {
        return population.stream()
            .max( Chromosome.MAX_FITNESS )
            .orElseThrow( () -> new IllegalStateException( "There needs to be a best chromosome." ) );
    }
}