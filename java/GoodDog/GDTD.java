import java.util.*;

class GoodDog{
	private int size;
	
	public int getSize(){
		return size;
	}
	
	public void setSize(int s){
		size = s;
	}
	
	void bark(){
		if(size > 60){
			System.out.println("wool wool!");
		}
		
		else if(size > 20){
			System.out.println("mung mung!");
		}
		
		else{
			System.out.println("wik wik!");
		}
	}
}
	
class GDTD{
	public static void main(String[] args){
		
		int[] _size = new int[3];
		GoodDog[] gd = new GoodDog[3];
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("input size");
		
		for(int i=0;i<3;i++){
			gd[i] = new GoodDog();
			_size[i] = sc.nextInt();
			gd[i].setSize(_size[i]);
		}
		
		for(int j=0;j<3;j++){
			System.out.println(gd[j].getSize());
			gd[j].bark();
		}
	}
}

	
	