package br.com.ia.genetic.algorithm.messages;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSubject
    implements
        Subject
{
    protected final List<Observer> observers = new LinkedList<>();

    @Override
    public void registerObserver(
        final Observer observer )
    {
        observers.add( observer );
    }

    @Override
    public void unregisterObserver(
        final Observer observer )
    {
        observers.remove( observer );
    }

    @Override
    public void notifyObservers(
        final Event event )
    {
        for( final Observer observer : observers ) {
            observer.update( event );
        }
    }

    @Override
    public void notifyObservers(
        final Iterable<Event> events )
    {
        for( final Observer observer : observers ) {
            observer.update( events );
        }
    }
}