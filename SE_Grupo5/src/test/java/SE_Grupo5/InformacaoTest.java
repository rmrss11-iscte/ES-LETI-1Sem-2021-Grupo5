package SE_Grupo5;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kohsuke.github.GitHub;

import com.julienvey.trello.Trello;

public class InformacaoTest {

	final static Trello trelloApi = Conexao.conexaoTrello("9b48622d0806fbee2ade9d0863709628",
			"49ed4b088bf928e3536a17ce839f775bdf700ffbdf7203af3943ba520a036170");
	final static String trelloUtilizador = "andre_barroso88";
	static GitHub gitHubApi;
	final static String repositoryName = "ES-LETI-1Sem-2021-Grupo5";
	final static String repositoryOwner = "rmrss11-iscte";
	static Informacao info;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gitHubApi = Conexao.conexaoGitHub("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");
		info = new Informacao(trelloApi, trelloUtilizador, gitHubApi, repositoryOwner, repositoryName);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInformacao() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNameofProject() throws IOException {
		String output = "Engenharia de Software";
		assertEquals(output, info.getNameofProject());
	}

	@Test
	public void testSetNovoCustoHora() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMembers() throws IOException {
		String output = "André Barroso\n" + "André Cristina Pereira\n" + "Luis Figueira\n" + "Ricardo Rodrigues\n";
		assertEquals(output, info.getMembers());
	}

	@Test
	public void testGetDate() throws IOException, ParseException {
		String date = "Wed Dec 08 12:00:00 GMT 2021";
		assertEquals(date, info.getDate().toString());
	}

	@Test
	public void testGetSprintsDuration() throws IOException {
		String output = "1 Sprint: Sun Oct 24 00:00:00 BST 2021\n" + "2 Sprint: Sun Nov 07 23:58:00 GMT 2021\n"
				+ "3 Sprint: Sun Nov 21 00:00:00 GMT 2021\n" + "4 Sprint: Wed Dec 08 00:00:00 GMT 2021\n";
		assertEquals(output, info.getSprintsDuration());
	}

	@Test
	public void testGetProductBacklog() throws IOException {
		String output = "Project Planning:\n" + "Sprint 1:\n" + "-Identificar elementos da equipa do projeto\n"
				+ "-Conectar API's\n" + "-Pedir identificação ao utilizador\n" + "-Escolher API's\n" + "Sprint 2:\n"
				+ "-Identificar Projeto\n" + "-Identificar data de início do projeto\n"
				+ "-Identificar número de horas de trabalho previstas e realizadas em cada Sprint\n" + "Sprint 3:\n"
				+ "-nº de atividades, horas e custo que deram origem a artefactos\n"
				+ "-Identificar itens do Product Backlog\n"
				+ "-nº de atividades, horas e custo que não deram origem a artefactos\n"
				+ "-Associar os textos das reuniões Sprint Planning, Review e Restrospective\n"
				+ "-Horas de trabalho realizadas no projeto com gráfico e tabela, discriminando cada membro\n"
				+ "-Conexão ao GitHub\n" + "-Construir gráfico e tabela com o custo dos recursos humanos\n"
				+ "-Identificar data de início e fim de cada sprint\n" + "Sprint 4:\n"
				+ "-Exportar para um ficheiro .csv a informação relativa a cada membro da equipa obtida previamente\n"
				+ "-Identificar data de início e conclusão dos testes realizados\n"
				+ "-Realização de testes unitários\n" + "-Fazer uma cobertura de testes, através do plugin EclEmma\n"
				+ "-Obter um relatório de code smells\n" + "-Escolher um plugin direcionado para code smells\n"
				+ "-Obter o conteúdo do ficheiro README presente no GitHub\n"
				+"-Obter as descrições e datas dos commits de cada membro da equipa, por branch e por ordem cronológica\n"
				+ "-Obter as tags presentes no branch master, bem como as respetivas datas em que foram criadas;\n";
		assertEquals(output, info.getProductBacklog());

	}

	@Test
	public void testGetProjectTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAttachList() throws IOException {
		String output = "https://trello.com/1/cards/61899b85c9604d1c03f8c234/attachments/61899b9d93401d46df61ede1/download/Planning_Sprint1.txt\r\n"
				+ "https://trello.com/1/cards/617723b6d2da3e632b447dba/attachments/61899ebbaf34776fa83d5bc2/download/Review_Sprint_1.txt\r\n"
				+ "https://trello.com/1/cards/61772d1953eb907d73de497a/attachments/61899f3c34f9db0b1590fef3/download/Retrospetiva_Sprint1.txt\r\n"
				+ "https://trello.com/1/cards/617731f754cab8478dc1c162/attachments/61773201e4b49b54376a4b8e/download/Planning_Sprint2.txt\r\n"
				+ "https://trello.com/1/cards/61899f809152c30fda3ea28b/attachments/6189a417cd02b1695aff4944/download/Review_Sprint2.txt\r\n"
				+ "https://trello.com/1/cards/6189a437fc4a5e0788fc082e/attachments/6189a6864a6dfb028a5b7bb1/download/Retrospetiva_Sprint2.txt\r\n"
				+ "https://trello.com/1/cards/6189acd9786baa1b03b48697/attachments/6189b5e7231e2c4256806277/download/Planning_Sprint3.txt\r\n"
				+ "https://trello.com/1/cards/61abc187ef4ef0474e78f548/attachments/61abc1932964ca22e01269d3/download/Review_Sprint3.txt\r\n"
				+ "https://trello.com/1/cards/61abc1ae652b8e4013b302d0/attachments/61abc1b900f5ad72274ff3ab/download/Retrospetiva_Sprint3.txt\r\n"
				+ "https://trello.com/1/cards/61abc1cde8d30f1eef6248eb/attachments/61abc1d782a0ec4723eabe53/download/Planning_Sprint4.txt\n";
		assertEquals(output, info.getAttachList());

	}

	@Test
	public void testGetActivitiesHoursCost() {
		String output = "->Neste projeto foram originados 15 artifactos,\n"
				+"gastando-se 37 horas, o que dá um custo total de: 740.0 euros\n"
				+"->O utilizador andre_barroso88 originou 6 artifactos no repositório \n"
				+"e gastou 17.879999999999995 horas  o que dá um custo total de: 357.5999999999999 euros\n"
				+"->O utilizador luisfigueira12 originou 5 artifactos no repositório \n"
				+"e gastou 5.989999999999998 horas  o que dá um custo total de: 119.79999999999997 euros\n"
				+"->O utilizador ricardorodrigues203 originou 4 artifactos no repositório \n"
				+"e gastou 12.180000000000001 horas  o que dá um custo total de: 243.60000000000002 euros\n"
				+"->O utilizador andrecristinapereira2 originou 1 artifactos no repositório \n"
				+"e gastou 3.0 horas  o que dá um custo total de: 60.0 euros\n";
		assertEquals(output, info.getActivitiesHoursCost());
	}

	@Test
	public void testNotgetActivitiesHoursCost() {
		String output = "->Neste projeto foram originadas 3 atividades que não deram origem a artifactos, \n"
				+ "gastando-se 5 horas, o que dá um custo total de: 100.0 euros\n"
				+ "->O utilizador ricardorodrigues203 originou 2 atividades que não deram origem a artifactos no repositório \n"
				+ "e gastou 0.45999999999999996 horas  o que dá um custo total de: 9.2 euros\n"
				+ "->O utilizador luisfigueira12 originou 2 atividades que não deram origem a artifactos no repositório \n"
				+ "e gastou 0.31 horas  o que dá um custo total de: 6.2 euros\n"
				+ "->O utilizador andrecristinapereira2 originou 3 atividades que não deram origem a artifactos no repositório \n"
				+ "e gastou 5.31 horas  o que dá um custo total de: 106.19999999999999 euros\n"
				+ "->O utilizador andre_barroso88 originou 2 atividades que não deram origem a artifactos no repositório \n"
				+ "e gastou 0.45999999999999996 horas  o que dá um custo total de: 9.2 euros\n";
		assertEquals(output, info.notgetActivitiesHoursCost());

	}

	@Test
	public void testGetTrelloApi() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTrelloUtilizador() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSprintHours() {
		fail("Not yet implemented");
	}

	@Test
	public void testCriarTabela() {
		fail("Not yet implemented");
	}

	@Test
	public void testCriarTabela1() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetREADME() throws IOException, URISyntaxException {
		String output = "# ES-LETI-1Sem-2021-Grupo5\r\n"
				+ "Este Projeto foi desenvolvido no âmbito da cadeira\r\n"
				+ "Engenharia de Software do Curso ETI no primeiro semestre do ano letivo 2021/22\r\n"
				+ "pelo grupo 5 composto por:\r\n"
				+ "André Barroso - 92552;\r\n"
				+ "André Pereira - 93252;\r\n"
				+ "Luís Figueira - 82146;\r\n"
				+ "Ricardo Rodrigues - 82993;\n";
		assertEquals(output,info.getREADME());
	}

	@Test
	public void testGetTagList() {
		String output = "ES1v1.0.0.1 - Mon Oct 11 23:49:53 BST 2021\r\n"
				+ "ES1v1.0 - Mon Oct 11 21:57:50 BST 2021\n";
		assertEquals(output, info.getTagList());
	}

}
