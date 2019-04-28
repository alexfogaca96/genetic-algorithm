package br.com.ia.genetic.algorithm.functions;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

import java.util.Collection;

/**
 * <p>
 * Fun��o de Rastringin, retratada em um espa�o d-dimensional.<br>
 * Caracter�stica pela <b>hostilidade � busca locais</b>, mas com m�ximos ou
 * m�nimos globais bem definidos.
 * </p>
 * <p>
 * Caracter�sticas �teis:
 * <ul>
 * <li>Intervalo: xi pertence [-5.12, 5.12]</li>
 * <li>M�nimo global: f(0, 0, ..., 0, 0) = 0</li>
 * </ul>
 * </p>
 */
public class Rastringin
    implements
        Function
{
    public static final Function INSTANCE = new Rastringin();

    private Rastringin()
    {
    }

    @Override
    public double getValue(
        final double... value )
    {
        double result = 10 * value.length;
        for( final double xi : value ) {
            result += xi * xi - 10 * cos( 2 * PI * xi );
        }
        return result;
    }

    @Override
    public double getValue(
    	final Collection<Double> variables )
    {
    	double result = 10 * variables.size();
    	for( final double xi : variables ) {
    		result += xi * xi - 10 * cos( 2 * PI * xi );
    	}
    	return result;
    }

    @Override
    public String toString()
    {
        return "Rastrigin";
    }
}
