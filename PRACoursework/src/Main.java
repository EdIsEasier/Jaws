import java.io.File;
import java.io.IOException;
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
		File usersFile = new File(System.getProperty("user.dir") + "\\Users");
		usersFile.mkdir();
		File defaultUser = new File(System.getProperty("user.dir") + "\\Users\\Default.txt");
		try {
			defaultUser.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Favourites favourites = new Favourites(jaws);
		SearchFrame search = new SearchFrame(favourites, jaws);
		favourites.setSearchFrame(search);
		favourites.addUserFavourites();
		User users = new User(favourites);
		MenuFrame menu = new MenuFrame(users, search, favourites);
	}
}
