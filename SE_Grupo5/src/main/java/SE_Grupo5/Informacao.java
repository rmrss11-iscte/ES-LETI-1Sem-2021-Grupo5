package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Informacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Trello trelloApi;
	private String trelloUtilizador;
	private String membersList = "";

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
		membersLabel.setBounds(20, 88, 211, 43);
		contentPane.add(membersLabel);

		JTextArea membersDisplay = new JTextArea(getMembers());
		membersLabel.setLabelFor(membersDisplay);
		membersDisplay.setEditable(false);
		membersDisplay.setBounds(20, 129, 253, 152);
		contentPane.add(membersDisplay);
		
		JLabel nameDisplay = new JLabel(getNameofProject());
		nameDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		nameDisplay.setFont(new Font("Felix Titling", Font.BOLD, 20));
		nameDisplay.setBounds(221, 11, 575, 53);
		contentPane.add(nameDisplay);
	
	}

	private String getNameofProject() {
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Card> cards = trelloApi.getBoardCards(boards.get(0).getId());
		Card projectcard= trelloApi.getBoardCard(boards.get(0).getId(), cards.get(0).getId());

		String name = projectcard.getName();

		return name;
	}

	private String getMembers() {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Member> members = trelloApi.getBoardMembers(boards.get(0).getId());
		for(Member m: members) {
			membersList = membersList + m.getFullName()+"\n";
		}
		return membersList;
	}
}
