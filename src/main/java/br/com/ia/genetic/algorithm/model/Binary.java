package br.com.ia.genetic.algorithm.model;

import static java.util.Objects.hash;

import java.util.Objects;

public class Binary
{
    private final int bits;
    private final int numberOfBits;

    private Binary(
        final int bits,
        final int numberOfBits )
    {
        this.bits = bits;
        this.numberOfBits = numberOfBits;
    }

    public static Binary of(
        final int bits,
        final int numberOfBits )
    {
        return new Binary( bits, numberOfBits );
    }

    public static Binary random(
        final int numberOfBits )
    {
        final int randomNumber = Double.valueOf( Math.random() * Math.pow( 2, numberOfBits ) ).intValue();
        return new Binary( randomNumber, numberOfBits );
    }

    public int getNumber()
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
        return String.format( "%0" + numberOfBits + "d", Integer.parseInt( Integer.toBinaryString( bits ) ) );
    }
}
