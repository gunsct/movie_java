package YBHalgo;

public class Shell{
	int num;//����ȣ
	boolean block;//���̳ľƴϳ�
	int xpos, ypos;//��ǥ
	
	 int heuristicCost = 0; //Heuristic cost@@
     int finalCost = 0; //G+H@@
     Shell parent; //@@
	
	Shell(){
		num = 0;
		block = false;
		xpos = ypos = 0;
	}
}
