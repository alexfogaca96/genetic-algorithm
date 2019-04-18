package br.com.ia.genetic.algorithm.model;

import static com.google.common.base.MoreObjects.toStringHelper;

public class Pair<S, T>
{
    private final S first;
    private final T second;

    public Pair(
        final S first,
        final T second )
    {
        this.first = first;
        this.second = second;
    }

    public S getFirst()
    {
        return first;
    }

    public T getSecond()
    {
        return second;
    }

    @Override
    public String toString()
    {
        return toStringHelper( this )
            .add( "first", first )
            .add( "second", second )
            .toString();
    }
}
