import java.awt.BorderLayout;

import javax.swing.JApplet;

public class homePage extends JApplet{

	private static final long serialVersionUID = 1L;
	
	public splashScreen screen;
	
	public void init(){
		
		this.setSize(750, 450);
		this.setLayout(new BorderLayout());
		
		screen = new splashScreen(this, new DBConnections());
		
		this.getContentPane().add(screen);
	}
	
	public void showSplashScreen(){
		this.screen.setVisible(true);
		this.screen.removeComponents();
	}
}
