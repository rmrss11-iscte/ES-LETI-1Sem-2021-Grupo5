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
import java.util.Date;

public class Informacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Trello trelloApi;
	private String trelloUtilizador;
	private String membersList = "";
	private String productBacklogList = "";

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
		
		JLabel dataLabel = new JLabel("Data de Inicio:");
		dataLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dataLabel.setBounds(10, 245, 170, 32);
		contentPane.add(dataLabel);
		
		JLabel dataDisplay = new JLabel(getDate());
		dataLabel.setLabelFor(dataDisplay);
		dataDisplay.setBounds(10, 276, 305, 72);
		contentPane.add(dataDisplay);
		
		JLabel productBacklogLabel= new JLabel("Items do ProductBacklog:");
		productBacklogLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		productBacklogLabel.setBounds(10, 359, 211, 28);
		contentPane.add(productBacklogLabel);
		
		JTextArea productBacklogDisplay = new JTextArea(getProductBacklog());
		productBacklogLabel.setLabelFor(productBacklogDisplay);
		productBacklogDisplay.setBounds(10, 398, 274, 152);
		contentPane.add(productBacklogDisplay);
	}
	
	private String getMembers() {
		
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Member> members = trelloApi.getBoardMembers(boards.get(0).getId());
		for(Member m: members) {
			membersList = membersList + m.getFullName()+"\n";
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
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<TList> lists = trelloApi.getBoardLists(boards.get(0).getId());	
		List<Card> listCards = trelloApi.getListCards(lists.get(2).getId());
		for(Card c: listCards) {
			productBacklogList = productBacklogList + c.getName()+"\n";
		}
		return productBacklogList;
		
	}
	}
	
	

	