package br.com.ia.genetic.algorithm.model.converter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.com.ia.genetic.algorithm.model.Binary;

/**
 * <p>
 * Dado um número de dimensões (D) e um {@link Binary}, converte para D
 * {@link Binary} dividindo sequencialmente.
 * </p>
 * Ex. <b>01101001 e 2 dimensões -> 0110 e 1001</b>
 * <p>
 * Assume-se que o número de bits do {@link Binary} dado é divisível por D.
 * </p>
 */
public final class BinaryMultiDimensionalConverter
{
    public static List<Binary> convert(
        final Binary binary,
        final int numberOfDimensions )
    {
        if( numberOfDimensions == 1 ) {
            return Arrays.asList( binary );
        }
        final int length = binary.length();
        final List<Binary> binaries = new LinkedList<>();
        for( int dimension = 0; dimension < numberOfDimensions; dimension++ ) {
            binaries.add( buildMask( binary, length, length / numberOfDimensions, dimension ) );
        }
        return binaries;
    }

    private static Binary buildMask(
        final Binary binary,
        final int binaryLength,
        final int maskLength,
        final int order )
    {
        final StringBuilder builder = new StringBuilder();
        for( int bit = 0; bit < binaryLength; bit++ ) {
            if( bit >= maskLength * order && bit < maskLength * ( order + 1 ) ) {
                builder.append( "1" );
            } else {
                builder.append( "0" );
            }
        }
        final long binaryValue = Long.parseLong( builder.toString(), 2 );
        final Binary maskBinary = binary.and( Binary.of( binaryValue, binaryLength ) );
        long maskBinaryValue = maskBinary.getNumber();
        while( maskBinaryValue > Math.pow( 2, maskLength ) ) {
            maskBinaryValue = maskBinaryValue >>> 1;
        }
        return Binary.of( maskBinaryValue, maskLength );
    }
}
