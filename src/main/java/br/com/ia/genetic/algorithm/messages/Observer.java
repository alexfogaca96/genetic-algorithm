package br.com.ia.genetic.algorithm.messages;

public interface Observer
{
    void update(
        Event event );

    void update(
        Iterable<Event> events );
}
