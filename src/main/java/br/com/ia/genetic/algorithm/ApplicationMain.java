package br.com.ia.genetic.algorithm;

import br.com.ia.genetic.algorithm.functions.Rastringin;
import br.com.ia.genetic.algorithm.functions.Subtraction;
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
		
		final Problem problem = Problem.builder( -5d, 5d )
			.numberOfBits( 10 )
			.function( new Subtraction( 100d, Rastringin.INSTANCE ) )
			.errorFunction( (fitness) -> (100d - fitness) < acceptableError )
			.build();
		
		final Algorithm algorithm = Algorithm.builder()
			.crossoverProbability( 0.8 )
			.crossoverStrategy( Crossover.SINGLE_POINT )
			.mutationProbability( 0.1 )
			.mutationStrategy( Mutation.ONE_GENE )
			.matingSelectionStrategy( MatingSelection.FITNESS_PROPORTIONATE )
			.maximumGenerations( 1000 )
			.populationSize( 30 )
			.build();

		new GeneticAlgorithmRunner().run( problem, algorithm );
	}
}