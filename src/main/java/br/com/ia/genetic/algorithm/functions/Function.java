package br.com.ia.genetic.algorithm.functions;

import java.util.Collection;

public interface Function
{
    double getValue(
        double... variables );

    double getValue(
        Collection<Double> variables );
}
