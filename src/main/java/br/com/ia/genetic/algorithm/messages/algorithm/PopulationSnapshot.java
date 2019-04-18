package br.com.ia.genetic.algorithm.messages.algorithm;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.Collection;

import br.com.ia.genetic.algorithm.messages.PrintableEvent;
import br.com.ia.genetic.algorithm.model.Chromosome;

public class PopulationSnapshot
    implements
        PrintableEvent
{
    private final int generation;
    private final Collection<Chromosome> population;

    private PopulationSnapshot(
        final int generation,
        final Collection<Chromosome> population )
    {
        this.generation = generation;
        this.population = population;
    }

    public static PopulationSnapshot of(
        final int generation,
        final Collection<Chromosome> population )
    {
        return new PopulationSnapshot( generation, population );
    }

    public int getGeneration()
    {
        return generation;
    }

    public Collection<Chromosome> getPopulation()
    {
        return population;
    }

    @Override
    public void print()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "(Gen. " + generation + ")\n" );
        builder.append( "    Fitness [" );
        population.forEach( chromosome -> builder.append( String.format( "%.3f", chromosome.getFitness() ) + "; " ) );
        builder.deleteCharAt( builder.length() - 2 );
        builder.append( "]\n" );
        builder.append( "Chromosomes [" );
        population.forEach( chromosome -> builder.append( chromosome.getBinary() + "; " ) );
        builder.deleteCharAt( builder.length() - 2 );
        builder.append( "]\n" );
        System.out.println( builder.toString() );
    }

    @Override
    public String toString()
    {
        return toStringHelper( this )
            .add( "generation", generation )
            .add( "population", population )
            .toString();
    }
}
