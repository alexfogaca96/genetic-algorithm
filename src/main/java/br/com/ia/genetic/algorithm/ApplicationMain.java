package br.com.ia.genetic.algorithm;

import br.com.ia.genetic.algorithm.functions.Function;
import br.com.ia.genetic.algorithm.functions.Rastringin;
import br.com.ia.genetic.algorithm.functions.Subtraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import br.com.ia.genetic.algorithm.functions.Exponential;
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
        final double acceptableError = 0.01;
        double exponential_fitness_equalizer =  Math.pow(100d,3);
        final Problem problem = Problem.builder( - 5d, 5d )
            .numberOfBits( 20 )
            .numberOfDimensions( 2 )
            .function(new Exponential((Function)new Subtraction( 100d, Rastringin.INSTANCE ), 3.0))
            .errorFunction( fitness -> exponential_fitness_equalizer - fitness < exponential_fitness_equalizer*acceptableError )
            .build();

        final Problem problem2 = Problem.builder( - 5d, 5d )
                .numberOfBits( 20 )
                .numberOfDimensions( 2 )
                .function(new Subtraction( 100d, Rastringin.INSTANCE ))
                .errorFunction( fitness ->  100d - fitness < acceptableError )
                .build();
        
        final Algorithm algorithm = Algorithm.builder()
	            .crossoverProbability( 0.75 )
	            .crossoverStrategy( Crossover.UNIFORM )
	            .mutationProbability( 0.05 )
	            .mutationStrategy( Mutation.ONE_GENE )
	            .matingSelectionStrategy( MatingSelection.FITNESS_PROPORTIONATE )
	            .maximumGenerations( 1000)
	            .populationSize( 50 )
	            .propagationQuantity( 1 )
	            .propagationStrategy( ResultPropagation.IMMORTALITY )
	            .build();

        final Algorithm algorithm2 = Algorithm.builder()
                .crossoverProbability( 0.90 )
                .crossoverStrategy( Crossover.UNIFORM )
                .mutationProbability( 0.05 )
                .mutationStrategy( Mutation.ONE_GENE )
                .matingSelectionStrategy( MatingSelection.FITNESS_PROPORTIONATE )
                .maximumGenerations( 1000)
                .populationSize( 50 )
                .propagationStrategy( ResultPropagation.NONE )
                .build();
        
        final Algorithm algorithm3 = Algorithm.builder()
                .crossoverProbability( 0.75 )
                .crossoverStrategy( Crossover.SINGLE_POINT )
                .mutationProbability( 0.05 )
                .mutationStrategy( Mutation.ONE_GENE )
                .matingSelectionStrategy( MatingSelection.FITNESS_PROPORTIONATE )
                .maximumGenerations( 1000)
                .populationSize( 50 )
                .propagationStrategy( ResultPropagation.NONE )
                .build();
        
        final Algorithm algorithm4 = Algorithm.builder()
                .crossoverProbability(0.90 )
                .crossoverStrategy( Crossover.SINGLE_POINT )
                .mutationProbability( 0.20 )
                .mutationStrategy( Mutation.ONE_GENE )
                .matingSelectionStrategy( MatingSelection.FITNESS_PROPORTIONATE )
                .maximumGenerations( 1000)
                .populationSize( 50 )
                .propagationQuantity( 1 )
                .propagationStrategy( ResultPropagation.IMMORTALITY )
                .build();
        
        final Algorithm algorithm5 = Algorithm.builder()
                .crossoverProbability( 0.75 )
                .crossoverStrategy( Crossover.SINGLE_POINT )
                .mutationProbability( 0.20 )
                .mutationStrategy( Mutation.ONE_GENE )
                .matingSelectionStrategy( MatingSelection.FITNESS_PROPORTIONATE )
                .maximumGenerations( 1000)
                .populationSize( 50 )
                .propagationQuantity( 1 )
                .propagationStrategy( ResultPropagation.IMMORTALITY )
                .build();
        
        Collection<Problem> list_of_problems = 
        		new ArrayList<Problem>(Arrays.asList(problem2 )); 
        Collection<Algorithm> list_of_algorithms =
        		new ArrayList<Algorithm>(Arrays.asList(algorithm , algorithm2 , algorithm3 , algorithm4 , algorithm5));
        
        for (Problem cur_problem : list_of_problems) {
        	for (Algorithm cur_algorithm : list_of_algorithms) {
        		new GeneticAlgorithmRunner().run( cur_problem, cur_algorithm );
        	}
        }
    }
}
