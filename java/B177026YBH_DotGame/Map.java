package dot_game;
import dot_game.*;
import java.util.Random;

//this position doc for map and receive pos for main 
//and hit true false ... count..?
class Map {
	final static int MAP_SIZE = 7;//const map size
	Dot[] _dot = new Dot[9];
	private	int[][][] map;
	int total_input_cnt;
	int total_hit_cnt;
	Random random = new Random();
	//int m_xyzPos;
	
	
	//init
	public void init() {
		for(int u=0;u<9;u++) {
			_dot[u] = new Dot();
			_dot[u].init();
			
			if(u/3 == 0)//set dot name 3unit 
				_dot[u].set_name("google.com");
			else if(u/3 == 1)
				_dot[u].set_name("naver.com");
			else if(u/3 == 2)
				_dot[u].set_name("honkig.com");
		}
		
		map = new int[MAP_SIZE][MAP_SIZE][MAP_SIZE];
		for(int i=0;i<MAP_SIZE;i++) {
			for(int j=0;j<MAP_SIZE;j++) {
				for(int k=0;k<MAP_SIZE;k++) {
					map[i][j][k] = 0;// defalut 0,pisition xyz,  hit -1
				}
			}
		}
		
		total_input_cnt = 0;
		total_hit_cnt = 0;
	}
	
	
	///pos,hit
	public	void position_dot() {
		int head = 0;
		int set_cnt = 0;
		
		int x = 0;
		int y = 0;
		int z = 0;
		
		int pos = 0;
			
		int px = 0;
		int py = 0;
		int pz = 0;
		
		while(set_cnt < 9) {//non Overlap & random position
			x = random.nextInt(3) + 2;//2~5 pos;
			y = random.nextInt(3) + 2;
			z = random.nextInt(3) + 2;
			
			while(true){
				px = random.nextInt(3) -1;//-1~1
				py = random.nextInt(3) -1;
				pz = random.nextInt(3) -1;
				
				if(px != 0 && py != 0 && pz != 0){//non Overlap
					break;
				}
					
			}
			//position
			for(int j = 0; j < 9; j++) {//head node non overlap discrimination
				if(_dot[j].get_pos_num() == 100*x + 10*y +z) {//overlap
					break;
				}
				
				if(j==8 && _dot[j].get_pos_num() != (100*x + 10*y +z)) {//non overlap
					for(int k = 0; k < 9; k++) {//middle node non overlap discrimination
						if(_dot[k].get_pos_num() == 100*(x+px) + 10*(y+py) + (z+pz)) {//overlap
							break;
						}
						
						if(k==8 && _dot[k].get_pos_num() != 100*(x+px) + 10*(y+py) + (z+pz)) {//non overlap
							for(int l = 0; l < 9; l++) {//tail node non overlap discrimination
								if(_dot[l].get_pos_num() == 100*(x+2*px) + 10*(y+2*py) + (z+2*pz)) {//overlap
									break;
								}
								
								if(l==8 && _dot[l].get_pos_num() != 100*(x+2*px) + 10*(y+2*py) + (z+2*pz)) {//non overlap
									map[x][y][z] = 100*x + 10*y +z;//set infomaion
									_dot[head].set_pos_num(100*x + 10*y +z);
									map[x+px][y+py][z+pz] = 100*(x+px) + 10*(y+py) + (z+pz);//set infomaion
									_dot[head+1].set_pos_num(100*(x+px) + 10*(y+py) + (z+pz));
									map[x+2*px][y+2*py][z+2*pz] = 100*(x+2*px) + 10*(y+2*py) + (z+2*pz);//set infomaion
									_dot[head+2].set_pos_num(100*(x+2*px) + 10*(y+2*py) + (z+2*pz));
									set_cnt+=3;
									head+=3;//3unit 
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println("A total of three dot was placed in a 7x7x7 3 -dimensional space , as follows");//test
	
		for(int i = 0; i<9;i+=3) {
			System.out.println(_dot[i].get_name() +"'s pos : "+ _dot[i].get_pos_num() +
				", "+ _dot[i+1].get_pos_num() +", "+ _dot[i+2].get_pos_num());
		}
	}
	
	
	public	void discrimination(int _xyzPos) {
		total_input_cnt++;
		int x = _xyzPos / 100;
		int y = (_xyzPos % 100) / 10;
		int z = _xyzPos % 10;
		
		if(map[x][y][z] == -1) {
			System.out.println("Alreay hit");
		}
		else if(map[x][y][z] != _xyzPos) {
			System.out.println("Digress");
		}
		else if(map[x][y][z] == _xyzPos) {
			System.out.println("Hit");
			map[x][y][z] = -1;//hit pos -> -1
			
			for(int i = 0; i<9;i++) {
				if(_dot[i].get_pos_num() == _xyzPos) {
					_dot[i].set_hit_cnt();//hit cnt++
					total_hit_cnt++;
					
					if(i % 3 == 0) {//3unit discrimination
						if(_dot[i].get_hit_cnt() + _dot[i+1].get_hit_cnt()
							 +_dot[i+2].get_hit_cnt() == 3) {
								 System.out.println("you sunk " + _dot[i].get_name());
							 }
					}
					else if(i % 3 == 1) {
						if(_dot[i-1].get_hit_cnt() + _dot[i].get_hit_cnt()
							 +_dot[i+1].get_hit_cnt() == 3) {
								 System.out.println("you sunk " + _dot[i].get_name());
							 }
					}
					
					else if(i % 3 == 2) {
						if(_dot[i-2].get_hit_cnt() + _dot[i-1].get_hit_cnt()
							 +_dot[i].get_hit_cnt() == 3) {
								 System.out.println("you sunk " + _dot[i].get_name());
							 }
					}
				}
			}
		}
		
		System.out.println("hit: " + total_hit_cnt + "guess: " + total_input_cnt);
		if(total_hit_cnt == 9) {
				System.out.println("Align all dot com . Exit the program");
				System.exit(0);
		}
	}
	
	
	///get,set
	public void set_map(int x,int y, int z, int _pos) {
		map[x][y][z] = _pos;
	}
	
	public int get_map(int x,int y, int z) {
		return map[x][y][z];
	}
}