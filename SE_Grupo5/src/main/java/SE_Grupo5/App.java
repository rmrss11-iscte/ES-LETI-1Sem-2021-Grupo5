package SE_Grupo5;

import java.awt.EventQueue;

import com.julienvey.trello.Trello;

public class App 
{
	private static Trello trelloApi;
	
    public static void main( String[] args )
    {
    	EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				JanelaInicial j = new JanelaInicial();
    				j.getFrame().setVisible(true);
    				trelloApi = Conexao.conexaoTrello(j.getTrelloKey(), j.getTrelloToken());
    				 
    				EventQueue.invokeLater(new Runnable() {
    					public void run() {
    						try {
    							Informacao i = new Informacao(trelloApi);
    							i.setVisible(true);
    						} catch (Exception e) {
    							e.printStackTrace();
    						}
    					}
    				});
    				
    				
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	});	
    	
    	
    }

   
}
