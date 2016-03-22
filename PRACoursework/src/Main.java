import Jaws.View.Favourites;
import Jaws.View.MenuFrame;
import Jaws.View.SearchFrame;

public class Main
{
	public static void main(String[] args)
	{
		Favourites favourites = new Favourites();
		SearchFrame search = new SearchFrame(favourites);
		MenuFrame menu = new MenuFrame(search, favourites);
	}
}
