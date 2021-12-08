package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.IOUtils;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;

import org.springframework.web.client.RestTemplate;

public class Informacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5924931067800093425L;

	JTabbedPane abas = new JTabbedPane();

	private Trello trelloApi;
	private String trelloUtilizador;
	private String repositoryName;
	private String repositoryOwner;
	private GitHub gitHubApi;

	private JPanel contentPane = new JPanel();
	private JPanel contentPane2 = new JPanel();
	private List<SprintHoursInformation> sHours = new ArrayList<SprintHoursInformation>();
	private JTextField txtNovoCustohora = new JTextField();;
	private JTable tabelaHoras;
	private JTable tabelaCusto;
	private double custoHora = 20;

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * 
	 * @param trelloApi        Representa a conexão ao trello
	 * @param trelloUtilizador Representa o user no trello do Utilizador
	 * @param gitHubApi        Representa a conexão ao GitHub
	 * @param repositoryOwner  Representa o nome do dono do repositório GitHub
	 * @param repositoryName   Representa o nome do repositório GitHub
	 */

	public Informacao(Trello trelloApi, String trelloUtilizador, GitHub gitHubApi, String repositoryOwner,
			String repositoryName) throws IOException {

		this.trelloApi = trelloApi;
		this.trelloUtilizador = trelloUtilizador;
		this.gitHubApi = gitHubApi;
		this.repositoryName = repositoryName;
		this.repositoryOwner = repositoryOwner;

		getProjectTime();

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 900, 750);
		getContentPane().add(BorderLayout.CENTER, abas);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane2.setBackground(Color.WHITE);
		contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane2.setLayout(null);

		abas.addTab("Informação Principal", contentPane);
		abas.addTab("Inf. Adicional", contentPane2);

		JLabel lblMembers = new JLabel("Membros do projeto:");
		lblMembers.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMembers.setBounds(10, 62, 200, 43);
		contentPane.add(lblMembers);

		JTextArea jTextMembers = new JTextArea(getMembers());
		lblMembers.setLabelFor(jTextMembers);
		jTextMembers.setEditable(false);
		jTextMembers.setBounds(20, 105, 232, 91);
		contentPane.add(jTextMembers);

		JLabel lblDataFinal = new JLabel("Data de Fim:");
		lblDataFinal.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDataFinal.setBounds(10, 207, 160, 30);
		contentPane.add(lblDataFinal);

		JTextArea jTextDataFinal = new JTextArea(getDate());
		lblDataFinal.setLabelFor(jTextDataFinal);
		jTextDataFinal.setEditable(false);
		jTextDataFinal.setBounds(20, 238, 232, 20);
		contentPane.add(jTextDataFinal);

		JLabel lblSprintsDuration = new JLabel("Duração de cada Sprint:");
		lblSprintsDuration.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSprintsDuration.setBounds(10, 274, 242, 34);
		contentPane.add(lblSprintsDuration);

		JTextArea jTextSprintsDuration = new JTextArea(getSprintsDuration());
		lblSprintsDuration.setLabelFor(jTextSprintsDuration);
		jTextSprintsDuration.setEditable(false);
		jTextSprintsDuration.setBounds(20, 310, 290, 100);
		contentPane.add(jTextSprintsDuration);

		JScrollPane scrollPaneLinksTexts = new JScrollPane();
		scrollPaneLinksTexts.setBounds(30, 265, 389, 165);
		contentPane2.add(scrollPaneLinksTexts);

		JTextArea jTextLinksTexts = new JTextArea(getAttachList());
		scrollPaneLinksTexts.setViewportView(jTextLinksTexts);

		JLabel lblProductBacklog = new JLabel("Items do ProductBacklog:");
		lblProductBacklog.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProductBacklog.setBounds(20, 11, 334, 28);
		contentPane2.add(lblProductBacklog);

		JScrollPane scrollPaneProductBackLog = new JScrollPane();
		scrollPaneProductBackLog.setViewportBorder(null);
		scrollPaneProductBackLog.setBounds(30, 50, 389, 165);
		contentPane2.add(scrollPaneProductBackLog);

		JTextArea jTextProductBacklog = new JTextArea(getProductBacklog());
		scrollPaneProductBackLog.setViewportView(jTextProductBacklog);
		jTextProductBacklog.setEditable(false);
		lblProductBacklog.setLabelFor(jTextProductBacklog);

		JScrollPane scrollPaneTabelaCusto = new JScrollPane();
		scrollPaneTabelaCusto.setBounds(422, 324, 445, 174);
		contentPane.add(scrollPaneTabelaCusto);
		tabelaCusto = criarTabela(sHours, 20);
		scrollPaneTabelaCusto.setViewportView(tabelaCusto);

		JLabel lblCustoHora = new JLabel("Custo-Hora =             €");
		lblCustoHora.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCustoHora.setBounds(574, 509, 203, 24);
		contentPane.add(lblCustoHora);

		txtNovoCustohora.setHorizontalAlignment(SwingConstants.CENTER);
		txtNovoCustohora.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtNovoCustohora.setText("20");
		txtNovoCustohora.setBounds(688, 509, 67, 24);
		contentPane.add(txtNovoCustohora);
		txtNovoCustohora.setColumns(10);

		JButton buttonApplyNovoCustoHora = new JButton("Apply");
		buttonApplyNovoCustoHora.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setNovoCustoHora(Double.parseDouble(txtNovoCustohora.getText()));
			}
		});
		buttonApplyNovoCustoHora.setBounds(778, 509, 89, 24);
		contentPane.add(buttonApplyNovoCustoHora);

		JScrollPane scrollPaneTabelaHoras = new JScrollPane();
		scrollPaneTabelaHoras.setBounds(422, 103, 445, 174);
		contentPane.add(scrollPaneTabelaHoras);
		tabelaHoras = CriarTabela(sHours);
		scrollPaneTabelaHoras.setViewportView(tabelaHoras);

		JButton btnObterGraficoHoras = new JButton("Obter Gráficos das Horas de Trabalho");
		btnObterGraficoHoras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GraficoBarra("Horas de trabalho", "Membro da equipe", "Horas", sHours);
			}
		});
		btnObterGraficoHoras.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnObterGraficoHoras.setBounds(593, 544, 274, 43);
		contentPane.add(btnObterGraficoHoras);

		JButton btnObterGraficoCustos = new JButton("Obter Gráficos dos Custos do Trabalho");
		btnObterGraficoCustos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GraficoBarra("Custos do Projeto", "Membro da equipe", "Euros", sHours, custoHora);
			}
		});
		btnObterGraficoCustos.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnObterGraficoCustos.setBounds(593, 598, 274, 43);
		contentPane.add(btnObterGraficoCustos);

		JLabel lblTabelaDeHoras = new JLabel("Tabela de horas previstas/usadas");
		lblTabelaDeHoras.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTabelaDeHoras.setBounds(393, 62, 324, 43);
		contentPane.add(lblTabelaDeHoras);

		JLabel lblTabelaDeCustos = new JLabel("Tabela de custos");
		lblTabelaDeCustos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTabelaDeCustos.setBounds(393, 280, 305, 43);
		contentPane.add(lblTabelaDeCustos);

		String projectName = getNameofProject();
		JLabel lblProjectName = new JLabel(projectName);
		lblProjectName.setHorizontalAlignment(SwingConstants.CENTER);
		lblProjectName.setFont(new Font("Felix Titling", Font.BOLD, 25));
		lblProjectName.setBounds(0, 11, 879, 53);
		contentPane.add(lblProjectName);

		JLabel lblREADME = new JLabel("Conteúdo do ficheiro README:");
		lblREADME.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblREADME.setBounds(10, 421, 300, 30);
		contentPane.add(lblREADME);

		JScrollPane scrollPaneREADME = new JScrollPane();
		scrollPaneREADME.setViewportBorder(null);
		scrollPaneREADME.setBounds(20, 462, 324, 179);
		contentPane.add(scrollPaneREADME);

		try {
			JTextArea jTextREADME = new JTextArea(getREADME());
			jTextREADME.setEditable(false);
			scrollPaneREADME.setViewportView(jTextREADME);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		JLabel lblLinksTexts = new JLabel("Links para os textos das reuniões:");
		lblLinksTexts.setLabelFor(scrollPaneLinksTexts);
		lblLinksTexts.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLinksTexts.setBounds(20, 226, 334, 28);
		contentPane2.add(lblLinksTexts);

		JScrollPane scrollPaneOriginaArtefactos = new JScrollPane();
		scrollPaneOriginaArtefactos.setViewportBorder(null);
		scrollPaneOriginaArtefactos.setBounds(480, 50, 389, 165);
		contentPane2.add(scrollPaneOriginaArtefactos);

		JTextArea jTextOriginaArtefactos = new JTextArea(getActivitiesHoursCost());
		jTextOriginaArtefactos.setEditable(false);
		scrollPaneOriginaArtefactos.setViewportView(jTextOriginaArtefactos);

		JScrollPane scrollPaneNaoOriginaArtefactos = new JScrollPane();
		scrollPaneNaoOriginaArtefactos.setViewportBorder(null);
		scrollPaneNaoOriginaArtefactos.setBounds(480, 265, 389, 165);
		contentPane2.add(scrollPaneNaoOriginaArtefactos);

		JTextArea jTextNaoOriginaArtefactos = new JTextArea(notgetActivitiesHoursCost());
		jTextNaoOriginaArtefactos.setEditable(false);
		scrollPaneNaoOriginaArtefactos.setViewportView(jTextNaoOriginaArtefactos);

		JLabel lblNaoOriginaArtefactos = new JLabel("Atividades que não geraram artefactos:");
		lblNaoOriginaArtefactos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNaoOriginaArtefactos.setBounds(465, 226, 389, 28);
		contentPane2.add(lblNaoOriginaArtefactos);

		JLabel lblOriginaArtefactos = new JLabel("Atividades que geraram artefactos:");
		lblOriginaArtefactos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOriginaArtefactos.setBounds(465, 11, 334, 28);
		contentPane2.add(lblOriginaArtefactos);

		JLabel lblCommitsList = new JLabel("Lista de Commits:");
		lblCommitsList.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCommitsList.setBounds(20, 441, 334, 28);
		contentPane2.add(lblCommitsList);

		JScrollPane scrollPaneCommitsList = new JScrollPane();
		scrollPaneCommitsList.setBounds(30, 480, 389, 165);
		contentPane2.add(scrollPaneCommitsList);

		JTextArea jTextCommitsList = new JTextArea(commitsbydate());
		scrollPaneCommitsList.setViewportView(jTextCommitsList);

		JScrollPane scrollPaneTagList = new JScrollPane();
		scrollPaneTagList.setBounds(480, 480, 389, 165);
		contentPane2.add(scrollPaneTagList);

		JTextArea jTextTagList = new JTextArea((String) null);
		scrollPaneTagList.setViewportView(jTextTagList);

		JLabel lblTagList = new JLabel("Lista de Tags:");
		lblTagList.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTagList.setBounds(465, 441, 334, 28);
		contentPane2.add(lblTagList);

		witeToCSV(jTextMembers.getText(), tabelaHoras, tabelaCusto, jTextOriginaArtefactos.getText(),
				jTextNaoOriginaArtefactos.getText(), "commits");

	}

	private String getNameofProject() {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		Organization organization = trelloApi.getBoardOrganization(boards.get(0).getId());
		String name = organization.getDisplayName();
		return name;
	}

	/**
	 * Se o parametro por diferente do valor a se pagar por hora (custoHora) então
	 * Altera o valor a se pagar por hora e atualiza a tabela "tabelaCusto" com os
	 * novos custos
	 * 
	 * @param novoCustoHora Representa o novo valor a se pagar por Hora
	 */
	private void setNovoCustoHora(double novoCustoHora) {
		if (novoCustoHora == custoHora)
			return;
		int row = 0;
		while (row < tabelaCusto.getRowCount()) {
			double newValue = ((Number) tabelaCusto.getValueAt(row, 2)).doubleValue() * (novoCustoHora / custoHora);
			tabelaCusto.setValueAt(newValue, row, 2);
			row++;
		}
		custoHora = novoCustoHora;
	}

	/**
	 * Dá return de uma String que contém todos os membros do projeto separados por
	 * \n
	 * 
	 * @return String
	 */
	private String getMembers() {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Member> members = trelloApi.getBoardMembers(boards.get(0).getId());
		StringBuffer membersList = new StringBuffer();
		for (Member m : members) {
			membersList.append(m.getFullName() + "\n");
		}
		return membersList.toString();
	}

	/**
	 * Este método dá return da data de fim do projeto
	 * 
	 * @return String
	 */
	private String getDate() {
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Card> cards = trelloApi.getBoardCards(boards.get(0).getId());
		Card projectCard = trelloApi.getBoardCard(boards.get(0).getId(), cards.get(0).getId());
		Date dataInicio = projectCard.getDue();
		return dataInicio.toString();
	}

	private String getSprintsDuration() {
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<TList> lists = trelloApi.getBoardLists(boards.get(0).getId());
		List<Card> listCards = trelloApi.getListCards(lists.get(1).getId());
		StringBuffer sprintsduration = new StringBuffer();
		for (Card c : listCards) {
			sprintsduration.append(c.getName() + ": " + c.getDue() + "\n");
		}
		return sprintsduration.toString();
	}

	/**
	 * Este método dá return de uma String o Product Backlog
	 * 
	 * @return String
	 */
	private String getProductBacklog() {
		StringBuffer productBacklogList = new StringBuffer();
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		for (Board b : boards) {
			productBacklogList.append(b.getName() + ":" + "\n");
			List<TList> lists = trelloApi.getBoardLists(b.getId());
			for (TList l : lists) {
				if (!l.getName().contains("Planning") && !l.getName().contains("Review")
						&& !l.getName().contains("Retrospective") && !l.getName().contains("Scrum")
						&& !l.getName().contains("Project") && !l.getName().contains("Sprints")
						&& !l.getName().contains("Product Backlog")) {
					List<Card> listCards = trelloApi.getListCards(l.getId());
					for (Card c : listCards) {
						productBacklogList.append("-" + c.getName() + "\n");
					}
				}
			}
		}
		return productBacklogList.toString();
	}

	/**
	 * Vai ao trello e obtém as horas estimadas e utilizadas de cada membro por
	 * sprint Armazenando-as numa lista de SprintHours
	 */
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
				sHours.add(new SprintHoursInformation(boardName));
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

								String username;
								int doublesPosition = 1;
								if (dataText[1].contains("@")) {
									String[] temp = dataText[1].split("@");
									username = temp[1];
									doublesPosition = 2;
								} else {
									username = trelloApi.getActionMemberCreator(a.getId()).getUsername();
								}
								if (!username.equals("global")) {
									String fullName = trelloApi.getMemberInformation(username).getFullName();
									if (fullName == null)
										fullName = "Luis Figueira";
									String[] doubles = dataText[doublesPosition].split("/");

									int control = 0;
									while (control < sHours.get(control1).getMemberHoursInformationList().size()) {
										if (sHours.get(control1).getMemberHoursInformationList().get(control).getUser()
												.equals(fullName)) {
											break;
										}
										control++;
									}
									if (sHours.get(control1).getMemberHoursInformationList().size() == control) {
										sHours.get(control1).getMemberHoursInformationList()
												.add(new MemberHoursInformation(fullName));
									}
									sHours.get(control1).getMemberHoursInformationList().get(control)
											.addTime(Double.parseDouble(doubles[0]), Double.parseDouble(doubles[1]));
								}
							}
						}
					}
				}
			}
		}
	}

	String AttachmentsList = "";

	private String getAttachList() throws IOException {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);

		for (Board b : boards) {
			List<Card> cards = trelloApi.getBoardCards(b.getId());
			for (Card c : cards) {
				List<Attachment> listAttach = trelloApi.getCardAttachments(c.getId());
				for (Attachment a : listAttach) {
					if (a.getUrl().contains("Sprint"))
						AttachmentsList = AttachmentsList + a.getUrl() + "\n";

				}
			}
		}

		return AttachmentsList;
	}

	private String getActivitiesHoursCost() {
		List<Card> lista = new ArrayList<Card>();
		List<String[]> listauserartifactos = new ArrayList<String[]>();
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		for (Board b : boards) {
			List<Card> cards = b.fetchCards();
			for (Card c : cards) {
				List<Label> labels = c.getLabels();
				for (Label l : labels) {
					if (l.getColor().equals("purple")) {
						lista.add(c);
						List<Member> listamembros = trelloApi.getCardMembers(c.getId());
						for (Member m : listamembros) {
							int control = 0;
							String fullName = m.getFullName();
							while (control < listauserartifactos.size()) {
								if (listauserartifactos.get(control)[0].equals(fullName)) {
									int tempo = Integer.parseInt(listauserartifactos.get(control)[1]);
									tempo++;
									listauserartifactos.get(control)[1] = String.valueOf(tempo);
									break;
								}
								control++;
							}
							if (control == listauserartifactos.size()) {
								String[] vetor = { fullName, "1" };
								listauserartifactos.add(vetor);
							}
						}

					}

				}
			}

		}

		List<MemberHoursInformation> listamemberhours = new ArrayList<MemberHoursInformation>();
		for (Card card : lista) {
			List<Action> listaacoes = trelloApi.getCardActions(card.getId());
			for (Action a : listaacoes) {
				if (a.getType().matches("commentCard")) {
					if (a.getData().getText().contains("plus!")) {
						String[] dataText = a.getData().getText().split(" ");
						if (dataText[1].contains("@")) {
							String[] user = dataText[1].split("@");
							String username = user[1];
							if (!username.equals("global")) {
								String fullName = trelloApi.getMemberInformation(username).getFullName();
								if (fullName == null)
									fullName = "Luis Figueira";
								String[] doubles = dataText[2].split("/");
								int control = 0;
								while (control < listamemberhours.size()) {
									if (listamemberhours.get(control).getUser().equals(fullName)) {
										break;

									}
									control++;
								}
								if (listamemberhours.size() == control) {

									listamemberhours.add(new MemberHoursInformation(fullName));
								}
								listamemberhours.get(control).addTime(Double.parseDouble(doubles[0]),
										Double.parseDouble(doubles[1]));
							}
						} else {
							String username = trelloApi.getActionMemberCreator(a.getId()).getUsername();
							if (!username.equals("global")) {
								String fullName = trelloApi.getMemberInformation(username).getFullName();
								if (fullName == null)
									fullName = "Luis Figueira";
								String[] doubles = dataText[1].split("/");
								int control = 0;
								while (control < listamemberhours.size()) {
									if (listamemberhours.get(control).getUser().equals(fullName)) {
										break;
									}
									control++;
								}
								if (listamemberhours.size() == control) {
									listamemberhours.add(new MemberHoursInformation(fullName));
								}
								listamemberhours.get(control).addTime(Double.parseDouble(doubles[0]),
										Double.parseDouble(doubles[1]));
							}
						}
					}
				}
			}
		}
		StringBuffer info = new StringBuffer();
		int spentTime = 0;
		for (MemberHoursInformation m : listamemberhours) {
			for (String[] s : listauserartifactos) {
				if (m.getUser().equals(s[0])) {
					info.append("->O utilizador " + m.getUser() + " originou " + s[1]
							+ " artifactos no repositório \ne gastou " + m.getSpentTime() + " horas "
							+ " o que dá um custo total de: " + m.getSpentTime() * custoHora + " euros\n");
					spentTime += m.getSpentTime();
					break;
				}

			}

		}
		String retorno = "->Neste projeto foram originados " + lista.size() + " artifactos, \ngastando-se " + spentTime
				+ " horas, o que dá um custo total de: " + spentTime * custoHora + " euros\n" + info.toString();
		return retorno;
	}

	private String notgetActivitiesHoursCost() {
		List<Card> lista = new ArrayList<Card>();
		List<String[]> listauserartifactos = new ArrayList<String[]>();
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		for (Board b : boards) {
			List<Card> cards = b.fetchCards();
			for (Card c : cards) {
				List<Label> labels = c.getLabels();
				for (Label l : labels) {
					if (l.getColor().equals("blue")) {
						lista.add(c);
						List<Member> listamembros = trelloApi.getCardMembers(c.getId());
						for (Member m : listamembros) {
							int control = 0;
							String fullName = m.getFullName();
							while (control < listauserartifactos.size()) {
								if (listauserartifactos.get(control)[0].equals(fullName)) {
									int tempo = Integer.parseInt(listauserartifactos.get(control)[1]);
									tempo++;
									listauserartifactos.get(control)[1] = String.valueOf(tempo);
									break;
								}
								control++;
							}
							if (control == listauserartifactos.size()) {
								String[] vetor = { fullName, "1" };
								listauserartifactos.add(vetor);
							}
						}

					}

				}
			}
		}

		List<MemberHoursInformation> listamemberhours = new ArrayList<MemberHoursInformation>();
		for (Card card : lista) {
			List<Action> listaacoes = trelloApi.getCardActions(card.getId());
			for (Action a : listaacoes) {
				if (a.getType().matches("commentCard")) {
					if (a.getData().getText().contains("plus!")) {
						String[] dataText = a.getData().getText().split(" ");
						if (dataText[1].contains("@")) {
							String[] user = dataText[1].split("@");
							String username = user[1];
							if (!username.equals("global")) {
								String fullName = trelloApi.getMemberInformation(username).getFullName();
								if (fullName == null)
									fullName = "Luis Figueira";
								String[] doubles = dataText[2].split("/");
								int control = 0;
								while (control < listamemberhours.size()) {
									if (listamemberhours.get(control).getUser().equals(fullName)) {
										break;

									}
									control++;
								}
								if (listamemberhours.size() == control) {

									listamemberhours.add(new MemberHoursInformation(fullName));
								}
								listamemberhours.get(control).addTime(Double.parseDouble(doubles[0]),
										Double.parseDouble(doubles[1]));
							}
						} else {
							String username = trelloApi.getActionMemberCreator(a.getId()).getUsername();
							if (!username.equals("global")) {
								String fullName = trelloApi.getMemberInformation(username).getFullName();
								if (fullName == null)
									fullName = "Luis Figueira";
								String[] doubles = dataText[1].split("/");
								int control = 0;
								while (control < listamemberhours.size()) {
									if (listamemberhours.get(control).getUser().equals(fullName)) {
										break;
									}
									control++;
								}
								if (listamemberhours.size() == control) {
									listamemberhours.add(new MemberHoursInformation(fullName));
								}
								listamemberhours.get(control).addTime(Double.parseDouble(doubles[0]),
										Double.parseDouble(doubles[1]));
							}
						}
					}
				}
			}
		}
		StringBuffer info = new StringBuffer();
		int spentTime = 0;
		for (MemberHoursInformation m : listamemberhours) {
			for (String[] s : listauserartifactos) {
				if (m.getUser().equals(s[0])) {
					info.append("->O utilizador " + m.getUser() + " originou " + s[1]
							+ " atividades que não deram origem a artifactos no repositório \ne gastou "
							+ m.getSpentTime() + " horas " + " o que dá um custo total de: "
							+ m.getSpentTime() * custoHora + " euros\n");
					spentTime += m.getSpentTime();
					break;
				}

			}

		}
		String retorno = "->Neste projeto foram originadas " + lista.size()
				+ " atividades que não deram origem a artifactos, \ngastando-se " + spentTime
				+ " horas, o que dá um custo total de: " + spentTime * custoHora + " euros\n" + info.toString();
		return retorno;
	}

	/**
	 * Dá return da conexao ao Trello
	 * 
	 * @return Trello
	 */
	public Trello getTrelloApi() {
		return trelloApi;
	}

	/**
	 * Dá return de uma String que representa o user no trello do Utilizador
	 * 
	 * @return String
	 */
	public String getTrelloUtilizador() {
		return trelloUtilizador;
	}

	/**
	 * Dá return da lista de SprintHours
	 * 
	 * @return List<SprintHours>
	 */
	public List<SprintHoursInformation> getSprintHours() {
		return sHours;
	}

	/**
	 * Cria uma tabela do tipo JTable com as horas previstas e utilizadas por membro
	 * da equipe e por sprint e as horas previstas e utilizadas do projeto
	 * 
	 * @param sprintHoursList Representa uma lista de SprintHours
	 * 
	 * @return JTable
	 */
	private JTable CriarTabela(List<SprintHoursInformation> sprintHoursList) {
		String[] colunas = { "Sprint", "User", "Horas previstas", "Horas usadas" };
		int numberOfLines = 0;
		for (SprintHoursInformation sH : sprintHoursList) {
			if (sH.hasSpentTime())
				numberOfLines += sH.getMemberHoursInformationList().size();
		}
		Object[][] dados = new Object[numberOfLines + 1][4];
		int line = 1;
		double estimate = 0;
		double spent = 0;
		for (SprintHoursInformation sH : sprintHoursList) {
			if (sH.hasSpentTime()) {
				for (MemberHoursInformation h : sH.getMemberHoursInformationList()) {
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

	/**
	 * Cria uma tabela do tipo JTable com os pagamentos por membro de equipe e por
	 * sprint e custo total do projeto
	 * 
	 * @param sprintHoursList Representa uma lista de SprintHours
	 * @param custoHora       Representa o valor a pagar por hora
	 * 
	 * @return JTable
	 */
	private JTable criarTabela(List<SprintHoursInformation> sprintHoursList, double custoHora) {
		String[] colunas = { "Sprint", "User", "Pagamento" };

		int numberOfLines = 0;
		for (SprintHoursInformation sH : sprintHoursList) {
			if (sH.hasSpentTime())
				numberOfLines += sH.getMemberHoursInformationList().size();
		}
		Object[][] dados = new Object[numberOfLines + 1][3];
		int line = 1;
		double custo = 0;
		for (SprintHoursInformation sH : sprintHoursList) {
			if (sH.hasSpentTime()) {
				for (MemberHoursInformation h : sH.getMemberHoursInformationList()) {
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

	/**
	 * Este método dá return do conteúdo no ficheiro README.md presente na branch
	 * 'main' do repositório GitHub
	 * 
	 * @return String
	 */

	private String getREADME() throws IOException, URISyntaxException {

		StringBuffer readMEContent = new StringBuffer();

		/*
		 * Call GitHub REST API - https://developer.github.com/v3/repos/contents/
		 * 
		 * Using Spring's RestTemplate to simplify REST call. Any other REST client
		 * library can be used here.
		 */
		RestTemplate restTemplate = new RestTemplate();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Map> response = restTemplate.getForObject(
				"https://api.github.com/repos/{owner}/{repo}/contents?ref={branch}", List.class, repositoryOwner,
				repositoryName, "main");

		// Iterate through list of file metadata from response.
		for (@SuppressWarnings("rawtypes")
		Map fileMetaData : response) {

			// Get file name & raw file download URL from response.
			String downloadUrl = (String) fileMetaData.get("download_url");

			// We will only fetch read me file for this example.
			if (downloadUrl != null && downloadUrl.contains("README")) {
				/*
				 * Get file content as string
				 * 
				 * Using Apache commons IO to read content from the remote URL. Any other HTTP
				 * client library can be used here.
				 */
				String fileContent = IOUtils.toString(new URI(downloadUrl), Charset.defaultCharset());
				readMEContent.append(fileContent);
			}

		}
		return readMEContent.toString();
	}

	public void witeToCSV(String membros, JTable horas, JTable custo, String geraram, String naoGeraram,
			String commits) {

		StringBuffer buffer = new StringBuffer();
		String[] members = membros.split("\n");
		for (String m : members) {
			buffer.append(m + ";");
			double e = 0;
			double s = 0;
			for (int row = 0; row < horas.getRowCount(); row++) {
				String memberRow = ((String) horas.getValueAt(row, 1));
				if (memberRow.equals(m)) {
					e += ((double) horas.getValueAt(row, 2));
					s += ((double) horas.getValueAt(row, 3));
				}
			}
			buffer.append(e + ";" + s + ";");

			double c = 0;
			for (int row = 0; row < custo.getRowCount(); row++) {
				String memberRow = ((String) horas.getValueAt(row, 1));
				if (memberRow.equals(m)) {
					c += ((double) custo.getValueAt(row, 2));
				}
			}
			buffer.append(c + ";");

			String nrAtv = "";
			String[] gerou = geraram.split("->O utilizador ");
			for (int i = 1; i < gerou.length; i++) {
				String[] user = gerou[i].split(" originou ");
				if (user[0].equals(m)) {
					String[] temp = user[1].split(" artifactos");
					nrAtv = temp[0];
				}
			}
			buffer.append(nrAtv + ";");

			String nrAtv2 = "";
			String[] nGerou = naoGeraram.split("->O utilizador ");
			for (int i = 1; i < nGerou.length; i++) {
				String[] user = nGerou[i].split(" originou ");
				if (user[0].equals(m)) {
					String[] temp = user[1].split(" atividades ");
					nrAtv2 = temp[0];
				}
			}
			buffer.append(nrAtv2 + ";\n");

		}

		try {
			FileWriter writer = new FileWriter("Information.csv");
			writer.write(buffer.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String commitsbydate() {
		// BString last="";
		StringBuffer last = new StringBuffer();

		try {
			String path = repositoryOwner + "/" + repositoryName;

			GHRepository repositorio = gitHubApi.getRepository(path);
			PagedIterable<GHCommit> commit = repositorio.listCommits();

			for (GHCommit cc : commit) {

				GHUser committer = cc.getCommitter();

				if (committer != null) {

					String name = committer.getName();
					String commitDate = cc.getCommitDate().toString();
					String commitname = cc.getCommitShortInfo().getMessage();
					last.append(
							"commiter: " + name + " commited at: " + commitDate + " commit name: " + commitname + "\n");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(last);
		return last.toString();

	}
}