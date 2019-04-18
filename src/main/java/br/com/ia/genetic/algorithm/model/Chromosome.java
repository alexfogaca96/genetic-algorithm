package br.com.ia.genetic.algorithm.model;

import java.util.Comparator;

public class Chromosome
{
    public static final Comparator<Chromosome> MAX_FITNESS = (
        first,
        second ) -> Double.valueOf( first.getFitness() ).compareTo( second.getFitness() );

    private final Binary binary;
    private double fitness;

    public Chromosome(
        final Binary binary,
        final double fitness )
    {
        this.binary = binary;
        this.fitness = fitness;
    }

    public Binary getBinary()
    {
        return binary;
    }

    public double getFitness()
    {
        return fitness;
    }

    public void setFitness(
        final double fitness )
    {
        this.fitness = fitness;
    }

    @Override
    public String toString()
    {
        return binary + " -> " + fitness;
    }
}
