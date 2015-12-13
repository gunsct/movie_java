package dot_game;
import dot_game.*;
import java.util.Scanner;
//import java.lang;

//this main input pos and send for map class
class DotGame {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		String name;
		String xyzPos;
		String quit = "quit";
		System.out.println("set your name");
		name = scan.nextLine();
		
		Map _map = new Map();
		_map.init();//init map class
		_map.position_dot();//seting dot for map
		
		
		
		while(true) {
			//input pos & send
			System.out.println("If you want to exit , enter quit");
			System.out.print(name +" input pos :");
			
			xyzPos = scan.nextLine();
			
			if(quit.equals(xyzPos))
				 System.exit(0);
				 
			_map.discrimination(Integer.parseInt(xyzPos));//discrimination
		}
	}
}