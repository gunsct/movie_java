package YBHalgo;

public class Shell{
	int num;//셀번호
	boolean block;//벽이냐아니냐
	int front_shell, next_shell;//이전, 다음 셀
	int xpos, ypos;//좌표
	double distance_to_end;//목적지와의 거리
	
	
	Shell(){
		num = 0;
		block = false;
		front_shell = next_shell = 0;
		xpos = ypos = 0;
		distance_to_end = 0;
	}
	
	void distance(int ex, int ey){//클라에서 목적지 받으면 목적지 좌표 넣어주면 목적지까지 거리나옴
		distance_to_end = Math.sqrt( Math.pow( (double)(xpos-ex), 2.0 ) + Math.pow( (double)(ypos-ey), 2.0 ) );
	}
}
