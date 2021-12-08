package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;

import org.kohsuke.github.GitHub;

import com.julienvey.trello.Trello;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
/**
 * Esta classe serve para criar uma frame e pedir ao utilizador 
 * A sua key, Token e user no Trello, o Token no GitHub,
 * assim como o nome e dono do repositório GitHub  
 *
 */
public class MenuInicial {

	private JFrame frame;
	private JTextField textGitHubToken;
	private JTextField textRepositoryName;
	private JTextField textTrelloKey;
	private JTextField textTrelloToken;
	private JTextField textTrelloUser;
	private JButton nextButton;
	private JTextField textRepositoryOwner;

	/**
	 * Create the application.
	 */
	public MenuInicial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 527, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("GitHub");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(10, 11, 300, 37);
		frame.getContentPane().add(lblNewLabel);

		textGitHubToken = new JTextField();
		textGitHubToken.setFont(new Font("Tahoma", Font.ITALIC, 13));
		textGitHubToken.setText("GitHub Token");
		textGitHubToken.setBounds(20, 59, 404, 29);
		frame.getContentPane().add(textGitHubToken);
		textGitHubToken.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Trello");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_1.setBounds(10, 182, 300, 37);
		frame.getContentPane().add(lblNewLabel_1);

		textTrelloKey = new JTextField();
		textTrelloKey.setFont(new Font("Tahoma", Font.ITALIC, 13));
		textTrelloKey.setText("Trello Key");
		textTrelloKey.setBounds(20, 270, 404, 29);
		frame.getContentPane().add(textTrelloKey);
		textTrelloKey.setColumns(10);

		nextButton = new JButton("Next");
		nextButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nextButton.setBounds(400, 350, 101, 29);
		frame.getContentPane().add(nextButton);

		textTrelloToken = new JTextField();
		textTrelloToken.setText("Trello Token");
		textTrelloToken.setFont(new Font("Tahoma", Font.ITALIC, 13));
		textTrelloToken.setColumns(10);
		textTrelloToken.setBounds(20, 310, 404, 29);
		frame.getContentPane().add(textTrelloToken);

		textTrelloUser = new JTextField();
		textTrelloUser.setText("Trello Username");
		textTrelloUser.setFont(new Font("Tahoma", Font.ITALIC, 13));
		textTrelloUser.setColumns(10);
		textTrelloUser.setBounds(20, 230, 404, 29);
		frame.getContentPane().add(textTrelloUser);
		
		textRepositoryName = new JTextField();
		textRepositoryName.setText("Repository Name");
		textRepositoryName.setFont(new Font("Tahoma", Font.ITALIC, 13));
		textRepositoryName.setColumns(10);
		textRepositoryName.setBounds(20, 99, 404, 29);
		frame.getContentPane().add(textRepositoryName);
		
		textRepositoryOwner = new JTextField();
		textRepositoryOwner.setText("Repository Owner");
		textRepositoryOwner.setFont(new Font("Tahoma", Font.ITALIC, 13));
		textRepositoryOwner.setColumns(10);
		textRepositoryOwner.setBounds(20, 139, 404, 29);
		frame.getContentPane().add(textRepositoryOwner);
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					frame.setVisible(false);
				
					final GitHub gitHubApi = Conexao.conexaoGitHub("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");
					final Trello trelloApi = Conexao.conexaoTrello("95535b17caae83c1c1435cbe99dbcf24",
							"195e391a7ce8c837658de6d6473ac882450d8115dbe75d641f8c1cf6a396fd97");
					final String trelloUtilizador = "andre_barroso88";
					final String repositoryName = "ES-LETI-1Sem-2021-Grupo5";
					final String repositoryOwner = "rmrss11-iscte";
					//final GitHub gitHubApi = Conexao.conexaoGitHub(getGitHubToken());
					//final Trello trelloApi = Conexao.conexaoTrello(getTrelloKey(), getTrelloToken());
					//final String trelloUtilizador = getTrelloUser();
					//final String repositoryName = getRepositoryName();
					//final String repositoryOwner = getRepositoryOwner();
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								Informacao i = new Informacao(trelloApi, trelloUtilizador, gitHubApi, repositoryOwner ,repositoryName );
								i.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	/**
	 * Este método dá return do Token no GitHub
	 * 
	 * @return String
	 */
	public String getGitHubToken() {
		return textGitHubToken.getText();
	}
	/**
	 * Este método dá return do nome repositório no GitHub
	 * 
	 * @return String
	 */
	public String getRepositoryName() {
		return textRepositoryName.getText();
	}
	/**
	 * Este método dá return do dono do repositório no GitHub
	 * 
	 * @return String
	 */
	public String getRepositoryOwner() {
		return textRepositoryOwner.getText();
	}

	/**
	 * Este método dá return da Key no Trello
	 * 
	 * @return String
	 */
	public String getTrelloKey() {
		return textTrelloKey.getText();
	}
	
	/**
	 * Este método dá return do Token no Trello
	 * 
	 * @return String
	 */
	public String getTrelloToken() {
		return textTrelloToken.getText();
	}
	
	/**
	 * Este método dá return do User no Trello
	 * 
	 * @return String
	 */
	public String getTrelloUser() {
		return textTrelloUser.getText();
	}
	
	/**
	 * Este método dá return desta frame
	 * 
	 * @return frame
	 */
	public Window getFrame() {
		return frame;
	}
}
