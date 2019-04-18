package br.com.ia.genetic.algorithm.model.information;

import br.com.ia.genetic.algorithm.functions.Function;

public class Problem
{
    private final double minimumNumber;
    private final double maximumNumber;
    private final int numberOfBits;
    private final Function function;
    private final double acceptableError;

    public static Builder builder(
        final double minimumNumber,
        final double maximumNumber )
    {
        return new Builder( minimumNumber, maximumNumber );
    }

    public double getMinimumNumber()
    {
        return minimumNumber;
    }

    public double getMaximumNumber()
    {
        return maximumNumber;
    }

    public int getNumberOfBits()
    {
        return numberOfBits;
    }

    public Function getFunction()
    {
        return function;
    }

    public double getAcceptableError()
    {
        return acceptableError;
    }

    public static class Builder
    {
        private final double minimumNumber;
        private final double maximumNumber;
        private int numberOfBits;
        private Function function;
        private double acceptableError;

        private Builder(
            final double minimumNumber,
            final double maximumNumber )
        {
            if( minimumNumber > maximumNumber ) {
                throw new IllegalArgumentException( "The minimum number is bigger than maximum number." );
            }
            this.minimumNumber = minimumNumber;
            this.maximumNumber = maximumNumber;
        }

        public Builder numberOfBits(
            final int numberOfBits )
        {
            if( numberOfBits <= 0 ) {
                throw new IllegalArgumentException( "Number of bits must be bigger than 0." );
            }
            this.numberOfBits = numberOfBits;
            return this;
        }

        public Builder function(
            final Function function )
        {
            this.function = function;
            return this;
        }

        public Builder acceptableError(
            final double acceptableError )
        {
            if( acceptableError <= 0 ) {
                throw new IllegalArgumentException( "Acceptable error must be bigger than 0." );
            }
            this.acceptableError = acceptableError;
            return this;
        }

        public Problem build()
        {
            if( numberOfBits == 0 || function == null || acceptableError == 0d ) {
                throw new IllegalStateException( "Problem can't be built until all field are set." );
            }
            return new Problem(
                minimumNumber,
                maximumNumber,
                numberOfBits,
                function,
                acceptableError );
        }
    }

    private Problem(
        final double minimumNumber,
        final double maximumNumber,
        final int numberOfBits,
        final Function function,
        final double acceptableError )
    {
        this.maximumNumber = maximumNumber;
        this.minimumNumber = minimumNumber;
        this.numberOfBits = numberOfBits;
        this.function = function;
        this.acceptableError = acceptableError;
    }

    @Override
    public String toString()
    {
        return function + " [" + minimumNumber + ", " + maximumNumber + "]";
    }
}
