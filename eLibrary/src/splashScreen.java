import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class splashScreen extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JButton userRegButton, userLogButton, adminLogButton, displayLogo, headerLabel;
	private loginPanel lPanel;
	private userRegPanel rPanel;
	private homePage hp;
	
	public splashScreen(final homePage hp, final DBConnections dbc){
		
		this.setLayout(null);
		this.hp = hp;
		
		this.headerLabel = new JButton("This is the Header Label of HomePage");
		this.userRegButton = new JButton("User Registration");
		this.userLogButton = new JButton("User Login");
		this.userLogButton.requestFocus();
		this.adminLogButton = new JButton("Admin Login");		
		this.displayLogo = new JButton("This is the Logo Image Label");
		
		this.userRegButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
				rPanel = new userRegPanel(dbc, hp);
				hp.add(rPanel);
			}
		});
		
		this.userLogButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
				lPanel = new loginPanel("User", dbc, hp);
				hp.add(lPanel);
			}
		});
		
		this.adminLogButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
				lPanel = new loginPanel("Admin", dbc, hp);
				hp.add(lPanel);
			}
		});

		this.headerLabel.setBounds(70, 30, 600, 70);
		this.userRegButton.setBounds(120, 160, 180, 50);
		this.userLogButton.setBounds(120, 230, 180, 50);
		this.adminLogButton.setBounds(120, 300, 180, 50);
		this.displayLogo.setBounds(390, 160, 200, 200);

		this.add(this.headerLabel);
    	this.add(this.userLogButton);
		this.add(this.userRegButton);
		this.add(this.adminLogButton);
		this.add(this.displayLogo);
	}
	
	public void removeComponents(){
		hp.remove(hp.screen.lPanel);
	}
}
