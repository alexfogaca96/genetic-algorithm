package br.com.ia.genetic.algorithm.model;

import static java.util.Objects.hash;

import java.util.Objects;

public class Binary
{
    private final long bits;
    private final int numberOfBits;

    private Binary(
        final long bits,
        final int numberOfBits )
    {
        if( numberOfBits <= 0 ) {
            throw new IllegalArgumentException( "Invalid numberOfBits value " + numberOfBits );
        }
        this.bits = bits;
        this.numberOfBits = numberOfBits;
    }

    public static Binary of(
        final long bits,
        final int numberOfBits )
    {
        return new Binary( bits, numberOfBits );
    }

    public static Binary random(
        final int numberOfBits )
    {
        final long randomNumber = Double.valueOf( Math.random() * Math.pow( 2, numberOfBits ) ).longValue();
        return new Binary( randomNumber, numberOfBits );
    }

    public long getNumber()
    {
        return bits;
    }

    public Binary sum(
        final Binary binary )
    {
        return Binary.of( bits + binary.bits, numberOfBits );
    }

    public Binary minus(
        final Binary binary )
    {
        return Binary.of( bits - binary.bits, numberOfBits );
    }

    public Binary multiply(
        final Binary binary )
    {
        return Binary.of( bits * binary.bits, numberOfBits );
    }

    public Binary divide(
        final Binary binary )
    {
        return Binary.of( bits / binary.bits, numberOfBits );
    }

    public Binary and(
        final Binary binary )
    {
        return Binary.of( bits & binary.bits, numberOfBits );
    }

    public Binary or(
        final Binary binary )
    {
        return Binary.of( bits | binary.bits, numberOfBits );
    }

    public Binary xor(
        final Binary binary )
    {
        return Binary.of( bits ^ binary.bits, numberOfBits );
    }

    public Binary invert()
    {
        return Binary.of( ~ bits, numberOfBits );
    }

    public int length()
    {
        return numberOfBits;
    }

    @Override
    public int hashCode()
    {
        return hash( bits );
    }

    @Override
    public boolean equals(
        final Object obj )
    {
        if( obj == this ) {
            return true;
        }
        if( ! ( obj instanceof Binary ) ) {
            return false;
        }
        final Binary other = (Binary) obj;
        return Objects.equals( bits, other.bits );
    }

    @Override
    public String toString()
    {
        return Long.toBinaryString( bits );
    }
}
