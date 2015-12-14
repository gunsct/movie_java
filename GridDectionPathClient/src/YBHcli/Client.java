package YBHcli;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
	Scanner scan = new Scanner(System.in);
	String str = new String();
	
	boolean auto = false;
	
	int path_cnt = 0;
	ArrayList<Integer> path = new ArrayList<Integer>();
	
	public void go(){
		try{
			while(true){
				Socket cs= new Socket("127.0.0.1",6000);
				
				PrintWriter wt = new PrintWriter(cs.getOutputStream());
				
				if(auto == false)
					str = scan.nextLine();
				
				wt.println(str);
				
				if(str.equals("quit")){
					break;
				}
				//wt.close();
				//System.out.println(wt);
				wt.flush(); 
				
				InputStreamReader sr = new InputStreamReader(cs.getInputStream());
				BufferedReader rd = new BufferedReader(sr);
				String strr = rd.readLine();
				System.out.println(strr);
				
				rd.close();
				
				String[] token = strr.split("/");
				
				
				//스위치로
				if(token[0].equals("PATH")){
					for(int i = 1;i<token.length;i++){
						path.add(Integer.parseInt(token[i]));
						System.out.print(Integer.parseInt(token[i]));
					}
				}
				
				if(token[0].equals("OK")){
					auto = true;
					//path_cnt = 0;
					try {
						System.out.println(path_cnt + " " + path.size());
						if(path_cnt < path.size()){
							str = new String();
							str += "MOVE_GO/" + (int)path.get(path_cnt);
							path_cnt++;
							Thread.sleep(1000);
						}
						else{
							auto = false;
						}
					} 
					
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				
				if(token[0].equals("NOPATH") || (token[0].equals("MOVE_RESP") && token[1].equals("NO"))){
					System.out.println("이동불가 셀입니다");
				} 
				
				if(token[0].equals("LOGOUT_RESP")){
					cs.close();
				}
			}//LOGIN_REQ/test1/1234 
			//MOVE_END/도착지
		}//MOVE_REQ
		//LOGOUT_REQ
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	

	public static void main(String[] args){
		Client cl = new Client();
		cl.go();
	}

}
