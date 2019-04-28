package br.com.ia.genetic.algorithm.view;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import br.com.ia.genetic.algorithm.messages.algorithm.AlgorithmResult;
import br.com.ia.genetic.algorithm.model.information.FitnessSnapshot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartConstructor{
	
	public static String generate_chart(AlgorithmResult result) throws FileNotFoundException, IOException {
		Collection<FitnessSnapshot> all_generations_snapshots = result.getFitnessData();
		String name_chart = create_chart_image(result.getAlgorithm().toString() ,  all_generations_snapshots );
		return name_chart;
	}
	private static String create_chart_image(String title , Collection<FitnessSnapshot> all_generations_snapshots ) throws FileNotFoundException, IOException {
		String xlabel = "generações";
		String ylabel = "fitness";
		JFreeChart chart = ChartFactory.createXYLineChart(title 
									,xlabel 
									,ylabel
									,generate_data_from_snapshots(all_generations_snapshots)
									, PlotOrientation.VERTICAL 
									,true 
									,true 
									,false);
		int chartWidth = 700;
		int chartHeight = 500;
		String fileName = title.replace(' ', '_') + ".png";
		ChartUtils.writeChartAsPNG(new FileOutputStream(fileName),chart, chartWidth,chartHeight);
		return fileName;
	}
	private static XYSeriesCollection generate_data_from_snapshots(Collection<FitnessSnapshot> snapshots) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries averageFitnessLine = new XYSeries("Valor médio do fitness");
		XYSeries maximumFitnessLine = new XYSeries("melhor fitness da geração");
		XYSeries minimumFitnessLine = new XYSeries("pior fitness da geração");
		snapshots.forEach(snapshot -> {
			int generation = snapshot.getGeneration();
			averageFitnessLine.add( generation , snapshot.getAverageFitness());
			maximumFitnessLine.add(generation , snapshot.getMaximumFitness());
			minimumFitnessLine.add(generation , snapshot.getMinimumFitness());
		});
		dataset.addSeries(averageFitnessLine);
		dataset.addSeries(maximumFitnessLine);
		dataset.addSeries(minimumFitnessLine);
		return dataset;
	}
}