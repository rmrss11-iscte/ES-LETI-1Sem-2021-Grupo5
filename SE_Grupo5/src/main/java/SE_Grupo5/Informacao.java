package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.kohsuke.github.GitHub;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Informacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5924931067800093425L;
	private JPanel contentPane;
	private Trello trelloApi;
	private String trelloUtilizador;
	private GitHub gitHubApi;
	private List<SprintHours> sHours = new ArrayList<SprintHours>();

	/**
	 * Create the frame.
	 */
	public Informacao(Trello trelloApi, String trelloUtilizador, GitHub gitHubApi) {

		this.trelloApi = trelloApi;
		this.trelloUtilizador = trelloUtilizador;
		this.gitHubApi = gitHubApi;

		getProjectTime();

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 1033, 634);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel membersLabel = new JLabel("Membros do projeto:");
		membersLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		membersLabel.setBounds(10, 45, 211, 43);
		contentPane.add(membersLabel);

		JTextArea membersDisplay = new JTextArea(getMembers());
		membersLabel.setLabelFor(membersDisplay);
		membersDisplay.setEditable(false);
		membersDisplay.setBounds(20, 82, 253, 152);
		contentPane.add(membersDisplay);

		JLabel dataLabel = new JLabel("Data de Inicio:");
		dataLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dataLabel.setBounds(10, 245, 170, 32);
		contentPane.add(dataLabel);

		JLabel dataDisplay = new JLabel(getDate());
		dataLabel.setLabelFor(dataDisplay);
		dataDisplay.setBounds(10, 276, 305, 72);
		contentPane.add(dataDisplay);

		JLabel productBacklogLabel = new JLabel("Items do ProductBacklog:");
		productBacklogLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		productBacklogLabel.setBounds(10, 359, 211, 28);
		contentPane.add(productBacklogLabel);

		JTextArea productBacklogDisplay = new JTextArea(getProductBacklog());
		productBacklogLabel.setLabelFor(productBacklogDisplay);
		productBacklogDisplay.setBounds(10, 398, 274, 152);
		contentPane.add(productBacklogDisplay);
		JScrollPane scrollPane = CriarTabela(sHours);
		scrollPane.setBounds(562, 45, 445, 152);
		contentPane.add(scrollPane);

		criarGraficos();

	}

	private void criarGraficos() {
		List<GraficoData> projetoData = new ArrayList<GraficoData>();
		projetoData = getProjectGraficoData();
		new GraficoBarra("Horas de trabalho usadas no Projeto", "Membro da equipe", "Horas", projetoData);

		for (SprintHours s : sHours) {
			if (s.hasSpentTime()) {
				List<GraficoData> sprintEstimateTimeData = new ArrayList<GraficoData>();
				List<GraficoData> sprintSpentTimeData = new ArrayList<GraficoData>();
				sprintEstimateTimeData = getSprintEstimateTimeGraficoData(s);
				sprintSpentTimeData = getSprintSpentTimeGraficoData(s);
				new GraficoBarra(s.getSprint() + " -Horas Estimadas", "Membro da equipe", "Horas",
						sprintEstimateTimeData);
				new GraficoBarra(s.getSprint() + " -Horas Usadas", "Membro da equipe", "Horas", sprintSpentTimeData);
			}
		}
	}

	private String getMembers() {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Member> members = trelloApi.getBoardMembers(boards.get(0).getId());
		String membersList = "";
		for (Member m : members) {
			membersList = membersList + m.getFullName() + "\n";
		}
		return membersList;
	}

	private String getDate() {
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Card> cards = trelloApi.getBoardCards(boards.get(0).getId());
		Card projectCard = trelloApi.getBoardCard(boards.get(0).getId(), cards.get(0).getId());
		Date dataInicio = projectCard.getDue();
		return dataInicio.toString();
	}

	private String getProductBacklog() {
		
		String productBacklogList="";
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		for (Board b : boards) {
			List<TList> lists = trelloApi.getBoardLists(b.getId());
			for (TList l : lists) {

				if (!l.getName().contains("Planning") && !l.getName().contains("Review")
						&& !l.getName().contains("Retrospective") && !l.getName().contains("Scrum")) {
					List<Card> listCards = trelloApi.getListCards(l.getId());
					for (Card c : listCards) {
						productBacklogList += c.getName() + "\n";
					}
				}
			}
		}
		return productBacklogList;
	}
	private void getProjectTime() {
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		for (Board b : boards) {
			String boardName = b.getName();
			int control1 = 0;
			while (control1 < sHours.size()) {
				if (sHours.get(control1).getSprint().equals(boardName)) {
					break;
				}
				control1++;
			}
			if (sHours.size() == control1) {
				sHours.add(new SprintHours(boardName));
			}
			List<TList> lists = trelloApi.getBoardLists(b.getId());
			for (TList l : lists) {
				List<Card> cards = trelloApi.getListCards(l.getId());
				for (Card c : cards) {
					List<Action> actions = trelloApi.getCardActions(c.getId());
					for (Action a : actions) {
						if (a.getType().matches("commentCard")) {
							if (a.getData().getText().contains("plus!")) {
								String[] dataText = a.getData().getText().split(" ");

								if (dataText[1].contains("@")) {
									String[] user = dataText[1].split("@");
									String[] doubles = dataText[2].split("/");
									int control = 0;
									while (control < sHours.get(control1).getHours().size()) {
										if (sHours.get(control1).getHours().get(control).getUser().equals(user[1])) {
											break;
										}
										control++;
									}
									if (sHours.get(control1).getHours().size() == control) {
										sHours.get(control1).getHours().add(new Hours(user[1]));
									}
									sHours.get(control1).getHours().get(control).addTime(Double.parseDouble(doubles[0]),
											Double.parseDouble(doubles[1]));
								} else {
									String user = trelloApi.getActionMemberCreator(a.getId()).getUsername();
									String[] doubles = dataText[1].split("/");

									int control = 0;
									while (control < sHours.get(control1).getHours().size()) {
										if (sHours.get(control1).getHours().get(control).getUser().equals(user)) {
											break;
										}
										control++;
									}
									if (sHours.get(control1).getHours().size() == control) {
										sHours.get(control1).getHours().add(new Hours(user));
									}
									sHours.get(control1).getHours().get(control).addTime(Double.parseDouble(doubles[0]),
											Double.parseDouble(doubles[1]));
								}
							}
						}
					}
				}
			}
		}
	}

	public Trello getTrelloApi() {
		return trelloApi;
	}

	public String getTrelloUtilizador() {
		return trelloUtilizador;
	}

	public List<SprintHours> getSprintHours() {
		return sHours;
	}

	private List<GraficoData> getProjectGraficoData() {

		List<GraficoData> projetoData = new ArrayList<GraficoData>();

		for (SprintHours sprintHours : sHours) {
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

	private JScrollPane CriarTabela(List<SprintHours> sprintHoursList) {
		String[] colunas = { "Sprint", "User", "Horas previstas", "Horas usadas" };
		int numberOfLines = 0;
		for (SprintHours sH : sprintHoursList) {
			if (sH.hasSpentTime())
				numberOfLines += sH.getHours().size();
		}
		Object[][] dados = new Object[numberOfLines][4];
		int line = 0;
		for (SprintHours sH : sprintHoursList) {
			if (sH.hasSpentTime()) {
				for (Hours h : sH.getHours()) {
					dados[line][0] = sH.getSprint();
					dados[line][1] = h.getUser();
					dados[line][2] = h.getEstimateTime();
					dados[line][3] = h.getSpentTime();
					line++;
				}
			}
		}
		JTable tabela = new JTable(dados, colunas);
		JScrollPane jScrollPane = new JScrollPane(tabela);
		return jScrollPane;
	}

}
