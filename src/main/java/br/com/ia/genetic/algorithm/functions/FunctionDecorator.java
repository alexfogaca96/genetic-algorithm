package br.com.ia.genetic.algorithm.functions;

import java.util.Collection;

public class FunctionDecorator
    implements
        Function
{
    protected final Function function;

    public FunctionDecorator(
        final Function function )
    {
        this.function = function;
    }

    @Override
    public double getValue(
        final double... variables )
    {
        return function.getValue( variables );
    }

    @Override
    public double getValue(
        final Collection<Double> variables )
    {
        return function.getValue( variables );
    }

    @Override
    public String toString()
    {
        return function.toString();
    }
}
