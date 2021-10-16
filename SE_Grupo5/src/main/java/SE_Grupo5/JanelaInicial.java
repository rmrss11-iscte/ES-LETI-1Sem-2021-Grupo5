package SE_Grupo5;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaInicial {

	private JFrame frame;
	private JTextField rep;
	private JTextField trello;
	private JButton nextButton;

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
		frame.setBounds(100, 100, 450, 300);
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
		lblNewLabel_1.setBounds(10, 99, 300, 37);
		frame.getContentPane().add(lblNewLabel_1);
		
		trello = new JTextField();
		trello.setFont(new Font("Tahoma", Font.ITALIC, 13));
		trello.setText("Escreva aqui...");
		trello.setBounds(20, 147, 404, 29);
		frame.getContentPane().add(trello);
		trello.setColumns(10);
		
		nextButton = new JButton("Next");
		nextButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nextButton.setBounds(323, 221, 101, 29);
		frame.getContentPane().add(nextButton);
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				//frame.setVisible(false);
			}
		});
	}
	
	public String getRep() {
		return rep.getText();
	}
	
	public String getTrello() {
		return trello.getText();
	}
	public Window getFrame() {
		return frame;
	}
}
