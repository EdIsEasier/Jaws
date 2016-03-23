package Jaws.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Jaws.Model.User;

public class LoginCreateFrame extends JFrame{
	private JTextField userProfile;
	private JButton addUser;
	private JButton login;
	private User user;
	private Favourites favs;
	private String path;
	
	public LoginCreateFrame(User user, Favourites favs){
		super("Login or Create User");
		setLayout(new BorderLayout());
		createWidgets();
		this.user = user;
		this.favs = favs;
		path = System.getProperty("user.dir") + "\\Users\\";
		pack();
	}
	
	public void createWidgets(){
		addUser = new JButton("Create User");
		login = new JButton("Login");
		userProfile = new JTextField();
		JPanel jpBot = new JPanel(new FlowLayout());
		add(userProfile, BorderLayout.CENTER);
		jpBot.add(login);
		jpBot.add(addUser);
		add(jpBot, BorderLayout.SOUTH);
		
		addUser.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(!userProfile.getText().equals("")){
					File file = new File(path + userProfile.getText() + ".txt");
					user.addUsers(file);
				}
				else{
					JOptionPane warning = new JOptionPane();
					warning.showMessageDialog(null, "Must Enter a Username", "Warning", warning.INFORMATION_MESSAGE);
				}
				
			}
			
		});
		
		login.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(!userProfile.getText().equals("")){
					user.failLogin(userProfile.getText());
				}
				else{
					JOptionPane warning = new JOptionPane();
					warning.showMessageDialog(null, "Must Enter a Username", "Warning", warning.INFORMATION_MESSAGE);
				}
			}
			
		});
	}

}
