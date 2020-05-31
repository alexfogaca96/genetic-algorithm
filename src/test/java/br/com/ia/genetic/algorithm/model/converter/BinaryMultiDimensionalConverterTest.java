package br.com.ia.genetic.algorithm.model.converter;

import br.com.ia.genetic.algorithm.model.Binary;
import com.google.common.collect.Iterables;
import org.junit.Test;

import java.util.Collection;

import static br.com.ia.genetic.algorithm.model.converter.BinaryMultiDimensionalConverter.convert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BinaryMultiDimensionalConverterTest
{
    private static final Binary BINARY = Binary.of( 100L, 8 );

    @Test( expected = BinaryConverterException.class )
    public void shouldThrowExceptionWhenNumberOfDimensionsIsZero()
    {
        convert( BINARY, 0 );
    }

    @Test( expected = BinaryConverterException.class )
    public void shouldThrowExceptionWhenNumberOfDimensionsIsNegative()
    {
        convert( BINARY, -1 );
    }

    @Test( expected = BinaryConverterException.class )
    public void shouldThrowExceptionWhenNumberOfDimensionsIsBiggerThanBinarySize()
    {
        convert( BINARY, 9 );
    }

    @Test( expected = BinaryConverterException.class )
    public void shouldThrowExceptionWhenNumberOfDimensionsIsNotDivisibleByBinarySize()
    {
        convert( BINARY, 3 );
    }

    @Test
    public void shouldReturnSingletonOfBinaryWhenNumberOfDimensionsIsOne()
    {
        final int numberOfDimensions = 1;
        final Collection<Binary> binaries = convert( BINARY, numberOfDimensions );
        assertEquals( binaries.size(), numberOfDimensions );
        assertEquals( Iterables.getOnlyElement( binaries ), BINARY );
    }

    @Test
    public void shouldReturnTwoBinariesWhenNumberOfDimensionsIsTwo()
    {
        final int numberOfDimensions = 2;
        final Collection<Binary> binaries = convert( BINARY, numberOfDimensions );
        assertThatBinariesArePartOfBinary( numberOfDimensions, binaries );
    }

    @Test
    public void shouldReturnAsManyBinariesAsTheBinarySizeWhenNumberOfDimensionsIsEqualsToBinarySize()
    {
        final int numberOfDimensions = 8;
        final Collection<Binary> binaries = convert( BINARY, numberOfDimensions );
        assertThatBinariesArePartOfBinary( numberOfDimensions, binaries );
    }

    private static void assertThatBinariesArePartOfBinary(
        final int numberOfDimensions,
        final Collection<Binary> binaries )
    {
        assertEquals( binaries.size(), numberOfDimensions );
        final int binarySize = BINARY.length() / numberOfDimensions;
        final long binariesLeftSum = binaries.stream()
            .map( Binary::getNumber )
            .map( number -> number >>> binarySize )
            .reduce( Long::sum )
            .orElseThrow( () -> new IllegalArgumentException( "Couldn't shift and sum binaries values" ) );
        assertEquals( binariesLeftSum, 0 );
        final long binariesSum = binaries.stream()
            .map( Binary::getNumber )
            .reduce( Long::sum )
            .orElseThrow( () -> new IllegalArgumentException( "Couldn't sum binaries values" ) );
        assertTrue( binariesSum > 0 );
    }
}
