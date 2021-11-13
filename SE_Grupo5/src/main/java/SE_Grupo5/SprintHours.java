package SE_Grupo5;

import java.util.ArrayList;
import java.util.List;

class Hours {
	private String utilizador;
	private double timeSpent;
	private double timeEstimate;

	public Hours(String utilizador) {
		this.utilizador = utilizador;
		this.timeEstimate = 0;
		this.timeSpent = 0;
	}

	public void addTime(double spent, double estimate) {
		this.timeSpent += spent;
		this.timeEstimate += estimate;
	}
	public double getEstimateTime() {
		return timeEstimate;
	}
	public double getSpentTime() {
		return timeSpent;
	}

	public String getUser() {
		return this.utilizador;
	}

	public String getInfomation() {
		return utilizador + " has estimated " + timeEstimate + "hours and spent " + timeSpent + "hours!";
	}
}

class SprintHours {

	private String sprint = "";
	private List<Hours> hours = new ArrayList<Hours>();

	public SprintHours(String sprint) {
		this.sprint = sprint;
	}

	public String getSprint() {
		return this.sprint;
	}

	public List<Hours> getHours() {
		return this.hours;
	}
	
	public double getEstimateTime() {
		double time=0;
		for (Hours h : hours) {
			time +=h.getEstimateTime();
		}
		return time;
	}
	
	private double getSpentTime() {
		double time=0;
		for (Hours h : hours) {
			time +=h.getSpentTime();
		}
		return time;
	}
	public boolean hasSpentTime() {
		if(getSpentTime()!=0) return true;
		return false;
	}
	
	public String getInfomation() {

		String s = "";
		for (Hours h : hours) {
			s = s + "\n" + h.getInfomation();
		}

		return "On board " + sprint + ":" + s;
	}

}