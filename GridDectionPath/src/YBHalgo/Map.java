package YBHalgo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Map{
	int map_size;
	Shell map[][];//���迭�� ���� ����
	int px,py;//���߿� Ŭ���̾�Ʈ ������ �˷��ֱ�����
	
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
	
	void setingBlock(){//��ø����� �ߺ������ʰ� �������� ����� ������
		//Math.pow(map_size, 2.0) / 10
		
		while(bcnt != max_block){//�ּ� 0 �ִ� 1000
			block.add((int)(Math.random() * max_block + 1));
			bcnt = block.size();
		}

		System.out.println("bcnt" + bcnt);
	}
	
	void setingPlayer(){
		//�������� �÷��̾� ��ġ ���ϱ�
				int pnum = (int)(Math.random() *  (map_size * map_size - max_block) + 1);//��ü - �� = ���ƴѰ�
				System.out.println("pnum" + pnum);
				px = nonblock.get(pnum).xpos;
				py = nonblock.get(pnum).ypos;
				System.out.println(px + " " + py);
	}
	
	void setingMap(){//�� �����κ�
		int shell_num = 0;
		map = new Shell[map_size][map_size];//�켱 ���� ������ְ�
		
		setingBlock();//������
		
		//�� ���� �־���
		for(int x = 0; x < map_size; x++){//�� ���� �ڱ� ��ȣ, ��ǥ , ������ �־���
			for(int y = 0; y< map_size; y++){
				map[x][y] = new Shell();
				map[x][y].num = shell_num;
				
				while (b_iterator.hasNext()){//����ȣ�� ������ ������ ��������
					System.out.println((int)b_iterator.next());
					if(shell_num == (int)b_iterator.next()){
						map[x][y].block = true;
					}
			    }
				
				map[x][y].xpos = x;
				map[x][y].ypos = y;
				
				if(map[x][y].block == false){//���� �ƴϸ� �ͺ� ��ü¿� �־��� �̰� �ͺ��ΰ��� �������� �÷��̾� ��ġ �̱� ����
					nonblock.add(map[x][y]);
				}
				shell_num++;
			}
		}
		
		setingPlayer();//�÷��̾� ��ġ ����
	}
}