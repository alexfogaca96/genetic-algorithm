package br.com.ia.genetic.algorithm.functions;

import java.util.Collection;

/**
 * Função que <b>subtrai</b> o valor dado pelo valor da função pai:<br>
 * <ul>
 * <li>valor = valor a subtrair - valor da função pai</li>
 * </ul>
 */
public class Subtraction
    extends
        FunctionDecorator
{
    private final double subtraction;

    public Subtraction(
        final double subtraction,
        Function function )
    {
        super( function );
        this.subtraction = subtraction;
    }

    @Override
    public double getValue(
        double... variables )
    {
        return subtraction - super.getValue( variables );
    }

    @Override
    public double getValue(
        final Collection<Double> variables )
    {
        return subtraction - super.getValue( variables );
    }

    @Override
    public String toString()
    {
        return "Subtraction of " + subtraction + " from ( " + super.toString() + " )";
    }
}
