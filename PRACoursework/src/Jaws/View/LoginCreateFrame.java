package Jaws.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Jaws.Model.User;

/**
 * frame used to log in and create users, extends JFrame
 * 
 * @author Benjamin
 * @author Edvinas
 * @author Tomas
 * @author Hannah
 */
public class LoginCreateFrame extends JFrame{
	private JTextField userProfile;		//where the user enters a username
	private JButton addUser;		//button to create user
	private JButton login;			//button to log in
	private User user;			//the user class
	private String path;		//the path to the user folder
	
	/**
	 * constructor to se the initial layout of the frame and initialise the fields used
	 * 
	 * @param user all of the users
	 */
	public LoginCreateFrame(User user){
		super("Login or Create User");
		setLayout(new BorderLayout());
		createWidgets();
		this.user = user;		//hands in the user class we are using
		path = System.getProperty("user.dir") + "\\Users\\";	//sets the path to the users folder
		pack();
	}
	
	/**
	 * creates all of the components of the frame and adds the Action Listeners to the correct components
	 */
	public void createWidgets(){
		addUser = new JButton("Create User");		//button to create user
		login = new JButton("Login");		//button to login
		userProfile = new JTextField();		//username of the user
		JPanel jpBot = new JPanel(new FlowLayout());
		add(userProfile, BorderLayout.CENTER);
		jpBot.add(login);
		jpBot.add(addUser);
		add(jpBot, BorderLayout.SOUTH);
		
		addUser.addActionListener(new ActionListener(){		//action listener for the button to create users

			public void actionPerformed(ActionEvent e) {
				if(!userProfile.getText().equals("")){		//if the user has typed in a user name
					File file = new File(path + userProfile.getText() + ".txt");		//create a new text file with the username as the name
					if(user.addUsers(file)){	//if the new user was created
						closeFrame();		//close the frame
					}
					userProfile.setText("");	//set the text back to ""
				}
				else{
					JOptionPane warning = new JOptionPane();	//else display a warning message that they have to enter a username
					warning.showMessageDialog(null, "Must Enter a Username", "Warning", warning.INFORMATION_MESSAGE);
				}
				
			}
			
		});
		
		login.addActionListener(new ActionListener(){		//action listener for the login button

			public void actionPerformed(ActionEvent e) {
				if(!userProfile.getText().equals("")){		//if the user has entered a username
					if(!user.failLogin(userProfile.getText())){		//if the user has not failes the login (has logged in)
						closeFrame();		//close the frame
					}
					userProfile.setText("");		//set the text back to ""
				}
				else{
					JOptionPane warning = new JOptionPane();		//else display a warnign that they have to enter a username
					warning.showMessageDialog(null, "Must Enter a Username", "Warning", warning.INFORMATION_MESSAGE);
				}
			}
			
		});
		
	}
	
	/**
	 * closes the frame and disposes of everything on the frame
	 */
	private void closeFrame(){
		setVisible(false);
		dispose();
	}

}
