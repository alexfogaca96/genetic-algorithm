package br.com.ia.genetic.algorithm.functions;

import java.util.Collection;

public class Exponential
    extends
        FunctionDecorator
{
    private final double exponent;

    public Exponential(
        final Function function,
        final double exponent )
    {
        super( function );
        this.exponent = exponent;
    }

    @Override
    public double getValue(
        final double... variables )
    {
        return Math.pow( super.getValue( variables ), exponent );
    }

    @Override
    public double getValue(
        final Collection<Double> variables )
    {
        return Math.pow( super.getValue( variables ), exponent );
    }

    @Override
    public String toString()
    {
        return "Exponential of " + exponent + " from ( " + super.toString() + " )";
    }
}
