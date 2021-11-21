package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.JTextArea;
import java.util.Date;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

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
	private String AttachmentsList = "";

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Informacao(Trello trelloApi, String trelloUtilizador) throws IOException {

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
		membersDisplay.setBounds(30, 129, 253, 80);
		contentPane.add(membersDisplay);


		JLabel dataLabel = new JLabel("Data de Inicio:");
		dataLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dataLabel.setBounds(20, 220, 205, 36);
		contentPane.add(dataLabel);

		JLabel dataDisplay = new JLabel(getDate());
		dataDisplay.setFont(new Font("Monospaced", Font.PLAIN, 13));
		dataDisplay.setVerticalAlignment(SwingConstants.TOP);
		dataLabel.setLabelFor(dataDisplay);
		dataDisplay.setBounds(30, 267, 235, 28);
		contentPane.add(dataDisplay);

		JLabel productBacklogLabel= new JLabel("Items do ProductBacklog:");
		productBacklogLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		productBacklogLabel.setBounds(20, 306, 221, 28);
		contentPane.add(productBacklogLabel);

		JTextArea productBacklogDisplay = new JTextArea(getProductBacklog());
		productBacklogLabel.setLabelFor(productBacklogDisplay);
		productBacklogDisplay.setBounds(30, 345, 274, 152);
		contentPane.add(productBacklogDisplay);

		JLabel nameDisplay = new JLabel(getNameofProject());
		nameDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		nameDisplay.setFont(new Font("Felix Titling", Font.BOLD, 25));
		nameDisplay.setBounds(221, 11, 575, 53);
		contentPane.add(nameDisplay);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(66, 431, 46, 14);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(385, 88, 221, 190);
		contentPane.add(scrollPane);

		JTextArea textos = new JTextArea(getAttachList());
		scrollPane.setViewportView(textos);




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

	private String getAttachList() throws IOException {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);

		for(Board b: boards) {
			List<Card> cards= trelloApi.getBoardCards(b.getId());
			for(Card c : cards) {
				List<Attachment> listAttach = trelloApi.getCardAttachments(c.getId());
				for(Attachment a: listAttach) {
					AttachmentsList = AttachmentsList + URLReader(a.getUrl());

				}
			}
		}


		return AttachmentsList;
	}

	public String URLReader(String s) throws IOException {
		URL url = new URL(s);
		StringBuilder sb = new StringBuilder();
		String line;
		InputStream in = url.openStream();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while ((line = reader.readLine()) != null) 
				sb.append(line).append(System.lineSeparator());
			
			} finally {
				in.close();
			}
		return sb.toString();
	}
}

