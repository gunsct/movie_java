package YBHalgo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;


public class Map{
	public static final int DIAGONAL_COST = 14;//@@
	public static final int V_H_COST = 10;//@@
	    
	int map_size;
	Shell map[][];//셀배열로 맵을 만듬
	PriorityQueue<Shell> open;//@@
    static boolean closed[][];//@@
    
	int px,py;//나중에 클라이언트 시작점 알려주기위함
	int ex,ey;
	
	int max_block;
	HashSet<Integer> block = new HashSet<Integer>();//블록 번호담기
	Iterator b_iterator = block.iterator(); 
	ArrayList<Shell> nonblock = new ArrayList<Shell>();//블럭 아닌것만 담기
	ArrayList<Shell> path = new ArrayList<Shell>();//경로만 담기
	
	int bcnt = 0;
	
	
	Map(){
		map_size = (int)(Math.random() * 99 + 1);//1~100
		max_block = (int)Math.pow(map_size, 2.0) / 10;
		
		System.out.println("맵크기" + map_size * map_size); 
		System.out.println("블록 수" + max_block);
		
		px = py = 0;
	}
	
	void setingBlock(){//헤시맵으로 중복되지않게 랜덤으로 블록 번호를 저장함
		while(bcnt != max_block){//최소 0 최대 1000
			block.add((int)(Math.random() * map_size*map_size + 1));
			bcnt = block.size();
		}
		System.out.println("블럭 수" + bcnt);
	}
	
	void setingPlayer(){//랜덤으로  플레이어 위치 정하기, nonblock은 mapseting에서 블럭이 아닌것들만 따로 저장됨 
				int pnum = (int)(Math.random() *  (map_size * map_size - max_block) + 1);//전체 - 벽  = 벽아닌곳 중 랜덤 1곳
				px = nonblock.get(pnum).xpos;
				py = nonblock.get(pnum).ypos;
				
				System.out.println("시작 번호:" + pnum + "위치 " + px + " " + py);
	}
	
	void setEndShell(int _snum){//목적지 고르기, 벽인지 아닌지는 다른곳에서 판별해서 알려줌
		ex = nonblock.get(_snum).xpos;
		ey = nonblock.get(_snum).ypos;
		System.out.println("목적지 " + ex + " " + ey);
    }
	
	
	
	void setingMap(){//맵 설정부분
		int shell_num = 0;
		map = new Shell[map_size][map_size];//우선 맵을 만들어주고
		
		//@@지나온곳인지 판별하기위함인거같음
        closed = new boolean[map_size][map_size];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
             Shell c1 = (Shell)o1;
             Shell c2 = (Shell)o2;

             return c1.finalCost<c2.finalCost?-1:
                     c1.finalCost>c2.finalCost?1:0;
         });//@
		
		setingBlock();//벽설정
		
		//셀 정보 넣어줌
		for(int x = 0; x < map_size; x++){//각 셀에 자기 번호, 좌표 , 벽여부 넣어줌
			for(int y = 0; y< map_size; y++){
				map[x][y] = new Shell();
				map[x][y].num = shell_num;
				map[x][y].heuristicCost = Math.abs(x-ex)+Math.abs(y-ey);//@@목적지까지 거리값
				
				b_iterator = block.iterator();  
				while (b_iterator.hasNext()){//벽번호랑 같으면 벽으로 설정해줌
					if(shell_num == (int)b_iterator.next()){//위에서 벽번호를 설정해준것을 이용해 벽으로 체크해줌
						map[x][y].block = true;
					}
			    }
				
				map[x][y].xpos = x;
				map[x][y].ypos = y;
				
				if(map[x][y].block == false){//블럭이 아니면 넌블럭 헤시셋에 넣어줌 이건 넌블럭인것중 랜덤으로 플레이어 위치 뽑기 위함
					nonblock.add(map[x][y]);
				}
				shell_num++;//번호 올려줌
			}
		}
		
		map[px][py].finalCost = 0;//@@다음 셀이랑 목적지까지 거리 비교하기 위함
		
		setingPlayer();//플레이어 위치 정함
		
		
	}
	
	void checkAndUpdateCost(Shell current, Shell t, int cost){//@@주변 셀과 거리값 체크하는부분
        if(t.block == true || closed[t.xpos][t.ypos])return;
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            
            if(!inOpen) open.add(t);
        }
    }
	
	void AStar(){//@@ http://www.codebytes.in/2015/02/a-shortest-path-finding-algorithm.html 참고했음 우연히 내가 짠 맵과 동일한 방식을 써서 고치는데 힘들이지않았음
	        //add the start location to open list.
			open.clear();//경로 누적때문에 해봄 변화없음
	        open.add(map[px][py]);
	        
	        Shell current;
	        
	        while(true){ 
	            current = open.poll();
	            if(current.block==true)break;
	            closed[current.xpos][current.ypos]=true; 
	
	            if(current.equals(map[ex][ey])){
	                return; 
	            } 
	
	            Shell t;  
	            if(current.xpos-1>=0){
	                t = map[current.xpos-1][current.ypos];
	                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
	                
	                //대각선이동
	               /* if(current.ypos-1>=0){                      
	                    t = map[current.xpos-1][current.ypos-1];
	                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
	                }
	
	                if(current.ypos+1<map[0].length){
	                    t = map[current.xpos-1][current.ypos+1];
	                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
	                }*/
	            } 
	
	            if(current.ypos-1>=0){
	                t = map[current.xpos][current.ypos-1];
	                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
	            }
	
	            if(current.ypos+1<map[0].length){
	                t = map[current.xpos][current.ypos+1];
	                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
	            }
	
	            if(current.xpos+1<map.length){
	                t = map[current.xpos+1][current.ypos];
	                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
	                
	                //대각선이동
	                /*if(current.ypos-1>=0){
	                    t = map[current.xpos+1][current.ypos-1];
	                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
	                }
	                
	                if(current.ypos+1<map[0].length){
	                   t = map[current.xpos+1][current.ypos+1];
	                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
	                }  */
	            }
	        } 
	    }
	
	
	
	String moveMap(String _go){//클라이언트의 응답에 의해 시작점이 바뀌게 함
		String msg = new String();
		
		for(int i = 0; i< path.size(); i++){
			if(_go.equals(Integer.toString(path.get(i).num))){
				px = path.get(i).xpos;
				py = path.get(i).ypos;
				msg += "OK/move shell num:" + path.get(i).num + " x:" + px + " y:" + py;
				return msg;//가능하면 보내주는거고
			}
		}
		
		msg = "MOVE_RESP/NO";//불가능하면 안돼
		return msg;
	}
	
	String getInfo(){//로그인 후 넘기는 패킷
		String strw ="LOGIN_RESP/OK";
		strw += "/크기정보/" + Integer.toString(map_size * map_size) + "/벽 번호/";
		
		b_iterator = block.iterator();  
		while (b_iterator.hasNext()){//벽번호랑 같으면 벽으로 설정해줌
			strw += "/" + Integer.toString((int)b_iterator.next());
	    }
		strw += "/사용자 위치/" + map[px][py].num + "-" + px + "," + py;
		
		return strw;
	}
	
	String printPath(String _snum){//경로를 찾고  보여준다
		String msg = new String();
		
		b_iterator = block.iterator();  
		while (b_iterator.hasNext()){//벽번호랑 같으면 벽으로 설정해줌
			if(_snum.equals(Integer.toString((int)b_iterator.next()))){
				msg = "MOVE_RESP/NO";
				return msg;
			}
	    }
		
		setEndShell(Integer.parseInt(_snum));//목적지 도착 후 새로운 목적지 설정위함
		
		closed[ex][ey] = false;//경로가 안비워지는 문제때문에 이것저것 만지다 남은것 이거 지우면 에러생김
		
		AStar(); 
		 
		path = new ArrayList<Shell>();
		
		//@@ 여긴 경로 보여주는건데 이걸 문자로 해서 보내주면 될듯
		 if(closed[ex][ey]){//목적지까지 경로가 있다면 시행
	        //Trace back the path 
	         System.out.println("Path: ");
	         Shell current = map[ex][ey];
	         
	         //경로 리스트에 넣어줌
	         path.clear();//아니 클리어를 해줬는데 왜 안지워지고 그경로 그대로 나온
	         path.add(current);
	         
	         while(current.parent!=null){
	             current = current.parent;
	             path.add(current);
	         } 
	         
	         int size_path = (int)path.size();
	        // msg += path.get(size_path-1).num + "-" +path.get(size_path-1).xpos + "," + path.get(size_path-1).ypos;
	         msg += "PATH/" + path.get(size_path-1).num;
	         if(size_path >2){
		         for(int i = size_path - 2; i >= 0; i--){
		        	// msg += " -> " + path.get(i).num + "-" + path.get(i).xpos + "," + path.get(i).ypos;
		        	 msg += "/" + path.get(i).num;
		         }
	         }
	        
	         System.out.println();
		 }
		
		else msg ="NOPATH";//없으면 없다
		
		return msg;
	}
}