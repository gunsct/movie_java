package YBHalgo;

public class Shell{
	int num;//����ȣ
	boolean block;//���̳ľƴϳ�
	int front_shell, next_shell;//����, ���� ��
	int xpos, ypos;//��ǥ
	double distance_to_end;//���������� �Ÿ�
	
	
	Shell(){
		num = 0;
		block = false;
		front_shell = next_shell = 0;
		xpos = ypos = 0;
		distance_to_end = 0;
	}
	
	void distance(int ex, int ey){//Ŭ�󿡼� ������ ������ ������ ��ǥ �־��ָ� ���������� �Ÿ�����
		distance_to_end = Math.sqrt( Math.pow( (double)(xpos-ex), 2.0 ) + Math.pow( (double)(ypos-ey), 2.0 ) );
	}
}
