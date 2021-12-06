package SE_Grupo5;

import java.awt.EventQueue;

public class App 
{
	
    public static void main( String[] args )
    {
    	EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				MenuInicial j = new MenuInicial();
    				j.getFrame().setVisible(true);
    				
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	});	
    	
    }
 
}
