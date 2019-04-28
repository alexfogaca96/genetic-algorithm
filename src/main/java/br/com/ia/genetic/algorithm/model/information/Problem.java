package br.com.ia.genetic.algorithm.model.information;

import java.util.function.Predicate;

import br.com.ia.genetic.algorithm.functions.Function;

public class Problem
{
    private final double minimumNumber;
    private final double maximumNumber;
    private final int numberOfBits;
    private final int numberOfDimensions;
    private final Function function;
    private final Predicate<Double> errorFunction;

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

    public int getNumberOfDimensions()
    {
        return numberOfDimensions;
    }

    public Function getFunction()
    {
        return function;
    }

    public Predicate<Double> getErrorFunction()
    {
        return errorFunction;
    }

    public static class Builder
    {
        private final double minimumNumber;
        private final double maximumNumber;
        private int numberOfBits;
        private int numberOfDimensions = 1;
        private Function function;
        private Predicate<Double> errorFunction;

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

        public Builder numberOfDimensions(
            final int numberOfDimensions )
        {
            if( numberOfDimensions <= 0 ) {
                throw new IllegalArgumentException( "Number of dimensions must be bigger than 0." );
            }
            this.numberOfDimensions = numberOfDimensions;
            return this;
        }

        public Builder function(
            final Function function )
        {
            this.function = function;
            return this;
        }

        public Builder errorFunction(
            final Predicate<Double> errorFunction )
        {
            this.errorFunction = errorFunction;
            return this;
        }

        public Problem build()
        {
            if( numberOfBits == 0 || function == null ) {
                throw new IllegalStateException( "Problem can't be built until all fields are set." );
            }
            if( numberOfBits % numberOfDimensions != 0 ) {
                throw new IllegalStateException( "Number of bits doesn't fit perfectly on number of dimensions." );
            }
            return new Problem(
                minimumNumber,
                maximumNumber,
                numberOfBits,
                numberOfDimensions,
                function,
                errorFunction );
        }
    }

    private Problem(
        final double minimumNumber,
        final double maximumNumber,
        final int numberOfBits,
        final int numberOfDimensions,
        final Function function,
        final Predicate<Double> errorFunction )
    {
        this.maximumNumber = maximumNumber;
        this.minimumNumber = minimumNumber;
        this.numberOfBits = numberOfBits;
        this.numberOfDimensions = numberOfDimensions;
        this.function = function;
        this.errorFunction = errorFunction;
    }

    @Override
    public String toString()
    {
        return function + " [" + minimumNumber + ", " + maximumNumber + "]";
    }
}
