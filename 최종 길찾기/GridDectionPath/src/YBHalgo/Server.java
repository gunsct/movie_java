package YBHalgo;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import YBHalgo.*;
 
public class Server {
	Scanner scan = new Scanner(System.in);
	
	Map map = new Map();//맵용
	LogInOut lio = new LogInOut();//로그인 체크용
	
	public void network(){//네트워크부분
		try{
			ServerSocket serverSock = new ServerSocket(6000);
			
			while(true){
				Socket cs = serverSock.accept();//소켓 클로스하니까 문제가 생겨서 빼버림
				
				InputStreamReader sr = new InputStreamReader(cs.getInputStream());
				BufferedReader rd = new BufferedReader(sr);
				String strr = rd.readLine();
				System.out.println(strr);
				//rd.close();// 버퍼 클롯를 했더니 문제가 생김
				
				String[] token = strr.split("/");
				String strw = new String();
				
				switch(token[0]){//패킷 헤더 구분
				case "LOGIN_REQ":
					if(lio.logOn(token[1], token[2])){ //로그인확인
						System.out.println("login");
						 
						map.setingMap();//맵셋팅해주고
						 
						strw = map.getInfo();//셋팅된거 토대로 패킷구성해줌
					}
					break;
					  
				case "MOVE_END":
					strw = map.printPath(token[1]);//경로 찾고 알려줌
					break;
					
				case "MOVE_REQ":
					strw = "OK";
					break;
					
				case "MOVE_GO":
					strw = map.moveMap(token[1]);//클라이언트에게 맵이동 신호받고 이동한 뒤 이동된 위치알려줌
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
