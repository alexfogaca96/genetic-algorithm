package br.com.ia.genetic.algorithm.strategies;

import java.util.Random;

import br.com.ia.genetic.algorithm.model.Binary;
import br.com.ia.genetic.algorithm.model.Chromosome;
import br.com.ia.genetic.algorithm.model.Pair;

/**
 * Algoritmos para execução do <i>crossover</i> entre um par de cromossomos.
 */
public enum Crossover
{
    SINGLE_POINT
    {
        @Override
        long getMask(
            final int bits )
        {
            final Random random = new Random();
            final int point = random.nextInt( bits );

            final StringBuilder mask = new StringBuilder();
            for( int i = 0; i <= point; i++ ) {
                mask.append( "0" );
            }
            for( int i = point + 1; i < bits; i++ ) {
                mask.append( "1" );
            }
            return Long.parseLong( mask.toString(), 2 );
        }
    },
    TWO_POINT
    {
        @Override
        long getMask(
            final int bits )
        {
            final Random random = new Random();
            final int pointOne = random.nextInt( bits );
            final int pointTwo = random.nextInt( bits );
            final int min = Math.min( pointOne, pointTwo );
            final int max = Math.max( pointOne, pointTwo );

            final StringBuilder mask = new StringBuilder();
            for( int i = 0; i <= min; i++ ) {
                mask.append( "0" );
            }
            for( int i = min + 1; i < max; i++ ) {
                mask.append( "1" );
            }
            for( int i = max + 1; i < bits; i++ ) {
                mask.append( "0" );
            }
            return Long.parseLong( mask.toString(), 2 );
        }
    },
    UNIFORM
    {
        @Override
        long getMask(
            final int bits )
        {
            final StringBuilder mask = new StringBuilder();
            final Random random = new Random();
            for( int i = 0; i < bits; i++ ) {
                mask.append( random.nextDouble() < 0.5 ? "0" : "1" );
            }
            return Long.parseLong( mask.toString(), 2 );
        }
    };

    abstract long getMask(
        final int bits );

    public Pair<Chromosome,Chromosome> apply(
        final Pair<Chromosome,Chromosome> chromosomes )
    {
        final int firstBits = chromosomes.getFirst().getBinary().length();
        final int secondBits = chromosomes.getSecond().getBinary().length();
        if( firstBits != secondBits ) {
            throw new IllegalStateException( "Chromossomes can't be of different sizes." );
        }
        final Pair<Binary,Binary> parents = new Pair<>( chromosomes.getFirst().getBinary(), chromosomes.getSecond().getBinary() );
        final Pair<Binary,Binary> newBinaries = applyMask( firstBits, parents );
        return new Pair<>( new Chromosome( newBinaries.getFirst(), 0d ), new Chromosome( newBinaries.getSecond(), 0d ) );
    }

    private Pair<Binary,Binary> applyMask(
        final int bits,
        final Pair<Binary,Binary> pair )
    {
        // get left bits
        final Binary mask = Binary.of( getMask( bits ), bits );
        final Binary firstLeftBits = pair.getFirst().and( mask );
        final Binary secondLeftBits = pair.getSecond().and( mask );

        // invert mask
        final Binary inversionMask = Binary.of( Double.valueOf( Math.pow( 2, bits ) ).intValue() - 1, bits );
        final Binary invertedMask = mask.xor( inversionMask );

        // get right bits
        final Binary firstRightBits = pair.getFirst().and( invertedMask );
        final Binary secondRightBits = pair.getSecond().and( invertedMask );

        // mix each other left bits with the other's right bits
        final Binary firstParent = firstLeftBits.or( secondRightBits );
        final Binary secondParent = secondLeftBits.or( firstRightBits );

        return new Pair<>( firstParent, secondParent );
    }
}
