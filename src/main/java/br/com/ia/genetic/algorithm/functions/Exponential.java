package br.com.ia.genetic.algorithm.functions;

import java.util.Collection;
import java.lang.Math;

public class Exponential extends FunctionDecorator{

	private double exponent = 0;
	public Exponential(Function function , double exponent) {
		super(function);
		this.exponent = exponent; 
	}
	@Override
    public double getValue(
        double... variables )
    {
        return Math.pow(super.getValue( variables ) , exponent);
    }

    @Override
    public double getValue(
        final Collection<Double> variables )
    {
        return Math.pow(super.getValue( variables ) , exponent);
    }
    @Override
    public String toString()
    {
        return "Exponential of " + exponent + " from ( " + super.toString() + " )";
    }
}
