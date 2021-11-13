package SE_Grupo5;

public class GraficoData {
	String xData;
	double yData;
	
	public GraficoData(String xData, double yData){
		this.xData=xData;
		this.yData=yData;
	}
	public void addYData(double yData) {
		this.yData += yData;
	}
	public double getYData() {
		return yData;
	}
	public String getXData() {
		return xData;
	}
}
