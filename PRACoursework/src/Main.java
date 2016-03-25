import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import Jaws.Model.User;
import Jaws.View.Favourites;
import Jaws.View.MenuFrame;
import Jaws.View.SearchFrame;
import api.jaws.Jaws;

public class Main
{
	public static void main(String[] args)
	{
		Jaws jaws = new Jaws("jphHPbni3MIBmMKu", "jbB8OPuNG5Sxw11c");
		createFiles();
		Favourites favourites = new Favourites(jaws);
		SearchFrame search = new SearchFrame(favourites, jaws);
		favourites.setSearchFrame(search);
		favourites.addUserFavourites();
		User users = new User(favourites);
		MenuFrame menu = new MenuFrame(users, search, favourites);
	}

	/**
	 * Create the default user and shark of the day files
	 */
	public static void createFiles(){
		// Create a Users directory to store all the favourites sharks
		File usersFile = new File(System.getProperty("user.dir") + "\\Users");
		usersFile.mkdir();
		// Create a default user that's used if you haven't logged in or if you log out
		File defaultUser = new File(System.getProperty("user.dir") + "\\Users\\Default.txt");
		try {
			defaultUser.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create a text file to store the shark of the day
		File sharkOfDay = new File(System.getProperty("user.dir") + "\\SharkDay.txt");
		try {
			sharkOfDay.createNewFile();
			BufferedReader reader = new BufferedReader(new FileReader(sharkOfDay));
			if(reader.readLine() == null){
				PrintWriter writer = new PrintWriter(new FileWriter(sharkOfDay, false));
				writer.append("22/03/2016");
				writer.close();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
