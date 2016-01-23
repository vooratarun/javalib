import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class loginPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	JPanel mainPanel;
	private JButton headerLabel, loginButton, goBackButton;
	private JLabel uNameLabel, passLabel;
	private JTextField uName;
	private JPasswordField password;
	private userFunctions uFuns;
	private adminFunctions aFuns;
	
	public loginPanel(String str, final DBConnections dbc, final homePage hp){
		this.setLayout(new BorderLayout());
		
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(null);
		this.headerLabel = new JButton("This is the Header Label of " + str);
		this.uNameLabel = new JLabel("User Name :  ");
		this.uName = new JTextField();
		this.passLabel = new JLabel("Password :  ");
		this.password = new JPasswordField();
		this.loginButton = new JButton("Login");
		this.goBackButton = new JButton("Go Back");
		
		this.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int loginType = dbc.checkLogin(uName.getText(), getPassword(password.getPassword()));
				if (loginType == 1){
					mainPanel.setVisible(false);
					uFuns = new userFunctions(dbc, hp, uName.getText());
					uFuns.setBounds(0, 0, 750, 450);
					add(uFuns);
				}
				else if (loginType == 2){
					mainPanel.setVisible(false);
					aFuns = new adminFunctions(dbc, hp);
					aFuns.setBounds(0, 0, 750, 450);
					add(aFuns);
				}
				else{
					JOptionPane.showMessageDialog(null, "Invalid Username or Password... Please Signin with Correct Credentials...");
				}
			}
		});
		
		this.goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				hp.screen.setVisible(true);
			}
		});
		
		this.headerLabel.setBounds(70, 30, 600, 70);
		this.uNameLabel.setBounds(200, 160, 150, 40);
		this.uName.setBounds(310, 170, 250, 30);
		this.passLabel.setBounds(200, 230, 150, 40);
		this.password.setBounds(310, 240, 250, 30);
		this.loginButton.setBounds(230, 315, 120, 30);
		this.goBackButton.setBounds(400, 315, 120, 30);
		
		this.mainPanel.add(this.headerLabel);
		this.mainPanel.add(this.uName);
		this.mainPanel.add(this.uNameLabel);
		this.mainPanel.add(this.passLabel);
		this.mainPanel.add(this.password);
		this.mainPanel.add(this.loginButton);
		this.mainPanel.add(this.goBackButton);
		
		this.add(this.mainPanel);
	}
	
	public String getPassword(char[] password){
        String str = "";
        for (int i = 0; i < password.length; i++){
            str += password[i];
        }
        return str;
    }
}
