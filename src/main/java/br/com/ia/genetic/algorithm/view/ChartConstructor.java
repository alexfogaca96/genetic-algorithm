package br.com.ia.genetic.algorithm.view;

import static org.jfree.chart.ChartUtils.writeChartAsPNG;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.com.ia.genetic.algorithm.messages.Event;
import br.com.ia.genetic.algorithm.messages.Observer;
import br.com.ia.genetic.algorithm.messages.algorithm.AlgorithmResult;
import br.com.ia.genetic.algorithm.model.information.FitnessSnapshot;

public final class ChartConstructor
    implements
        Observer
{
    private final Random RANDOM = new Random();

    private final String pathToWrite;

    public ChartConstructor(
        final String pathToWrite )
    {
        this.pathToWrite = pathToWrite;
    }

    @Override
    public void update(
        final Event event )
    {
        if( AlgorithmResult.class.isAssignableFrom( event.getClass() ) ) {
            try {
                generateChart( (AlgorithmResult) event );
            } catch( final IOException e ) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(
        final Iterable<Event> events )
    {
        for( final Event event : events ) {
            update( event );
        }
    }

    private String generateChart(
        final AlgorithmResult result )
        throws FileNotFoundException,
            IOException
    {
        final Collection<FitnessSnapshot> snapshots = result.getFitnessData();
        final String title = getTitle( result );
        final JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "gerações",
            "fitness",
            createDataFromSnapshots( snapshots ),
            PlotOrientation.VERTICAL,
            true,
            true,
            false );
        final String fileName = title.replace( " ", "" ) + ".png";
        final Path path = Files.createFile( Paths.get( pathToWrite + "\\" + fileName ) );
        writeChartAsPNG( new FileOutputStream( path.toFile() ), chart, 700, 500 );
        return fileName;
    }

    private String getTitle(
        final AlgorithmResult result )
    {
        return "chart" + RANDOM.nextInt();
    }

    private static XYSeriesCollection createDataFromSnapshots(
        final Collection<FitnessSnapshot> snapshots )
    {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeries averageFitnessLine = new XYSeries( "Valor médio do fitness" );
        final XYSeries maximumFitnessLine = new XYSeries( "Melhor fitness da geração" );
        final XYSeries minimumFitnessLine = new XYSeries( "Pior fitness da geração" );
        snapshots.forEach( snapshot -> {
            final int generation = snapshot.getGeneration();
            averageFitnessLine.add( generation, snapshot.getAverageFitness() );
            maximumFitnessLine.add( generation, snapshot.getMaximumFitness() );
            minimumFitnessLine.add( generation, snapshot.getMinimumFitness() );
        } );
        dataset.addSeries( averageFitnessLine );
        dataset.addSeries( maximumFitnessLine );
        dataset.addSeries( minimumFitnessLine );
        return dataset;
    }
}