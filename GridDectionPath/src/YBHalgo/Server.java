package YBHalgo;
import YBHalgo.*;

public class Server {
	public static void main(String args[]){
		boolean goNext = false;
		int goShellNum = 0;
		
		LogInOut lio = new LogInOut();
		
		if(lio.logOn("test2", 5678)){
			//�α��εǸ� �� ��������
			Map map = new Map();
			map.setingMap();
			map.AStar();
			map.printPath();
			
			
			goNext = true;
			//���� �޽����� goNext == true 
			if(goNext){//�� �̵��ϰڴٰ� �ϸ� 
				map.moveMap();//��Ʈ��� ��ǥ �̵�
				
			}
			//�����Ŀ� ���ο� ������ ����
			
			//map.setEndShell(goShellNum);//�������� �̵��� �� ����
			
			
		}
	}
}
