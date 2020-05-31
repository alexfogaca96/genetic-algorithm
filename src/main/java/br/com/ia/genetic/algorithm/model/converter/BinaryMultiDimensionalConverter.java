package br.com.ia.genetic.algorithm.model.converter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import br.com.ia.genetic.algorithm.model.Binary;

public final class BinaryMultiDimensionalConverter
{
    private static final Logger LOGGER = Logger.getLogger( BinaryMultiDimensionalConverter.class.getName() );

    /**
     * <p>
     * Given a number of dimensions (D) and a {@link Binary}, it converts
     * to D binaries divided sequentially.
     * </p>
     * i.e. <b>01101001 and 2 dimensions -> 0110 and 1001</b>
     * <p>
     * It's assumed that the number of {@link Binary} bits is divisible by D.
     * </p>
     */
    public static List<Binary> convert(
        final Binary binary,
        final int numberOfDimensions )
    {
        final int length = binary.length();
        if( numberOfDimensions <= 0 || numberOfDimensions > length ) {
            throw new BinaryConverterException( "Invalid number of dimensions " + numberOfDimensions );
        }
        if( numberOfDimensions == 1 ) {
            LOGGER.warning( "1-dimensional binary conversion is an unnecessary operation" );
            return Collections.singletonList( binary );
        }
        if( length % numberOfDimensions != 0 ) {
            throw new BinaryConverterException( String.format(
                "Number of dimensions %d is not divisible by binary length %d",
                numberOfDimensions,
                length ) );
        }
        return doConvert( binary, length, numberOfDimensions );
    }

    private static List<Binary> doConvert(
        final Binary binary,
        final int length,
        final int numberOfDimensions )
    {
        final int maskLength = length / numberOfDimensions;
        final List<Binary> binaries = new LinkedList<>();
        for( int dimension = 0; dimension < numberOfDimensions; dimension++ ) {
            final int initialIndex = maskLength * dimension;
            final long maskedBits = extractBits( binary.getNumber(), initialIndex, initialIndex + maskLength );
            binaries.add( Binary.of( maskedBits, length ) );
        }
        LOGGER.info( String.format( "Successfully converted binary to %d binaries", numberOfDimensions ) );
        return binaries;
    }

    private static long extractBits(
        final long value,
        final int begin,
        final int end )
    {
        final long mask = ( 1 << ( end - begin ) ) - 1;
        return ( value >> begin ) & mask;
    }
}
