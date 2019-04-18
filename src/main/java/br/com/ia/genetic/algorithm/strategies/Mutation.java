package br.com.ia.genetic.algorithm.strategies;

import java.util.Random;

import br.com.ia.genetic.algorithm.model.Binary;
import br.com.ia.genetic.algorithm.model.Chromosome;

/**
 * Algoritmos para execução da <i>mutação</i> em um cromossomo.
 */
public enum Mutation
{
    ONE_GENE
    {
        @Override
        int getMask(
            final int bits )
        {
            final Random random = new Random();
            final int bit = random.nextInt( bits );
            final StringBuilder mask = new StringBuilder();
            for( int i = 0; i < bits; i++ ) {
                if( i == bit ) {
                    mask.append( "1" );
                } else {
                    mask.append( "0" );
                }
            }
            return Integer.valueOf( mask.toString(), 2 );
        }
    },
    N_GENES
    {
        @Override
        int getMask(
            final int bits )
        {
            final Random random = new Random();
            final int n = random.nextInt( bits ) + 1;
            final int[] bitsToChange = new int[ n ];
            for( int bit = 0; bit < n; bit++ ) {
                bitsToChange[ bit ] = random.nextInt( bits );
            }
            final StringBuilder mask = new StringBuilder();
            outer: for( int i = 0; i < bits; i++ ) {
                for( int k = 0; k < n; k++ ) {
                    if( i == bitsToChange[ k ] ) {
                        mask.append( "1" );
                        continue outer;
                    }
                }
                mask.append( "0" );
            }
            return Integer.valueOf( mask.toString(), 2 );
        }
    },
    PROBABILITY_PER_GENE
    {
        @Override
        int getMask(
            final int bits )
        {
            final Random random = new Random();
            final StringBuilder mask = new StringBuilder();
            for( int bit = 0; bit < bits; bit++ ) {
                if( random.nextDouble() < 0.5 ) {
                    mask.append( "1" );
                } else {
                    mask.append( "0" );
                }
            }
            return Integer.valueOf( mask.toString(), 2 );
        }
    };

    abstract int getMask(
        final int bits );

    public Chromosome apply(
        final Chromosome chromosome )
    {
        final int bits = chromosome.getBinary().length();
        final int mask = getMask( bits );
        return new Chromosome( chromosome.getBinary().xor( Binary.of( mask, bits ) ), 0d );
    }
}