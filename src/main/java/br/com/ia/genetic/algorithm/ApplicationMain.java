package br.com.ia.genetic.algorithm;

import br.com.ia.genetic.algorithm.functions.Rastringin;
import br.com.ia.genetic.algorithm.model.information.Algorithm;
import br.com.ia.genetic.algorithm.model.information.Problem;
import br.com.ia.genetic.algorithm.strategies.Crossover;
import br.com.ia.genetic.algorithm.strategies.MatingSelection;
import br.com.ia.genetic.algorithm.strategies.Mutation;

public final class ApplicationMain
{
    public static void main(
        final String[] args )
    {
        final Problem problem = Problem.builder( - 5d, 5d )
            .numberOfBits( 10 )
            .function( Rastringin.INSTANCE )
            .acceptableError( 0.005d )
            .build();

        final Algorithm algorithm = Algorithm.builder()
            .crossoverProbability( 0.8 )
            .crossoverStrategy( Crossover.TWO_POINT )
            .mutationProbability( 0.1 )
            .mutationStrategy( Mutation.N_GENES )
            .matingSelectionStrategy( MatingSelection.INVERSE_FITNESS_PROPORTIONATE )
            .maximumGenerations( 5 )
            .populationSize( 6 )
            .build();

        new GeneticAlgorithmRunner().run( problem, algorithm );
    }
}
