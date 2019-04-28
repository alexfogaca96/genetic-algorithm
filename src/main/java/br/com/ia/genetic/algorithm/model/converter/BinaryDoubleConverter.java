package br.com.ia.genetic.algorithm.model.converter;

import br.com.ia.genetic.algorithm.model.Binary;
import br.com.ia.genetic.algorithm.model.information.Problem;

/**
 * <h1>Conversor dos tipos:</h1><br>
 * <ul>
 * <li><b>real -> bin�rio</b></li>
 * <li><b>bin�rio -> real</b></li>
 * </ul>
 * <p>
 * Dado que a convers�o de real para bin�rio requer um erro determinado por
 * quantos bits de representa��o ser�o utilzados, � necess�rio informar diversos
 * fatores para o c�lculo desse erro.
 * </p>
 * <p>
 * O erro � necess�rio para poder calcular um intervalo de representa��o do
 * n�mero real em bin�rio, ou seja, <b>definir intervalos de valores reais para
 * um �nico valor bin�rio</b> de forma que o tamanho dos intervalos sejam
 * definidos pela satisfa��o do erro calculado.
 * </p>
 */
public final class BinaryDoubleConverter
{
    public static Binary convert(
        final double number,
        final Problem problem )
    {
        checkNumberBetweenRange( number, problem );
        final double binaryValue = ( number - problem.getMinimumNumber() ) / conversionError( problem );
        final int binaryIntegerValue = Double.valueOf( binaryValue ).intValue();
        final int oneBitMore = Double.valueOf( Math.pow( 2, problem.getNumberOfBits() ) ).intValue();
        if( binaryIntegerValue == oneBitMore ) {
            return Binary.of( binaryIntegerValue - 1, problem.getNumberOfBits() );
        }
        return Binary.of( binaryIntegerValue, problem.getNumberOfBits() );
    }

    public static double convert(
        final Binary binary,
        final Problem problem )
    {
        return problem.getMinimumNumber() + binary.getNumber() * conversionError( problem );
    }

    private static double conversionError(
        final Problem problem )
    {
        int denominator = Double.valueOf( Math.pow( 2, problem.getNumberOfBits() / problem.getNumberOfDimensions() ) ).intValue();
        if( problem.getMinimumNumber() == 0 ) {
            denominator -= 1;
        }
        return ( problem.getMaximumNumber() - problem.getMinimumNumber() ) / denominator;
    }

    private static void checkNumberBetweenRange(
        final double number,
        final Problem problem )
    {
        if( number > problem.getMaximumNumber() ) {
            throw new IllegalArgumentException( "Number to convert can't be bigger than the maximum number." );
        }
        if( number < problem.getMinimumNumber() ) {
            throw new IllegalArgumentException( "Number to convert can't be smaller than the minimum number." );
        }
    }
}