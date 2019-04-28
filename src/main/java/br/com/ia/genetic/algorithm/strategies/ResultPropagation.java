package br.com.ia.genetic.algorithm.strategies;

import static java.util.stream.Collectors.toList;

import java.util.List;

import br.com.ia.genetic.algorithm.model.Chromosome;

public enum ResultPropagation
{
    NONE
    {
        @Override
        public void propagate(
            final int quantityToPropagate,
            final List<Chromosome> oldPopulation,
            final List<Chromosome> newPopulation )
        {
            // Nothing to be done
        }
    },
    IMMORTALITY
    {
        @Override
        public void propagate(
            final int quantityToPropagate,
            final List<Chromosome> oldPopulation,
            final List<Chromosome> newPopulation )
        {
            final List<Chromosome> sortedOldPopulation = oldPopulation.stream()
                .sorted( Chromosome.MAX_FITNESS )
                .collect( toList() );
            final List<Chromosome> sortedNewPopulation = newPopulation.stream()
                .sorted( Chromosome.MAX_FITNESS )
                .collect( toList() );
            for( int immortal = 0; immortal < quantityToPropagate; immortal++ ) {
                final Chromosome chromosome = sortedOldPopulation.get( oldPopulation.size() - immortal - 1 );
                sortedNewPopulation.remove( immortal );
                sortedNewPopulation.add( immortal, chromosome );
            }
            newPopulation.clear();
            newPopulation.addAll( sortedNewPopulation );
        }
    };

    public abstract void propagate(
        int quantityToPropagate,
        List<Chromosome> oldPopulation,
        List<Chromosome> newPopulation );
}
