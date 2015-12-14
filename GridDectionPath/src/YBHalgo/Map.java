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
	HashSet<Integer> block = new HashSet<Integer>();
	Iterator b_iterator = block.iterator(); 
	ArrayList<Shell> nonblock = new ArrayList<Shell>();
	ArrayList<Shell> path = new ArrayList<Shell>();
	
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
			block.add((int)(Math.random() * map_size*map_size + 1));
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
				System.out.println("start " + px + " " + py);
	}
	
	/*void setStartShell(int _snum){
		px = nonblock.get(_snum).xpos;
		py = nonblock.get(_snum).ypos;
		System.out.println(px + " " + py);
	}*/
	
	void setEndShell(int _snum){
		ex = nonblock.get(_snum).xpos;
		ey = nonblock.get(_snum).ypos;
		System.out.println("end " + ex + " " + ey);
    }
	
	
	
	void setingMap(){//맵 설정부분
		int shell_num = 0;
		map = new Shell[map_size][map_size];//우선 맵을 만들어주고
		
		//@@
        closed = new boolean[map_size][map_size];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
             Shell c1 = (Shell)o1;
             Shell c2 = (Shell)o2;

             return c1.finalCost<c2.finalCost?-1:
                     c1.finalCost>c2.finalCost?1:0;
         });//@@
		
		setingBlock();//벽설정
		
		//셀 정보 넣어줌
		for(int x = 0; x < map_size; x++){//각 셀에 자기 번호, 좌표 , 벽여부 넣어줌
			for(int y = 0; y< map_size; y++){
				map[x][y] = new Shell();
				map[x][y].num = shell_num;
				map[x][y].heuristicCost = Math.abs(x-ex)+Math.abs(y-ey);//@@
				
				b_iterator = block.iterator();  
				while (b_iterator.hasNext()){//벽번호랑 같으면 벽으로 설정해줌
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
		
		map[px][py].finalCost = 0;//@@
		
		setingPlayer();//플레이어 위치 정함
		
		
	}
	
	void checkAndUpdateCost(Shell current, Shell t, int cost){
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
	
	
	
	String moveMap(String _go){
		String msg = new String();
		
		for(int i = 0; i< path.size(); i++){
			if(_go.equals(Integer.toString(path.get(i).num))){
				px = path.get(i).xpos;
				py = path.get(i).ypos;
				msg += "OK/move shell num:" + path.get(i).num + " x:" + px + " y:" + py;
				return msg;
			}
		}
		
		msg = "MOVE_RESP/NO";
		return msg;
	}
	
	String getInfo(){//로그인 후 넘기는 패킷
		//LOGIN_RESP:OK:그리드공간 크기정보: 벽이 있는 셀들의 정보:사용자 위치정보\r\n
		String strw ="LOGIN_RESP/OK";
		strw += "/크기정보/" + Integer.toString(map_size * map_size) + "/벽 번호/";
		b_iterator = block.iterator();  
		while (b_iterator.hasNext()){//벽번호랑 같으면 벽으로 설정해줌
			strw += "/" + Integer.toString((int)b_iterator.next());
	    }
		strw += "/사용자 위치/" + map[px][py].num + "-" + px + "," + py;
		
		return strw;
	}
	
	String printPath(String _snum){
		String msg = new String();
		b_iterator = block.iterator();  
		while (b_iterator.hasNext()){//벽번호랑 같으면 벽으로 설정해줌
			if(_snum.equals(Integer.toString((int)b_iterator.next()))){
				msg = "MOVE_RESP/NO";
				return msg;
			}
	    }
		
		setEndShell(Integer.parseInt(_snum));
		
		AStar(); 
		
		//@@ 여긴 경로 보여주는건데 이걸 문자로 해서 보내주면 될듯
		 if(closed[ex][ey]){
	        //Trace back the path 
	         System.out.println("Path: ");
	         Shell current = map[ex][ey];
	         
	         //경로 리스트에 넣어줌
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
		
		else msg ="NOPATH";
		 
		 return msg;
	}
}