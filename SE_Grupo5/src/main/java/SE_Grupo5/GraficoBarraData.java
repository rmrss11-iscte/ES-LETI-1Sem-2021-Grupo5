package SE_Grupo5;

/**
 * Esta classe serve para armazenar a informação necessária 
 * Para criar as barras no gráfico de barras
 * 
 * @author Andre Barroso
 */


public class GraficoBarraData {
	/**
	 * Representa o nome da barra
	 */
	String xData;
	/**
	 * Representa o valor da barra
	 */
	double yData;
	/**
	 * Armazena a informação para uma barra
	 * 
	 * @param xData Representa o nome da barra
	 * @param yData Representa o valor da barra
	 */
	public GraficoBarraData(String xData, double yData){
		this.xData=xData;
		this.yData=yData;
	}
	/**
	 * Recebe um valor a adicionar ao atual valor da barra
	 * 
	 * @param yData Representa o valor a adicionar á barra
	 */
	public void addYData(double yData) {
		this.yData += yData;
	}
	/**
	 * Dá return do valor da barra
	 * 
	 * @return yData
	 */
	public double getYData() {
		return yData;
	}
	/**
	 * Dá return do nome da barra
	 * 
	 * @return xData
	 */
	public String getXData() {
		return xData;
	}
}
