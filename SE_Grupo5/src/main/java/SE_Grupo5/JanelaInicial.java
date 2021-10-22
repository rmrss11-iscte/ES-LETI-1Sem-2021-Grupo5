package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;

import com.julienvey.trello.Trello;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaInicial {

	private JFrame frame;
	private JTextField rep;
	private JTextField trelloKey;
	private JButton nextButton;
	private JTextField trelloToken;
	private JTextField trelloUser;

	/**
	 * Create the application.
	 */
	public JanelaInicial() {
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
		
		JLabel lblNewLabel = new JLabel("GitHub Repository");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(10, 11, 300, 37);
		frame.getContentPane().add(lblNewLabel);
		
		rep = new JTextField();
		rep.setFont(new Font("Tahoma", Font.ITALIC, 13));
		rep.setText("Escreva aqui...");
		rep.setBounds(20, 59, 404, 29);
		frame.getContentPane().add(rep);
		rep.setColumns(10);
		
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
				//frame.dispose();
				frame.setVisible(false);
				final Trello trelloApi = Conexao.conexaoTrello(getTrelloKey(), getTrelloToken());
				final String trelloUtilizador = getTrelloUser();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Informacao i = new Informacao(trelloApi, trelloUtilizador);
							i.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
	
	public String getRep() {
		return rep.getText();
	}
	
	public String getTrelloKey() {
		return trelloKey.getText();
	}
	public String getTrelloToken() {
		return trelloToken.getText();
	}
	public String getTrelloUser() {
		return trelloUser.getText();
	}
	public Window getFrame() {
		return frame;
	}
}
