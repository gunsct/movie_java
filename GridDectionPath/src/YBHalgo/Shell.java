package YBHalgo;

public class Shell{
	int num;//셀번호
	boolean block;//벽이냐아니냐
	int xpos, ypos;//좌표
	
	 int heuristicCost = 0; //Heuristic cost@@
     int finalCost = 0; //G+H@@
     Shell parent; //@@
	
	Shell(){
		num = 0;
		block = false;
		xpos = ypos = 0;
	}
}
