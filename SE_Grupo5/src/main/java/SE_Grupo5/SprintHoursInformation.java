package SE_Grupo5;

import java.util.ArrayList;
import java.util.List;
/**
 * Esta classe serve para armazenar as horas estimadas e utilizadas por um membro
 * 
 * @author Andre Barroso
 */
class MemberHoursInformation {
	private String utilizador;
	private double timeSpent;
	private double timeEstimate;
	
	/**
	 * Cria uma memberHoursInformation e 
	 * Inicia as horasa estimadas e utilizadas a zero
	 * 
	 * @param utilizador Representa o membro da equipe
	 */
	public MemberHoursInformation(String utilizador) {
		this.utilizador = utilizador;
		this.timeEstimate = 0;
		this.timeSpent = 0;
	}
	
	/**
	 * Este método serve para adicionar horas estimadas e utilizadas 
	 * 
	 * @param spent Representa as horas utilizadas
	 * @param estimate Representa as horas estimadas
	 */
	public void addTime(double spent, double estimate) {
		this.timeSpent += spent;
		this.timeEstimate += estimate;
	}
	
	/**
	 * Dá return das horas estimadas
	 * 
	 * @return timeEstimate
	 */
	public double getEstimateTime() {
		return timeEstimate;
	}
	
	/**
	 * Dá return das horas utilizadas
	 * 
	 * @return timeSpent
	 */
	public double getSpentTime() {
		return timeSpent;
	}

	/**
	 * Dá return do membro
	 * 
	 * @return utilizador
	 */
	public String getUser() {
		return this.utilizador;
	}

	/**
	 * Dá return duma String com o membro e as horas estimadas e gastas 
	 * 
	 * @return String
	 */
	public String getInfomation() {
		return utilizador + " has estimated " + timeEstimate + "hours and spent " + timeSpent + "hours!";
	}
}

/**
 * Esta classe serve para armazenar uma lista de memberHoursInformation
 * e o sprint em questão
 * 
 * @author Andre Barroso
 */
class SprintHoursInformation {
	
	/**
	 * Nome do sprint
	 */
	private String sprint = "";
	/**
	 * Lista de MemberHoursInformation
	 */
	private List<MemberHoursInformation> memberHoursInformationList = new ArrayList<MemberHoursInformation>();

	/**
	 * Cria um SprintHoursInformation do sprint dado como parametro
	 * 
	 * @param sprint Representa o nome do sprint
	 */
	public SprintHoursInformation(String sprint) {
		this.sprint = sprint;
	}

	/**
	 * Este método da return do nome do sprint
	 * 
	 * @return sprint
	 */
	public String getSprint() {
		return this.sprint;
	}

	/**
	 * Este método dá return da lista de MemberHoursInformation do sprint
	 * 
	 * @return memberHoursInformationList
	 */
	public List<MemberHoursInformation> getMemberHoursInformationList() {
		return this.memberHoursInformationList;
	}
	
	/**
	 * Este método dá return das horas estimadas para este sprint 
	 * 
	 * @return time
	 */
	public double getEstimateTime() {
		double time=0;
		for (MemberHoursInformation h : memberHoursInformationList) {
			time +=h.getEstimateTime();
		}
		return time;
	}
	
	/**
	 * Este método dá return das horas utilizadas neste sprint 
	 * 
	 * @return time
	 */
	private double getSpentTime() {
		double time=0;
		for (MemberHoursInformation h : memberHoursInformationList) {
			time +=h.getSpentTime();
		}
		return time;
	}
	
	/**Este método dá return True se já se trabalhou neste sprint, ou seja, 
	 * getSpenthours diferente de 0, False caso contrário
	 * 
	 * @return boolean
	 */
	public boolean hasSpentTime() {
		if(getSpentTime()!=0) return true;
		return false;
	}
	
	/**
	 * Este método da return duma String com o nome do sprint
	 * E as horas estimadas e utilizadas de cada membro
	 * 
	 * @return String
	 */
	public String getInfomation() {

		String s = "";
		for (MemberHoursInformation h : memberHoursInformationList) {
			s = s + "\n" + h.getInfomation();
		}

		return "On board " + sprint + ":" + s;
	}

}