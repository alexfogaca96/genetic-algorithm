package br.com.ia.genetic.algorithm.functions;

/**
 * Função que <b>inverte</b> o valor aplicado à função pai:<br>
 * <ul>
 * <li>valor = (1 / valor da função pai)</li>
 * </ul>
 */
public class Inverse
    extends
        FunctionDecorator
{
    public Inverse(
        final Function function )
    {
        super( function );
    }

    @Override
    public double getValue(
        final double... variables )
    {
        final double value = super.getValue( variables );
        if( value == 0 ) {
            return Integer.MAX_VALUE;
        }
        return 1 / value;
    }

    @Override
    public String toString()
    {
        return "Inverse( " + super.toString() + " )";
    }
}
