package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.kohsuke.github.GitHub;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import javax.swing.JTextPane;

public class Informacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5924931067800093425L;
	private JPanel contentPane = new JPanel();
	private Trello trelloApi;
	private String trelloUtilizador;
	private GitHub gitHubApi;
	private List<SprintHours> sHours = new ArrayList<SprintHours>();
	private JTextField txtNovoCustohora = new JTextField();;
	private JTable tabelaHoras;
	private JTable tabelaCusto;
	private double custoHora = 20;

	/**
	 * Create the frame.
	 */
	public Informacao(Trello trelloApi, String trelloUtilizador, GitHub gitHubApi) {

		this.trelloApi = trelloApi;
		this.trelloUtilizador = trelloUtilizador;
		this.gitHubApi = gitHubApi;

		getProjectTime();

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 952, 751);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel membersLabel = new JLabel("Membros do projeto:");
		membersLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		membersLabel.setBounds(10, 119, 211, 43);
		contentPane.add(membersLabel);

		JTextArea membersDisplay = new JTextArea(getMembers());
		membersLabel.setLabelFor(membersDisplay);
		membersDisplay.setEditable(false);
		membersDisplay.setBounds(20, 162, 253, 152);
		contentPane.add(membersDisplay);

		JLabel dataLabel = new JLabel("Data de Inicio:");
		dataLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		dataLabel.setBounds(10, 310, 170, 32);
		contentPane.add(dataLabel);

		JLabel productBacklogLabel = new JLabel("Items do ProductBacklog:");
		productBacklogLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		productBacklogLabel.setBounds(10, 409, 263, 28);
		contentPane.add(productBacklogLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(20, 437, 274, 179);
		contentPane.add(scrollPane);
		
				JTextArea productBacklogDisplay = new JTextArea(getProductBacklog());
				scrollPane.setViewportView(productBacklogDisplay);
				productBacklogLabel.setLabelFor(productBacklogDisplay);

		JScrollPane scrollPane_custo = new JScrollPane();
		scrollPane_custo.setBounds(444, 362, 445, 155);
		contentPane.add(scrollPane_custo);
		tabelaCusto = criarTabela(sHours, 20);
		scrollPane_custo.setViewportView(tabelaCusto);

		JLabel lblCustoHora = new JLabel("Custo-Hora =             €");
		lblCustoHora.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCustoHora.setBounds(587, 538, 203, 24);
		contentPane.add(lblCustoHora);
		txtNovoCustohora.setHorizontalAlignment(SwingConstants.CENTER);
		txtNovoCustohora.setFont(new Font("Tahoma", Font.PLAIN, 18));

		// txt Novo Custo-Hora
		txtNovoCustohora.setText("20");
		txtNovoCustohora.setBounds(699, 538, 67, 24);
		contentPane.add(txtNovoCustohora);
		txtNovoCustohora.setColumns(10);

		JButton buttonApplyNovoCustoHora = new JButton("Apply");
		buttonApplyNovoCustoHora.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setNovoCustoHora(Double.parseDouble(txtNovoCustohora.getText()));
			}
		});
		buttonApplyNovoCustoHora.setBounds(800, 538, 89, 24);
		contentPane.add(buttonApplyNovoCustoHora);

		JScrollPane scrollPane_Horas = new JScrollPane();
		scrollPane_Horas.setBounds(444, 160, 445, 155);
		contentPane.add(scrollPane_Horas);
		tabelaHoras = CriarTabela(sHours);
		scrollPane_Horas.setViewportView(tabelaHoras);

		JButton btnObterGraficoHoras = new JButton("Obter Gráficos das Horas de Trabalho");
		btnObterGraficoHoras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GraficoBarra("Horas de trabalho", "Membro da equipe", "Horas", sHours);
			}
		});
		btnObterGraficoHoras.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnObterGraficoHoras.setBounds(615, 573, 274, 43);
		contentPane.add(btnObterGraficoHoras);

		JButton btnObterGraficoCustos = new JButton("Obter Gráficos dos Custos do Trabalho");
		btnObterGraficoCustos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GraficoBarra("Custos do Projeto", "Membro da equipe", "Euros", sHours, custoHora);
			}
		});
		btnObterGraficoCustos.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnObterGraficoCustos.setBounds(615, 627, 274, 43);
		contentPane.add(btnObterGraficoCustos);
		
		JLabel lblTabelaDeHoras = new JLabel("Tabela de horas previstas/usadas");
		lblTabelaDeHoras.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTabelaDeHoras.setBounds(412, 119, 324, 43);
		contentPane.add(lblTabelaDeHoras);
		
		JLabel lblTabelaDeCustos = new JLabel("Tabela de custos");
		lblTabelaDeCustos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTabelaDeCustos.setBounds(412, 326, 305, 43);
		contentPane.add(lblTabelaDeCustos);
		
		JTextArea textDate = new JTextArea(getDate());
		textDate.setBounds(20, 338, 211, 49);
		contentPane.add(textDate);

		

	}

	private void setNovoCustoHora(double novoCustoHora) {
		int row = 0;
		while (row < tabelaCusto.getRowCount()) {
			double newValue = ((Number) tabelaCusto.getValueAt(row, 2)).doubleValue() * (novoCustoHora / custoHora);
			tabelaCusto.setValueAt(newValue, row, 2);
			row++;
		}
		custoHora = novoCustoHora;
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

		String productBacklogList = "";
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

	private JTable CriarTabela(List<SprintHours> sprintHoursList) {
		String[] colunas = { "Sprint", "User", "Horas previstas", "Horas usadas" };
		int numberOfLines = 0;
		for (SprintHours sH : sprintHoursList) {
			if (sH.hasSpentTime())
				numberOfLines += sH.getHours().size();
		}
		Object[][] dados = new Object[numberOfLines + 1][4];
		int line = 1;
		double estimate = 0;
		double spent = 0;
		for (SprintHours sH : sprintHoursList) {
			if (sH.hasSpentTime()) {
				for (Hours h : sH.getHours()) {
					dados[line][0] = sH.getSprint();
					dados[line][1] = h.getUser();
					dados[line][2] = h.getEstimateTime();
					dados[line][3] = h.getSpentTime();
					estimate += h.getEstimateTime();
					spent += h.getSpentTime();
					line++;
				}
			}
		}

		dados[0][0] = "Projeto";
		dados[0][1] = "Global";
		dados[0][2] = estimate;
		dados[0][3] = spent;

		JTable tabela = new JTable(dados, colunas);
		tabela.setBackground(UIManager.getColor("Button.light"));
		return tabela;
	}

	private JTable criarTabela(List<SprintHours> sprintHoursList, double custoHora) {
		String[] colunas = { "Sprint", "User", "Pagamento" };

		int numberOfLines = 0;
		for (SprintHours sH : sprintHoursList) {
			if (sH.hasSpentTime())
				numberOfLines += sH.getHours().size();
		}
		Object[][] dados = new Object[numberOfLines + 1][3];
		int line = 1;
		double custo = 0;
		for (SprintHours sH : sprintHoursList) {
			if (sH.hasSpentTime()) {
				for (Hours h : sH.getHours()) {
					dados[line][0] = sH.getSprint();
					dados[line][1] = h.getUser();
					dados[line][2] = h.getSpentTime() * custoHora;
					custo += h.getSpentTime() * custoHora;
					line++;
				}
			}
		}
		dados[0][0] = "Projeto";
		dados[0][1] = "Global";
		dados[0][2] = custo;

		JTable tabela = new JTable(dados, colunas);
		tabela.setBackground(UIManager.getColor("Button.light"));
		return tabela;
	}
}
