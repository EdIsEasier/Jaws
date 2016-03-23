package Jaws.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private SearchFrame search;
	private Favourites favs;
	
	public LoginCreateFrame(SearchFrame search, Favourites favs){
		super("Login or Create User");
		setLayout(new BorderLayout());
		createWidgets();
		this.search = search;
		this.favs = favs;
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
					File file = new File(userProfile);
					search.addUsers(user);
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
					search.failLogin(userProfile.getText());
				}
				else{
					JOptionPane warning = new JOptionPane();
					warning.showMessageDialog(null, "Must Enter a Username", "Warning", warning.INFORMATION_MESSAGE);
				}
			}
			
		});
	}

}
