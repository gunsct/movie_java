package YBHalgo;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import YBHalgo.*;
 
public class Server {
	Scanner scan = new Scanner(System.in);
	
	Map map = new Map();//�ʿ�
	LogInOut lio = new LogInOut();//�α��� üũ��
	
	public void network(){//��Ʈ��ũ�κ�
		try{
			ServerSocket serverSock = new ServerSocket(6000);
			
			while(true){
				Socket cs = serverSock.accept();//���� Ŭ�ν��ϴϱ� ������ ���ܼ� ������
				
				InputStreamReader sr = new InputStreamReader(cs.getInputStream());
				BufferedReader rd = new BufferedReader(sr);
				String strr = rd.readLine();
				System.out.println(strr);
				//rd.close();// ���� Ŭ�Ը� �ߴ��� ������ ����
				
				String[] token = strr.split("/");
				String strw = new String();
				
				switch(token[0]){//��Ŷ ��� ����
				case "LOGIN_REQ":
					if(lio.logOn(token[1], token[2])){ //�α���Ȯ��
						System.out.println("login");
						 
						map.setingMap();//�ʼ������ְ�
						 
						strw = map.getInfo();//���õȰ� ���� ��Ŷ��������
					}
					break;
					  
				case "MOVE_END":
					strw = map.printPath(token[1]);//��� ã�� �˷���
					break;
					
				case "MOVE_REQ":
					strw = "OK";
					break;
					
				case "MOVE_GO":
					strw = map.moveMap(token[1]);//Ŭ���̾�Ʈ���� ���̵� ��ȣ�ް� �̵��� �� �̵��� ��ġ�˷���
					break;
					
				case "LOGOUT_REQ":
					strw = "LOGOUT_RESP";
					break;
				}
				
				PrintWriter wt = new PrintWriter(cs.getOutputStream());
				wt.println(strw);
				wt.close();
				 
				if(strw == "LOGOUT_RESP")
					cs.close();
			}
		}
		
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Server sv = new Server();
		sv.network();
	}
}
