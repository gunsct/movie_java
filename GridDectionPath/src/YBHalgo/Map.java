package YBHalgo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Map{
	int map_size;
	Shell map[][];//셀배열로 맵을 만듬
	int px,py;//나중에 클라이언트 시작점 알려주기위함
	
	int max_block;
	HashSet<Integer> block = new HashSet<Integer>();
	Iterator b_iterator = block.iterator(); 
	ArrayList<Shell> nonblock = new ArrayList<Shell>();
	
	int bcnt = 0;
	
	
	Map(){
		map_size = (int)(Math.random() * 99 + 1);//1~100
		max_block = (int)Math.pow(map_size, 2.0) / 10;
		
		System.out.println("mapsize" + map_size);
		System.out.println("max_block" + max_block);
		
		px = py = 0;
	}
	
	void setingBlock(){//헤시맵으로 중복되지않게 랜덤으로 블록을 선택함
		//Math.pow(map_size, 2.0) / 10
		
		while(bcnt != max_block){//최소 0 최대 1000
			block.add((int)(Math.random() * max_block + 1));
			bcnt = block.size();
		}

		System.out.println("bcnt" + bcnt);
	}
	
	void setingPlayer(){
		//랜덤으로 플레이어 위치 정하기
				int pnum = (int)(Math.random() *  (map_size * map_size - max_block) + 1);//전체 - 벽 = 벽아닌거
				System.out.println("pnum" + pnum);
				px = nonblock.get(pnum).xpos;
				py = nonblock.get(pnum).ypos;
				System.out.println(px + " " + py);
	}
	
	void setingMap(){//맵 설정부분
		int shell_num = 0;
		map = new Shell[map_size][map_size];//우선 맵을 만들어주고
		
		setingBlock();//벽설정
		
		//셀 정보 넣어줌
		for(int x = 0; x < map_size; x++){//각 셀에 자기 번호, 좌표 , 벽여부 넣어줌
			for(int y = 0; y< map_size; y++){
				map[x][y] = new Shell();
				map[x][y].num = shell_num;
				
				while (b_iterator.hasNext()){//벽번호랑 같으면 벽으로 설정해줌
					System.out.println((int)b_iterator.next());
					if(shell_num == (int)b_iterator.next()){
						map[x][y].block = true;
					}
			    }
				
				map[x][y].xpos = x;
				map[x][y].ypos = y;
				
				if(map[x][y].block == false){//블럭이 아니면 넌블럭 헤시셋에 넣어줌 이건 넌블럭인것중 랜덤으로 플레이어 위치 뽑기 위함
					nonblock.add(map[x][y]);
				}
				shell_num++;
			}
		}
		
		setingPlayer();//플레이어 위치 정함
	}
}