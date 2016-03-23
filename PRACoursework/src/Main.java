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
		Favourites favourites = new Favourites(jaws);
		SearchFrame search = new SearchFrame(favourites, jaws);
		User users = new User(favourites);
		MenuFrame menu = new MenuFrame(users, search, favourites);
	}
}
