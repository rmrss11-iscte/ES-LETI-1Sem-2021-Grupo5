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
 * A sua key, Token e user no Trello e o seu Token no GitHub 
 *
 */
public class MenuInicial {

	private JFrame frame;
	private JTextField gitHubToken;
	private JTextField trelloKey;
	private JButton nextButton;
	private JTextField trelloToken;
	private JTextField trelloUser;

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
		frame.setBounds(100, 100, 527, 403);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("GitHub");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(10, 11, 300, 37);
		frame.getContentPane().add(lblNewLabel);

		gitHubToken = new JTextField();
		gitHubToken.setFont(new Font("Tahoma", Font.ITALIC, 13));
		gitHubToken.setText("GitHub Token");
		gitHubToken.setBounds(20, 59, 404, 29);
		frame.getContentPane().add(gitHubToken);
		gitHubToken.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Trello");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_1.setBounds(10, 136, 300, 37);
		frame.getContentPane().add(lblNewLabel_1);

		trelloKey = new JTextField();
		trelloKey.setFont(new Font("Tahoma", Font.ITALIC, 13));
		trelloKey.setText("Trello Key");
		trelloKey.setBounds(20, 224, 404, 29);
		frame.getContentPane().add(trelloKey);
		trelloKey.setColumns(10);

		nextButton = new JButton("Next");
		nextButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nextButton.setBounds(400, 324, 101, 29);
		frame.getContentPane().add(nextButton);

		trelloToken = new JTextField();
		trelloToken.setText("Trello Token");
		trelloToken.setFont(new Font("Tahoma", Font.ITALIC, 13));
		trelloToken.setColumns(10);
		trelloToken.setBounds(20, 264, 404, 29);
		frame.getContentPane().add(trelloToken);

		trelloUser = new JTextField();
		trelloUser.setText("Trello Username");
		trelloUser.setFont(new Font("Tahoma", Font.ITALIC, 13));
		trelloUser.setColumns(10);
		trelloUser.setBounds(20, 184, 404, 29);
		frame.getContentPane().add(trelloUser);
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					frame.setVisible(false);
					final GitHub gitHubApi = Conexao.conexaoGitHub(getGitHubToken());
					final Trello trelloApi = Conexao.conexaoTrello("95535b17caae83c1c1435cbe99dbcf24",
							"195e391a7ce8c837658de6d6473ac882450d8115dbe75d641f8c1cf6a396fd97");
					final String trelloUtilizador = "andre_barroso88";
					//final Trello trelloApi = Conexao.conexaoTrello(getTrelloKey(), getTrelloToken());
					//final String trelloUtilizador = getTrelloUser();
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								Informacao i = new Informacao(trelloApi, trelloUtilizador, gitHubApi);
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
		return gitHubToken.getText();
	}

	/**
	 * Este método dá return da Key no Trello
	 * 
	 * @return String
	 */
	public String getTrelloKey() {
		return trelloKey.getText();
	}
	
	/**
	 * Este método dá return do Token no Trello
	 * 
	 * @return String
	 */
	public String getTrelloToken() {
		return trelloToken.getText();
	}
	
	/**
	 * Este método dá return do User no Trello
	 * 
	 * @return String
	 */
	public String getTrelloUser() {
		return trelloUser.getText();
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
