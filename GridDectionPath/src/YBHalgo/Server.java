package YBHalgo;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import YBHalgo.*;

public class Server {
	Scanner scan = new Scanner(System.in);
	
	Map map = new Map();
	
	boolean goNext = false;
	int goShellNum = 0;
	
	
	
	LogInOut lio = new LogInOut();
	
	
	public void network(){
		try{
			ServerSocket serverSock = new ServerSocket(6000);
			
			while(true){
				Socket cs = serverSock.accept();
				
				InputStreamReader sr = new InputStreamReader(cs.getInputStream());
				BufferedReader rd = new BufferedReader(sr);
				String strr = rd.readLine();
				System.out.println(strr);
				//rd.close();
				String[] token = strr.split("/");
				 
				String strw = new String();
				
				//����ġ�� �ٲ�
				if(token[0].equals("LOGIN_REQ")){//����� �α��� �䱸�϶� 
					if(lio.logOn(token[1], token[2])){ //�α���
						//�α��εǸ� �� ��������
						//LOGIN_RESP:OK:�׸������ ũ������: ���� �ִ� ������ ����:����� ��ġ����\r\n
						System.out.println("login");
						 
						map.setingMap();//�ʼ������ְ�
						 
						strw = map.getInfo();//���õȰ� ���� ��Ŷ��������
					}
				}   
				 
				if(token[0].equals("MOVE_END")){   
					strw = map.printPath(token[1]);
				} 
				 
				if(token[0].equals("MOVE_REQ")){   
					strw = "OK";
				}    
				
				if(token[0].equals("MOVE_GO")){   
					strw = map.moveMap(token[1]); 
				}
				
				if(token[0].equals("LOGOUT_REQ")){
					strw = "LOGOUT_RESP";
				}
				
				PrintWriter wt = new PrintWriter(cs.getOutputStream());
				//strw = scan.nextLine();
				wt.println(strw);
				wt.close();
				
				if(strw == "LOGOUT_RESP")
					cs.close();
				//System.out.println(wt);
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
