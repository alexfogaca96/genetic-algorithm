package br.com.ia.genetic.algorithm.messages.algorithm;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.Collection;

import br.com.ia.genetic.algorithm.messages.PrintableEvent;
import br.com.ia.genetic.algorithm.model.Chromosome;
import br.com.ia.genetic.algorithm.model.information.Algorithm;
import br.com.ia.genetic.algorithm.model.information.FitnessSnapshot;
import br.com.ia.genetic.algorithm.model.information.Problem;

public class AlgorithmResult
    implements
        PrintableEvent
{
    private final String threadName;
    private final Problem problem;
    private final Algorithm algorithm;
    private final Collection<Chromosome> population;
    private final Collection<FitnessSnapshot> fitnessData;

    private AlgorithmResult(
        final String threadName,
        final Problem problem,
        final Algorithm algorithm,
        final Collection<Chromosome> population,
        final Collection<FitnessSnapshot> fitnessData )
    {
        this.threadName = threadName;
        this.problem = problem;
        this.algorithm = algorithm;
        this.population = population;
        this.fitnessData = fitnessData;
    }

    public static AlgorithmResult from(
        final String threadName,
        final Problem problem,
        final Algorithm algorithm,
        final Collection<Chromosome> population,
        final Collection<FitnessSnapshot> fitnessData )
    {
        return new AlgorithmResult( threadName, problem, algorithm, population, fitnessData );
    }

    public String getThreadName()
    {
        return threadName;
    }

    public Problem getProblem()
    {
        return problem;
    }

    public Algorithm getAlgorithm()
    {
        return algorithm;
    }

    public Collection<Chromosome> getPopulation()
    {
        return population;
    }

    public Collection<FitnessSnapshot> getFitnessData()
    {
        return fitnessData;
    }

    @Override
    public void print()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( problem + " -> " + problem.getErrorFunction() + "\n" );
        builder.append( algorithm + "\n" );
        builder.append( "Final population: [\n" );
        population.forEach( individual -> builder.append( "   " + individual + "\n" ) );
        builder.append( "]\n" );
        builder.append( "Fitness snapshots: [\n" );
        builder.append( " gen: maxFit  | minFit  | avgFit\n" );
        fitnessData.forEach( data -> builder.append(
            String.format( "   %d: %.4f | %.4f | %.4f\n",
                data.getGeneration(),
                data.getMaximumFitness(),
                data.getMinimumFitness(),
                data.getAverageFitness() ) ) );
        builder.append( "]\n" );
        System.out.println( builder.toString() );
    }

    @Override
    public String toString()
    {
        return toStringHelper( this )
            .add( "problem", problem )
            .add( "algorithm", algorithm )
            .add( "population", population )
            .add( "fitnessData", fitnessData )
            .toString();
    }
}
