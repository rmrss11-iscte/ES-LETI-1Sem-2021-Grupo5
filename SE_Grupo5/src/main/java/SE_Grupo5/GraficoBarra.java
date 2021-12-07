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
/**
 * Esta classe serve para criar uma frame com gráficos separados em tabs 
 * 
 * @author Andre Barroso
 */
public class GraficoBarra extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 124479714253861621L;
	JTabbedPane abas = new JTabbedPane();
	
	/**
	 * Cria uma frame com gráficos das Horas Estimadas e Utilizadas por sprint e membro da equipe
	 * Separando os sprints por tabs
	 * E ainda um gráfico com as horas utilizadas por membro no total do projeto 
	 * 
	 * @param title Representa o nome da frame
	 * @param xLabel Representa o nome do eixo dos X's
	 * @param yLabel Representa o nome do eixo dos Y's
	 * @param sprintHoursList Representa uma lista de SprintHours
	 */
	public GraficoBarra(String title,String xLabel,String yLabel, List<SprintHoursInformation> sprintHoursList) {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle(title);
		setSize(1000, 550);
		setLocationRelativeTo(null);
		add(BorderLayout.CENTER,abas);
		
		List<GraficoBarraData> projetoData = getProjectHorasGraficoData(sprintHoursList);
		abas.addTab("Projeto", criarGrafico("Horas de trabalho usadas no Projeto", xLabel, yLabel, projetoData));
		
		for(SprintHoursInformation sprintHours : sprintHoursList) {
			if (sprintHours.hasSpentTime()) {
				JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
				splitPane.setDividerSize(1);
				splitPane.setDividerLocation(500);
				splitPane.setLeftComponent(criarGrafico(sprintHours.getSprint() + " -Horas Estimadas", xLabel, yLabel,
						getSprintEstimateTimeGraficoData(sprintHours)));
				splitPane.setRightComponent(criarGrafico(sprintHours.getSprint() + " -Horas Usadas", xLabel, yLabel, getSprintSpentTimeGraficoData(sprintHours)));
				abas.addTab(sprintHours.getSprint(),splitPane);
			}
		}
		
		setVisible(true);
	}
	
	/**
	 * Cria uma frame com gráficos o valor a pagar por sprint e membro da equipe
	 * Separando os sprints por tabs
	 * E ainda um gráfico com o pagamento total por membro
	 * 
	 * @param title Representa o nome da frame
	 * @param xLabel Representa o nome do eixo dos X's
	 * @param yLabel Representa o nome do eixo dos Y's
	 * @param sprintHoursList Representa uma lista de SprintHours
	 */
	public GraficoBarra(String title,String xLabel,String yLabel, List<SprintHoursInformation> sprintHoursList ,double custoHora) {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle(title);
		setSize(550, 550);
		setLocationRelativeTo(null);
		add(BorderLayout.CENTER,abas);
		
		abas.addTab("Projeto", criarGrafico("Pagamento Total", xLabel, yLabel, getProjectPagamentoGraficoData(sprintHoursList,custoHora)));
		
		for(SprintHoursInformation sprintHours : sprintHoursList) {
			if (sprintHours.hasSpentTime()) {
				abas.addTab(sprintHours.getSprint(),criarGrafico(sprintHours.getSprint(), xLabel, yLabel, getSprintPagamentoGraficoData(sprintHours,custoHora)));
			}
		}
		
		setVisible(true);
	}
	
	/**
	 * Este método cria um ChartPanel que é criado com um gráfico em JFreeChart 
	 * 
	 * @param title Representa o nome da frame
	 * @param xLabel Representa o nome do eixo dos X's
	 * @param yLabel Representa o nome do eixo dos Y's
	 * @param data Representa uma lista com a inforamções pra cada barra
	 * @return chartPanel
	 */
	private ChartPanel criarGrafico(String title,String xLabel,String yLabel, List<GraficoBarraData> data) {
		DefaultCategoryDataset barra = new  DefaultCategoryDataset();
		for(GraficoBarraData d: data) {
			barra.setValue(d.yData, d.xData, "");
		}
		JFreeChart grafico = ChartFactory.createBarChart(title, xLabel, yLabel, barra, PlotOrientation.VERTICAL, true, true, true);
		ChartPanel chartPanel = new ChartPanel(grafico);
		return chartPanel;
	}
	
	/**
	 * Este método dá return de uma lista de GraficoBarraData criada 
	 * Através da lista de SprintHours dada nos parametros e contém
	 * As Horas utilizadas por cada membro no projeto
	 * 
	 * @param sprintHoursList Representa uma lista de SprintHours
	 * @return projetoData
	 */
	private List<GraficoBarraData> getProjectHorasGraficoData(List<SprintHoursInformation> sprintHoursList) {

		List<GraficoBarraData> projetoData = new ArrayList<GraficoBarraData>();

		for (SprintHoursInformation sprintHours : sprintHoursList) {
			if (sprintHours.hasSpentTime()) {
				for (MemberHoursInformation hours : sprintHours.getMemberHoursInformationList()) {
					int control = 0;
					for (GraficoBarraData graficoData : projetoData) {
						if (graficoData.getXData().equals(hours.getUser())) {
							graficoData.addYData(hours.getSpentTime());
							control = 1;
							break;
						}
					}
					if (control == 0)
						projetoData.add(new GraficoBarraData(hours.getUser(), hours.getSpentTime()));
				}
			}
		}

		return projetoData;
	}

	/**
	 * Este método dá return de uma lista de GraficoBarraData criada 
	 * Através da lista de SprintHours e do custo por hora a pagar dados nos parametros 
	 * E contém o valor total a se pagar por membro
	 * 
	 * @param sprintHoursList Representa uma lista de SprintHours
	 * @param custoHora Representa o valor a pagar por hora de trabalho
	 * @return projetoData
	 */	
	private List<GraficoBarraData> getProjectPagamentoGraficoData(List<SprintHoursInformation> sprintHoursList,double custoHora) {

		List<GraficoBarraData> projetoData = new ArrayList<GraficoBarraData>();

		for (SprintHoursInformation sprintHours : sprintHoursList) {
			if (sprintHours.hasSpentTime()) {
				for (MemberHoursInformation hours : sprintHours.getMemberHoursInformationList()) {
					int control = 0;
					for (GraficoBarraData graficoData : projetoData) {
						if (graficoData.getXData().equals(hours.getUser())) {
							graficoData.addYData(hours.getSpentTime()*custoHora);
							control = 1;
							break;
						}
					}
					if (control == 0)
						projetoData.add(new GraficoBarraData(hours.getUser(), hours.getSpentTime()*custoHora));
				}
			}
		}

		return projetoData;
	}
	
	/**
	 * Este método dá return de uma lista de GráficoBarraData
	 * Que contém as horas estimadas por membro no dado sprint 
	 * 
	 * @param sprintHours Contém os membros do sprint com as horas estimadas e utilizadas
	 * @return sprintEstimateTimeData
	 */
	private List<GraficoBarraData> getSprintEstimateTimeGraficoData(SprintHoursInformation sprintHours) {

		List<GraficoBarraData> sprintEstimateTimeData = new ArrayList<GraficoBarraData>();

		for (MemberHoursInformation hours : sprintHours.getMemberHoursInformationList()) {
			sprintEstimateTimeData.add(new GraficoBarraData(hours.getUser(), hours.getEstimateTime()));
		}

		return sprintEstimateTimeData;
	}

	/**
	 * Este método dá return de uma lista de GráficoBarraData
	 * Que contém as horas utilizadas por membro no dado sprint 
	 * 
	 * @param sprintHours Contém os membros do sprint com as horas estimadas e utilizadas
	 * @return sprintSpentTimeData
	 */
	private List<GraficoBarraData> getSprintSpentTimeGraficoData(SprintHoursInformation sprintHours) {

		List<GraficoBarraData> sprintSpentTimeData = new ArrayList<GraficoBarraData>();

		for (MemberHoursInformation hours : sprintHours.getMemberHoursInformationList()) {
			sprintSpentTimeData.add(new GraficoBarraData(hours.getUser(), hours.getSpentTime()));
		}

		return sprintSpentTimeData;
	}
	
	/**
	 * Este método dá return de uma lista de GráficoBarraData
	 * Que contém o valor a pagar por membro no dado sprint 
	 * 
	 * @param sprintHours Contém os membros do sprint com as horas estimadas e utilizadas
	 * @param custoHora Representa o valor a pagar por hora de trabalho
	 * @return sprintPagamentoData
	 */
	private List<GraficoBarraData> getSprintPagamentoGraficoData(SprintHoursInformation sprintHours,double custoHora) {

		List<GraficoBarraData> sprintPagamentoData = new ArrayList<GraficoBarraData>();

		for (MemberHoursInformation hours : sprintHours.getMemberHoursInformationList()) {
			sprintPagamentoData.add(new GraficoBarraData(hours.getUser(), hours.getSpentTime()*custoHora));
		}

		return sprintPagamentoData;
	}

}
