package br.com.ia.genetic.algorithm;

import java.util.HashMap;
import java.util.Map;

import br.com.ia.genetic.algorithm.messages.Event;
import br.com.ia.genetic.algorithm.messages.Observer;
import br.com.ia.genetic.algorithm.messages.algorithm.AlgorithmResult;
import br.com.ia.genetic.algorithm.messages.algorithm.BestChromosome;
import br.com.ia.genetic.algorithm.messages.algorithm.PopulationSnapshot;
import br.com.ia.genetic.algorithm.model.information.Algorithm;
import br.com.ia.genetic.algorithm.model.information.Problem;

public final class GeneticAlgorithmRunner
    implements
        Observer
{
    private static final Map<String,GeneticAlgorithm> GENETIC_ALGORITHMS = new HashMap<>();

    public void run(
        final Problem problem,
        final Algorithm algorithm )
    {
        final GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm( problem, algorithm );
        geneticAlgorithm.registerObserver( this );
        final Thread thread = new Thread( geneticAlgorithm );
        GENETIC_ALGORITHMS.put( thread.getName(), geneticAlgorithm );
        thread.start();
    }

    @Override
    public void update(
        final Event event )
    {
        computeEvent( event );
    }

    @Override
    public void update(
        final Iterable<Event> events )
    {
        for( final Event event : events ) {
            computeEvent( event );
        }
    }

    private static void computeEvent(
        final Event event )
    {
        final Class<? extends Event> clazz = event.getClass();
        if( AlgorithmResult.class.isAssignableFrom( clazz ) ) {
            final AlgorithmResult result = (AlgorithmResult) event;
            GENETIC_ALGORITHMS.remove( result.getThreadName() );
            System.out.println( "\n========= END =========\n" );
            result.print();
        }
        if( PopulationSnapshot.class.isAssignableFrom( clazz ) ) {
            final PopulationSnapshot snapshot = (PopulationSnapshot) event;
            snapshot.print();
        }
        if( BestChromosome.class.isAssignableFrom( clazz ) ) {
            final BestChromosome snapshot = (BestChromosome) event;
            snapshot.print();
        }
    }
}