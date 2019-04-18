package br.com.ia.genetic.algorithm.ui;

public final class ConsoleUI
    extends
        Thread
{
    private final ConsoleStateMachine consoleStateMachine = ConsoleStateMachine.init();

    private ConsoleUI()
    {
    }

    public static void startConsole()
    {
        new ConsoleUI().start();
    }

    @Override
    public void run()
    {
        consoleStateMachine.information();
        while( ! consoleStateMachine.isClosed() ) {
            gatherInformation();
            notifySubscribers();
            consumeNotifications();
        }
    }

    private void gatherInformation()
    {
        throw new UnsupportedOperationException( "Still not implemented." );
    }

    private void notifySubscribers()
    {
        throw new UnsupportedOperationException( "Still not implemented." );
    }

    private void consumeNotifications()
    {
        throw new UnsupportedOperationException( "Still not implemented." );
    }
}
