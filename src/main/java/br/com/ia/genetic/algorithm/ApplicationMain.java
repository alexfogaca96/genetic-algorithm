package br.com.ia.genetic.algorithm;

import br.com.ia.genetic.algorithm.functions.Rastringin;
import br.com.ia.genetic.algorithm.functions.Subtraction;
import br.com.ia.genetic.algorithm.model.information.Algorithm;
import br.com.ia.genetic.algorithm.model.information.Problem;
import br.com.ia.genetic.algorithm.strategies.Crossover;
import br.com.ia.genetic.algorithm.strategies.MatingSelection;
import br.com.ia.genetic.algorithm.strategies.Mutation;
import br.com.ia.genetic.algorithm.strategies.ResultPropagation;

public final class ApplicationMain
{
    public static void main(
        final String[] args )
    {
        final double acceptableError = 0.005;

        final Problem problem = Problem.builder( - 5d, 5d )
            .numberOfBits( 20 )
            .numberOfDimensions( 2 )
            .function( new Subtraction( 100d, Rastringin.INSTANCE ) )
            .errorFunction( fitness -> 100d - fitness < acceptableError )
            .build();

        final Algorithm algorithm = Algorithm.builder()
            .crossoverProbability( 0.75 )
            .crossoverStrategy( Crossover.UNIFORM )
            .mutationProbability( 0.20 )
            .mutationStrategy( Mutation.N_GENES )
            .matingSelectionStrategy( MatingSelection.FITNESS_PROPORTIONATE )
            .propagationQuantity( 1 )
            .propagationStrategy( ResultPropagation.DEFAULT )
            .maximumGenerations( 100 )
            .populationSize( 10 )
            .build();

        new GeneticAlgorithmRunner().run( problem, algorithm );
    }
}
