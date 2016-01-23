import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class userRegPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JButton registerButton, goBackButton, headerLabel;
	private JLabel unameTextLabel, passTextLabel, confirmPassTextLabel;
	private JTextField unameTextFiled;
	private JPasswordField passField, confirmPassField;

	public userRegPanel(final DBConnections dbc, final homePage hp) {
		this.setLayout(null);
		
		this.headerLabel = new JButton("This is the Header Label of User Registration");
		this.unameTextFiled = new JTextField();
		this.unameTextFiled.requestFocus();
		this.unameTextLabel = new JLabel("Username :");
		this.passField = new JPasswordField();
		this.passTextLabel = new JLabel("Password :");
		this.confirmPassField = new JPasswordField();
		this.confirmPassTextLabel = new JLabel("Confirm Password :");
		this.registerButton = new JButton("Register");
		this.goBackButton = new JButton("Go Back");
		
		this.registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!unameTextFiled.getText().equals("") && unameTextFiled.getText().length() > 6){
					String pass = getPassword(passField.getPassword()); 
					if (pass.equals(getPassword(confirmPassField.getPassword())) && pass.length() > 6){
						if (dbc.registerUser(unameTextFiled.getText(), getPassword(passField.getPassword()))){
							JOptionPane.showMessageDialog(null, "You have been registered Successfully.\nPlease Login to Continue.");
							setVisible(false);
							hp.screen.setVisible(true);
						}
						else{
							JOptionPane.showMessageDialog(null, "Username already exists.\nPlease do Register again with different one.");
							unameTextFiled.setText("");
							passField.setText("");
							confirmPassField.setText("");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "Password and Confirm Password are not same.\n or Password length should be minimum 7 chars long.");
						passField.setText("");
						confirmPassField.setText("");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please Enter any valid username which is minimum 7 chars Long.");
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
		this.unameTextLabel.setBounds(180, 160, 200, 30);
		this.unameTextFiled.setBounds(380, 160, 200, 30);
		this.passTextLabel.setBounds(180, 210, 200, 30);
		this.passField.setBounds(380, 210, 200, 30);
		this.confirmPassTextLabel.setBounds(180, 260, 200, 30);
		this.confirmPassField.setBounds(380, 260, 200, 30);
		this.registerButton.setBounds(200, 330, 150, 30);
		this.goBackButton.setBounds(400, 330, 150, 30);
		
		this.add(headerLabel);
		this.add(confirmPassTextLabel);
		this.add(passField);
		this.add(goBackButton);
		this.add(passTextLabel);
		this.add(registerButton);
		this.add(unameTextFiled);
		this.add(unameTextLabel);
		this.add(confirmPassField);
		
		hp.add(this);
	}
	
	public String getPassword(char[] password){
        String str = "";
        for (int i = 0; i < password.length; i++){
            str += password[i];
        }
        return str;
    }
}
