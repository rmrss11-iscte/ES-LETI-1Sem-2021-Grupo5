package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.IOUtils;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
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
	
	private Trello trelloApi;
	private String trelloUtilizador;
	private String repositoryName;
	private String repositoryOwner;
	private GitHub gitHubApi;
	
	private JPanel contentPane = new JPanel();
	private List<SprintHoursInformation> sHours = new ArrayList<SprintHoursInformation>();
	private JTextField txtNovoCustohora = new JTextField();;
	private JTable tabelaHoras;
	private JTable tabelaCusto;
	private double custoHora = 20;

	
	

	/**
	 * Create the frame. <<<<<<< HEAD
	 * 
	 * @param trelloApi        Representa a conexão ao trello
	 * @param trelloUtilizador Representa o user no trello do Utilizador
	 * @param gitHubApi        Representa a conexão ao GitHub =======
	 * @throws IOException >>>>>>> refs/heads/main2
	 */

	public Informacao(Trello trelloApi, String trelloUtilizador, GitHub gitHubApi,String repositoryOwner, String repositoryName) throws IOException {

		this.trelloApi = trelloApi;
		this.trelloUtilizador = trelloUtilizador;
		this.gitHubApi = gitHubApi;
		this.repositoryName = repositoryName;
		this.repositoryOwner = repositoryOwner;

		getProjectTime();

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 952, 865);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMembers = new JLabel("Membros do projeto:");
		lblMembers.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMembers.setBounds(10, 20, 200, 43);
		contentPane.add(lblMembers);

		JTextArea jTextMembers = new JTextArea(getMembers());
		lblMembers.setLabelFor(jTextMembers);
		jTextMembers.setEditable(false);
		jTextMembers.setBounds(20, 65, 150, 90);
		contentPane.add(jTextMembers);

		JLabel lblDataFinal = new JLabel("Data de Fim:");
		lblDataFinal.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDataFinal.setBounds(10, 160, 160, 30);
		contentPane.add(lblDataFinal);

		JTextArea dataDisplay = new JTextArea(getDate());
		lblDataFinal.setLabelFor(dataDisplay);
		dataDisplay.setEditable(false);
		dataDisplay.setBounds(20, 190, 200, 20);
		contentPane.add(dataDisplay);

		JLabel lblSprintsDuration = new JLabel("Duração de cada Sprint:");
		lblSprintsDuration.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSprintsDuration.setBounds(10, 225, 242, 34);
		contentPane.add(lblSprintsDuration);

		JTextArea jTextSprintsDuration = new JTextArea(getSprintsDuration());
		lblSprintsDuration.setLabelFor(jTextSprintsDuration);
		jTextSprintsDuration.setEditable(false);
		jTextSprintsDuration.setBounds(20, 258, 253, 100);
		contentPane.add(jTextSprintsDuration);

		JLabel lblProductBacklog = new JLabel("Items do ProductBacklog:");
		lblProductBacklog.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProductBacklog.setBounds(10, 359, 263, 28);
		contentPane.add(lblProductBacklog);

		JScrollPane scrollPaneProductBackLog = new JScrollPane();
		scrollPaneProductBackLog.setViewportBorder(null);
		scrollPaneProductBackLog.setBounds(20, 387, 324, 179);
		contentPane.add(scrollPaneProductBackLog);
		
				JTextArea jTextProductBacklog = new JTextArea(getProductBacklog());
				jTextProductBacklog.setEditable(false);
				scrollPaneProductBackLog.setViewportView(jTextProductBacklog);
				lblProductBacklog.setLabelFor(jTextProductBacklog);

		JScrollPane scrollPaneTabelaCusto = new JScrollPane();
		scrollPaneTabelaCusto.setBounds(444, 308, 445, 155);
		contentPane.add(scrollPaneTabelaCusto);
		tabelaCusto = criarTabela(sHours, 20);
		scrollPaneTabelaCusto.setViewportView(tabelaCusto);

		JLabel lblCustoHora = new JLabel("Custo-Hora =             €");
		lblCustoHora.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCustoHora.setBounds(587, 474, 203, 24);
		contentPane.add(lblCustoHora);
		txtNovoCustohora.setHorizontalAlignment(SwingConstants.CENTER);
		txtNovoCustohora.setFont(new Font("Tahoma", Font.PLAIN, 18));

		// txt Novo Custo-Hora
		txtNovoCustohora.setText("20");
		txtNovoCustohora.setBounds(701, 474, 67, 24);
		contentPane.add(txtNovoCustohora);
		txtNovoCustohora.setColumns(10);

		JButton buttonApplyNovoCustoHora = new JButton("Apply");
		buttonApplyNovoCustoHora.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setNovoCustoHora(Double.parseDouble(txtNovoCustohora.getText()));
			}
		});
		buttonApplyNovoCustoHora.setBounds(800, 474, 89, 24);
		contentPane.add(buttonApplyNovoCustoHora);

		JScrollPane scrollPaneTabelaHoras = new JScrollPane();
		scrollPaneTabelaHoras.setBounds(444, 103, 445, 155);
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
		btnObterGraficoHoras.setBounds(615, 509, 274, 43);
		contentPane.add(btnObterGraficoHoras);

		JButton btnObterGraficoCustos = new JButton("Obter Gráficos dos Custos do Trabalho");
		btnObterGraficoCustos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GraficoBarra("Custos do Projeto", "Membro da equipe", "Euros", sHours, custoHora);
			}
		});
		btnObterGraficoCustos.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnObterGraficoCustos.setBounds(615, 563, 274, 43);
		contentPane.add(btnObterGraficoCustos);

		JLabel lblTabelaDeHoras = new JLabel("Tabela de horas previstas/usadas");
		lblTabelaDeHoras.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTabelaDeHoras.setBounds(412, 65, 324, 43);
		contentPane.add(lblTabelaDeHoras);

		JLabel lblTabelaDeCustos = new JLabel("Tabela de custos");
		lblTabelaDeCustos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTabelaDeCustos.setBounds(412, 270, 305, 43);
		contentPane.add(lblTabelaDeCustos);

		JTextArea textDate = new JTextArea(getDate());
		textDate.setBounds(20, 338, 211, 49);
		contentPane.add(textDate);

		JLabel lblProjectName = new JLabel(getNameofProject());
		lblProjectName.setHorizontalAlignment(SwingConstants.CENTER);
		lblProjectName.setFont(new Font("Felix Titling", Font.BOLD, 25));
		lblProjectName.setBounds(221, 11, 575, 53);
		contentPane.add(lblProjectName);
		
		JLabel lblREADME = new JLabel("Conteúdo do ficheiro README:");
		lblREADME.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblREADME.setBounds(10, 577, 300, 30);
		contentPane.add(lblREADME);
		
		JScrollPane scrollPaneREADME = new JScrollPane();
		scrollPaneREADME.setViewportBorder(null);
		scrollPaneREADME.setBounds(20, 607, 324, 179);
		contentPane.add(scrollPaneREADME);
		
		

		try {
			JTextArea jTextREADME = new JTextArea(getREADME());
			jTextREADME.setEditable(false);
			scrollPaneREADME.setViewportView(jTextREADME);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private String getNameofProject() {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		Organization organization = trelloApi.getBoardOrganization(boards.get(0).getId());
		String name = organization.getDisplayName();
		/*
		 * List<Card> cards = trelloApi.getBoardCards(boards.get(0).getId()); Card
		 * projectcard = trelloApi.getBoardCard(boards.get(0).getId(),
		 * cards.get(0).getId());
		 * 
		 * String name = projectcard.getName();
		 */
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
		String membersList = "";
		for (Member m : members) {
			membersList = membersList + m.getFullName() + "\n";
		}
		return membersList;
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
		String sprintsduration = "";
		for (Card c : listCards) {
			sprintsduration += c.getName() + ": " + c.getDue() + "\n";
		}
		return sprintsduration;
	}

	/**
	 * Este método dá return de uma String o Product Backlog
	 * 
	 * @return String
	 */
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

								if (dataText[1].contains("@")) {
									String[] user = dataText[1].split("@");
									String[] doubles = dataText[2].split("/");
									int control = 0;
									while (control < sHours.get(control1).getMemberHoursInformationList().size()) {
										if (sHours.get(control1).getMemberHoursInformationList().get(control).getUser()
												.equals(user[1])) {
											break;
										}
										control++;
									}
									if (sHours.get(control1).getMemberHoursInformationList().size() == control) {
										sHours.get(control1).getMemberHoursInformationList()
												.add(new MemberHoursInformation(user[1]));
									}
									sHours.get(control1).getMemberHoursInformationList().get(control)
											.addTime(Double.parseDouble(doubles[0]), Double.parseDouble(doubles[1]));
								} else {
									String user = trelloApi.getActionMemberCreator(a.getId()).getUsername();
									String[] doubles = dataText[1].split("/");

									int control = 0;
									while (control < sHours.get(control1).getMemberHoursInformationList().size()) {
										if (sHours.get(control1).getMemberHoursInformationList().get(control).getUser()
												.equals(user)) {
											break;
										}
										control++;
									}
									if (sHours.get(control1).getMemberHoursInformationList().size() == control) {
										sHours.get(control1).getMemberHoursInformationList()
												.add(new MemberHoursInformation(user));
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
	 * Este método dá return do conteúdo no ficheiro README.md
	 * presente na branch 'main' do repositório GitHub
	 * 
	 * @return String
	 */
	
	private String getREADME() throws IOException, URISyntaxException {
		
		String readMEContent ="";
		
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
		for (@SuppressWarnings("rawtypes") Map fileMetaData : response) {

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
				readMEContent += fileContent;
			}

		}
		return readMEContent;
	}
}