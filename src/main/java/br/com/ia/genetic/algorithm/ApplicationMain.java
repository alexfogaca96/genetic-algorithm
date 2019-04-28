package br.com.ia.genetic.algorithm;

import br.com.ia.genetic.algorithm.functions.Function;
import br.com.ia.genetic.algorithm.functions.Rastringin;
import br.com.ia.genetic.algorithm.functions.Subtraction;
import br.com.ia.genetic.algorithm.functions.Exponential;
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
        final double acceptableError = 0.005;

        final Problem problem = Problem.builder( - 5d, 5d )
            .numberOfBits( 20 )
            .numberOfDimensions( 2 )
            .function(new Exponential((Function)new Subtraction( 100d, Rastringin.INSTANCE ), 3.0))
            .errorFunction( fitness -> Math.pow(100d,3) - fitness < acceptableError )
            .build();

        final Algorithm algorithm = Algorithm.builder()
            .crossoverProbability( 0.8 )
            .crossoverStrategy( Crossover.UNIFORM )
            .mutationProbability( 0.2 )
            .mutationStrategy( Mutation.PROBABILITY_PER_GENE )
            .matingSelectionStrategy( MatingSelection.FITNESS_PROPORTIONATE )
            .maximumGenerations( 100)
            .populationSize( 100 )
            .build();

        new GeneticAlgorithmRunner().run( problem, algorithm );
    }
}
