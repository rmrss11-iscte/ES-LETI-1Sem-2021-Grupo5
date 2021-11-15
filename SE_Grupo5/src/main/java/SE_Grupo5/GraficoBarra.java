package SE_Grupo5;

import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficoBarra extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 124479714253861621L;

	public GraficoBarra(String title,String xLabel,String yLabel, List<GraficoData> data) {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle(title);
		setSize(500, 500);
		setLocationRelativeTo(null);
		criarGrafico(title,xLabel,yLabel,data);
		setVisible(true);
	}

	private void criarGrafico(String title,String xLabel,String yLabel, List<GraficoData> data) {
		DefaultCategoryDataset barra = new  DefaultCategoryDataset();
		for(GraficoData d: data) {
			barra.setValue(d.yData, d.xData, "");
		}
		JFreeChart grafico = ChartFactory.createBarChart(title, xLabel, yLabel, barra, PlotOrientation.VERTICAL, true, true, true);
		ChartPanel painel = new ChartPanel(grafico);
		add(painel);
	}

}
