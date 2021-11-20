package SE_Grupo5;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.BorderLayout;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class GraficoBarra extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 124479714253861621L;
	JTabbedPane abas = new JTabbedPane();
	
	public GraficoBarra(String title,String xLabel,String yLabel, List<SprintHours> sprintHoursList) {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle(title);
		setSize(1000, 550);
		setLocationRelativeTo(null);
		add(BorderLayout.CENTER,abas);
		
		//adicionar aba com grafico do projeto
		List<GraficoData> projetoData = new ArrayList<GraficoData>();
		projetoData = getProjectHorasGraficoData(sprintHoursList);
		abas.addTab("Projeto", criarGrafico("Horas de trabalho usadas no Projeto", "Membro da equipe", "Horas", projetoData));
		
		//adicionar abas com grafico dos sprints
		for(SprintHours sprintHours : sprintHoursList) {
			if (sprintHours.hasSpentTime()) {
				
				
				List<GraficoData> sprintEstimateTimeData = new ArrayList<GraficoData>();
				List<GraficoData> sprintSpentTimeData = new ArrayList<GraficoData>();
				sprintEstimateTimeData = getSprintEstimateTimeGraficoData(sprintHours);
				sprintSpentTimeData = getSprintSpentTimeGraficoData(sprintHours);
				JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
				splitPane.setDividerSize(1);
				splitPane.setDividerLocation(500);
				splitPane.setLeftComponent(criarGrafico(sprintHours.getSprint() + " -Horas Estimadas", "Membro da equipe", "Horas",
						sprintEstimateTimeData));
				splitPane.setRightComponent(criarGrafico(sprintHours.getSprint() + " -Horas Usadas", "Membro da equipe", "Horas", sprintSpentTimeData));
				abas.addTab(sprintHours.getSprint(),splitPane);
			}
		}
		
		setVisible(true);
	}
	public GraficoBarra(String title,String xLabel,String yLabel, List<SprintHours> sprintHoursList ,double custoHora) {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle(title);
		setSize(550, 550);
		setLocationRelativeTo(null);
		add(BorderLayout.CENTER,abas);
		
		//adicionar aba com grafico do projeto
		List<GraficoData> projetoData = new ArrayList<GraficoData>();
		projetoData = getProjectPagamentoGraficoData(sprintHoursList,custoHora);
		abas.addTab("Projeto", criarGrafico("Pagamento Total", "Membro da equipe", "Pagamento", projetoData));
		
		//adicionar abas com grafico dos sprints
		for(SprintHours sprintHours : sprintHoursList) {
			if (sprintHours.hasSpentTime()) {
				List<GraficoData> sprintPagamentoData = new ArrayList<GraficoData>();
				sprintPagamentoData = getSprintPagamentoGraficoData(sprintHours,custoHora);
				abas.addTab(sprintHours.getSprint(),criarGrafico(sprintHours.getSprint(), "Membro da equipe", "Pagamento", sprintPagamentoData));
			}
		}
		
		setVisible(true);
	}

	private ChartPanel criarGrafico(String title,String xLabel,String yLabel, List<GraficoData> data) {
		DefaultCategoryDataset barra = new  DefaultCategoryDataset();
		for(GraficoData d: data) {
			barra.setValue(d.yData, d.xData, "");
		}
		JFreeChart grafico = ChartFactory.createBarChart(title, xLabel, yLabel, barra, PlotOrientation.VERTICAL, true, true, true);
		ChartPanel chartPanel = new ChartPanel(grafico);
		return chartPanel;
	}
	
	private List<GraficoData> getProjectHorasGraficoData(List<SprintHours> sprintHoursList) {

		List<GraficoData> projetoData = new ArrayList<GraficoData>();

		for (SprintHours sprintHours : sprintHoursList) {
			if (sprintHours.hasSpentTime()) {
				for (Hours hours : sprintHours.getHours()) {
					int control = 0;
					for (GraficoData graficoData : projetoData) {
						if (graficoData.getXData().equals(hours.getUser())) {
							graficoData.addYData(hours.getSpentTime());
							control = 1;
							break;
						}
					}
					if (control == 0)
						projetoData.add(new GraficoData(hours.getUser(), hours.getSpentTime()));
				}
			}
		}

		return projetoData;
	}

	private List<GraficoData> getProjectPagamentoGraficoData(List<SprintHours> sprintHoursList,double custoHora) {

		List<GraficoData> projetoData = new ArrayList<GraficoData>();

		for (SprintHours sprintHours : sprintHoursList) {
			if (sprintHours.hasSpentTime()) {
				for (Hours hours : sprintHours.getHours()) {
					int control = 0;
					for (GraficoData graficoData : projetoData) {
						if (graficoData.getXData().equals(hours.getUser())) {
							graficoData.addYData(hours.getSpentTime()*custoHora);
							control = 1;
							break;
						}
					}
					if (control == 0)
						projetoData.add(new GraficoData(hours.getUser(), hours.getSpentTime()*custoHora));
				}
			}
		}

		return projetoData;
	}
	
	private List<GraficoData> getSprintEstimateTimeGraficoData(SprintHours sprintHours) {

		List<GraficoData> sprintEstimateTimeData = new ArrayList<GraficoData>();

		for (Hours hours : sprintHours.getHours()) {
			sprintEstimateTimeData.add(new GraficoData(hours.getUser(), hours.getEstimateTime()));
		}

		return sprintEstimateTimeData;
	}

	private List<GraficoData> getSprintSpentTimeGraficoData(SprintHours sprintHours) {

		List<GraficoData> sprintSpentTimeData = new ArrayList<GraficoData>();

		for (Hours hours : sprintHours.getHours()) {
			sprintSpentTimeData.add(new GraficoData(hours.getUser(), hours.getSpentTime()));
		}

		return sprintSpentTimeData;
	}
	
	private List<GraficoData> getSprintPagamentoGraficoData(SprintHours sprintHours,double custoHora) {

		List<GraficoData> sprintSpentTimeData = new ArrayList<GraficoData>();

		for (Hours hours : sprintHours.getHours()) {
			sprintSpentTimeData.add(new GraficoData(hours.getUser(), hours.getSpentTime()*custoHora));
		}

		return sprintSpentTimeData;
	}
	
	
	
}
