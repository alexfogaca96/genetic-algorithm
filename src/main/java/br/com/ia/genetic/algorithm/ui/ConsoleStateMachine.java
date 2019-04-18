package br.com.ia.genetic.algorithm.ui;

public final class ConsoleStateMachine
{
    private State state;

    private ConsoleStateMachine(
        final State state )
    {
        this.state = state;
    }

    public static ConsoleStateMachine init()
    {
        return new ConsoleStateMachine( State.INIT );
    }

    public boolean isClosed()
    {
        return state == State.CLOSE;
    }

    public void information()
    {
        if( state == State.RUN ) {
            System.out.println( "Change to BREAK or CLOSE to change to information state." );
        }
        modifyState( State.INFORMATION );
    }

    public void run()
    {
        if( state == State.RUN ) {
            System.out.println( "It's already running." );
        }
        modifyState( State.RUN );
    }

    public void pause()
    {
        if( state != State.RUN ) {
            System.out.println( "There's no proccess to break." );
        }
        modifyState( state = State.PAUSE );
    }

    public void close()
    {
        modifyState( State.CLOSE );
    }

    private synchronized void modifyState(
        final State state )
    {
        this.state = state;
    }

    public static enum State
    {
        INIT,
        INFORMATION,
        RUN,
        PAUSE,
        CLOSE;
    }
}
