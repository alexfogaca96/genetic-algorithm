package br.com.ia.genetic.algorithm.messages;

public interface Subject
{
    void registerObserver(
        Observer observer );

    void unregisterObserver(
        Observer observer );

    void notifyObservers(
        Event event );

    void notifyObservers(
        Iterable<Event> events );
}
