package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JTextArea;

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

	public String getInfomation() {

		String s = "";
		for (Hours h : hours) {
			s = s + "\n" + h.getInfomation();
		}

		return "On board " + sprint + ":" + s;
	}

}

public class Informacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Trello trelloApi;
	private String trelloUtilizador;
	private String membersList = "";
	private List<SprintHours> sHours = new ArrayList<SprintHours>();

	/**
	 * Create the frame.
	 */
	public Informacao(Trello trelloApi, String trelloUtilizador) {

		this.trelloApi = trelloApi;
		this.trelloUtilizador = trelloUtilizador;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		getProjectTime();
		for(SprintHours sH: sHours) {
			System.out.println(sH.getInfomation());
		}
	}

	private String getMembers() {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Member> members = trelloApi.getBoardMembers(boards.get(0).getId());
		for (Member m : members) {
			membersList = membersList + m.getFullName() + "\n";
		}
		return membersList;
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
								System.out.println(a.getData().getText());
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

}
