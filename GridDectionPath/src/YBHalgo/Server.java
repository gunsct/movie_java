package YBHalgo;
import YBHalgo.*;

public class Server {
	public static void main(String args[]){
		boolean goNext = false;
		int goShellNum = 0;
		
		LogInOut lio = new LogInOut();
		
		if(lio.logOn("test2", 5678)){
			//로그인되면 맵 생성해줌
			Map map = new Map();
			map.setingMap();
			map.AStar();
			map.printPath();
			
			
			goNext = true;
			//받은 메시지가 goNext == true 
			if(goNext){//맵 이동하겠다고 하면 
				map.moveMap();//루트대로 좌표 이동
				
			}
			//도착후에 새로운 도착지 지정
			
			//map.setEndShell(goShellNum);//다음으로 이동할 셀 정함
			
			
		}
	}
}
