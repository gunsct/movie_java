package dot_game;

//dot's info ->pos, shape type, hit count.. 
class Dot {
	private int pos_num;
	private int hit_cnt;
	private String name;
	
	//init
	public void init() {
		pos_num = 0;
		hit_cnt = 0;
		name = null;
	}
	
	
	//set, get
	public void set_pos_num(int _pos) {
		pos_num = _pos;
	}
	
	public int get_pos_num() {
		return pos_num;
	}
	
	public void set_hit_cnt() {
		hit_cnt++;
	}
	
	public int get_hit_cnt() {
		return hit_cnt;
	}
	
	public void set_name(String _name) {
		name = _name;
	}
	
	public String get_name() {
		return name;
	}
}