package YBHalgo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;


public class Map{
	public static final int DIAGONAL_COST = 14;//@@
	public static final int V_H_COST = 10;//@@
	    
	int map_size;
	Shell map[][];//���迭�� ���� ����
	PriorityQueue<Shell> open;//@@
    static boolean closed[][];//@@
    
	int px,py;//���߿� Ŭ���̾�Ʈ ������ �˷��ֱ�����
	int ex,ey;
	
	int max_block;
	HashSet<Integer> block = new HashSet<Integer>();//��� ��ȣ���
	Iterator b_iterator = block.iterator(); 
	ArrayList<Shell> nonblock = new ArrayList<Shell>();//�� �ƴѰ͸� ���
	ArrayList<Shell> path = new ArrayList<Shell>();//��θ� ���
	
	int bcnt = 0;
	
	
	Map(){
		map_size = (int)(Math.random() * 99 + 1);//1~100
		max_block = (int)Math.pow(map_size, 2.0) / 10;
		
		System.out.println("��ũ��" + map_size * map_size); 
		System.out.println("��� ��" + max_block);
		
		px = py = 0;
	}
	
	void setingBlock(){//��ø����� �ߺ������ʰ� �������� ��� ��ȣ�� ������
		while(bcnt != max_block){//�ּ� 0 �ִ� 1000
			block.add((int)(Math.random() * map_size*map_size + 1));
			bcnt = block.size();
		}
		System.out.println("�� ��" + bcnt);
	}
	
	void setingPlayer(){//��������  �÷��̾� ��ġ ���ϱ�, nonblock�� mapseting���� ���� �ƴѰ͵鸸 ���� ����� 
				int pnum = (int)(Math.random() *  (map_size * map_size - max_block) + 1);//��ü - ��  = ���ƴѰ� �� ���� 1��
				px = nonblock.get(pnum).xpos;
				py = nonblock.get(pnum).ypos;
				
				System.out.println("���� ��ȣ:" + pnum + "��ġ " + px + " " + py);
	}
	
	void setEndShell(int _snum){//������ ����, ������ �ƴ����� �ٸ������� �Ǻ��ؼ� �˷���
		ex = nonblock.get(_snum).xpos;
		ey = nonblock.get(_snum).ypos;
		System.out.println("������ " + ex + " " + ey);
    }
	
	
	
	void setingMap(){//�� �����κ�
		int shell_num = 0;
		map = new Shell[map_size][map_size];//�켱 ���� ������ְ�
		
		//@@�����°����� �Ǻ��ϱ������ΰŰ���
        closed = new boolean[map_size][map_size];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
             Shell c1 = (Shell)o1;
             Shell c2 = (Shell)o2;

             return c1.finalCost<c2.finalCost?-1:
                     c1.finalCost>c2.finalCost?1:0;
         });//@
		
		setingBlock();//������
		
		//�� ���� �־���
		for(int x = 0; x < map_size; x++){//�� ���� �ڱ� ��ȣ, ��ǥ , ������ �־���
			for(int y = 0; y< map_size; y++){
				map[x][y] = new Shell();
				map[x][y].num = shell_num;
				map[x][y].heuristicCost = Math.abs(x-ex)+Math.abs(y-ey);//@@���������� �Ÿ���
				
				b_iterator = block.iterator();  
				while (b_iterator.hasNext()){//����ȣ�� ������ ������ ��������
					if(shell_num == (int)b_iterator.next()){//������ ����ȣ�� �������ذ��� �̿��� ������ üũ����
						map[x][y].block = true;
					}
			    }
				
				map[x][y].xpos = x;
				map[x][y].ypos = y;
				
				if(map[x][y].block == false){//���� �ƴϸ� �ͺ� ��ü¿� �־��� �̰� �ͺ��ΰ��� �������� �÷��̾� ��ġ �̱� ����
					nonblock.add(map[x][y]);
				}
				shell_num++;//��ȣ �÷���
			}
		}
		
		map[px][py].finalCost = 0;//@@���� ���̶� ���������� �Ÿ� ���ϱ� ����
		
		setingPlayer();//�÷��̾� ��ġ ����
		
		
	}
	
	void checkAndUpdateCost(Shell current, Shell t, int cost){//@@�ֺ� ���� �Ÿ��� üũ�ϴºκ�
        if(t.block == true || closed[t.xpos][t.ypos])return;
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            
            if(!inOpen) open.add(t);
        }
    }
	
	void AStar(){//@@ http://www.codebytes.in/2015/02/a-shortest-path-finding-algorithm.html �������� �쿬�� ���� § �ʰ� ������ ����� �Ἥ ��ġ�µ� ���������ʾ���
	        //add the start location to open list.
			open.clear();//��� ���������� �غ� ��ȭ����
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
	                
	                //�밢���̵�
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
	                
	                //�밢���̵�
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
	
	
	
	String moveMap(String _go){//Ŭ���̾�Ʈ�� ���信 ���� �������� �ٲ�� ��
		String msg = new String();
		
		for(int i = 0; i< path.size(); i++){
			if(_go.equals(Integer.toString(path.get(i).num))){
				px = path.get(i).xpos;
				py = path.get(i).ypos;
				msg += "OK/move shell num:" + path.get(i).num + " x:" + px + " y:" + py;
				return msg;//�����ϸ� �����ִ°Ű�
			}
		}
		
		msg = "MOVE_RESP/NO";//�Ұ����ϸ� �ȵ�
		return msg;
	}
	
	String getInfo(){//�α��� �� �ѱ�� ��Ŷ
		String strw ="LOGIN_RESP/OK";
		strw += "/ũ������/" + Integer.toString(map_size * map_size) + "/�� ��ȣ/";
		
		b_iterator = block.iterator();  
		while (b_iterator.hasNext()){//����ȣ�� ������ ������ ��������
			strw += "/" + Integer.toString((int)b_iterator.next());
	    }
		strw += "/����� ��ġ/" + map[px][py].num + "-" + px + "," + py;
		
		return strw;
	}
	
	String printPath(String _snum){//��θ� ã��  �����ش�
		String msg = new String();
		
		b_iterator = block.iterator();  
		while (b_iterator.hasNext()){//����ȣ�� ������ ������ ��������
			if(_snum.equals(Integer.toString((int)b_iterator.next()))){
				msg = "MOVE_RESP/NO";
				return msg;
			}
	    }
		
		setEndShell(Integer.parseInt(_snum));//������ ���� �� ���ο� ������ ��������
		
		closed[ex][ey] = false;//��ΰ� �Ⱥ������ ���������� �̰����� ������ ������ �̰� ����� ��������
		
		AStar(); 
		 
		path = new ArrayList<Shell>();
		
		//@@ ���� ��� �����ִ°ǵ� �̰� ���ڷ� �ؼ� �����ָ� �ɵ�
		 if(closed[ex][ey]){//���������� ��ΰ� �ִٸ� ����
	        //Trace back the path 
	         System.out.println("Path: ");
	         Shell current = map[ex][ey];
	         
	         //��� ����Ʈ�� �־���
	         path.clear();//�ƴ� Ŭ��� ����µ� �� ���������� �װ�� �״�� ����
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
		
		else msg ="NOPATH";//������ ����
		
		return msg;
	}
}