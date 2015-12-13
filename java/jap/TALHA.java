class Triangle{
	double area;
	int length;
	int height;
	
	void setArea(){
		area = length * height / 2;
		}
}

class TALHA{
	public static void main(String[] args){
	
		Triangle[] ta = new Triangle[4];
		int x = 0;
		int y = x;
		
		while(x < 4){
			ta[x] = new Triangle();
			ta[x].height = (x + 1) * 2;
			ta[x].length = x + 4;
			ta[x].setArea();
			
			System.out.println("tri" + x + "area = " + ta[x].area);
			x += 1;
			}
		
		x = 27;
		Triangle t5 = ta[2];
		ta[2].area = 343;
		System.out.println("tri" + y + "area = " + t5.area);
	}
}
		