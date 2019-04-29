package br.com.ia.genetic.algorithm;

import static java.util.Arrays.asList;

import br.com.ia.genetic.algorithm.functions.Rastringin;
import br.com.ia.genetic.algorithm.functions.Subtraction;
import br.com.ia.genetic.algorithm.model.information.Algorithm;
import br.com.ia.genetic.algorithm.model.information.Problem;
import br.com.ia.genetic.algorithm.strategies.Crossover;
import br.com.ia.genetic.algorithm.strategies.MatingSelection;
import br.com.ia.genetic.algorithm.strategies.Mutation;
import br.com.ia.genetic.algorithm.strategies.ResultPropagation;
import br.com.ia.genetic.algorithm.view.ChartConstructor;

public final class ApplicationMain
{
    public static void main(
        final String[] args )
    {
        final double acceptableError = 0.01;

        final Problem problem = Problem.builder( - 5d, 5d )
            .numberOfBits( 20 )
            .numberOfDimensions( 2 )
            .function( new Subtraction( 100d, Rastringin.INSTANCE ) )
            .errorFunction( fitness -> 100d - fitness < acceptableError )
            .build();

        final Algorithm algorithm = Algorithm.builder()
            .crossoverProbability( 0.75 )
            .crossoverStrategy( Crossover.UNIFORM )
            .mutationProbability( 0.05 )
            .mutationStrategy( Mutation.ONE_GENE )
            .matingSelectionStrategy( MatingSelection.FITNESS_PROPORTIONATE )
            .maximumGenerations( 1000 )
            .populationSize( 50 )
            .propagationQuantity( 1 )
            .propagationStrategy( ResultPropagation.IMMORTALITY )
            .build();

        final ChartConstructor chartConstructor = new ChartConstructor( "charts" );
        new GeneticAlgorithmRunner().run( problem, algorithm, asList( chartConstructor ) );
    }
}
