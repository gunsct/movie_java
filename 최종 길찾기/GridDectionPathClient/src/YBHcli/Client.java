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
	ArrayList<Integer> path = new ArrayList<Integer>();//��� ��ȣ�� ����
	
	public void go(){
		try{
			while(true){
				Socket cs= new Socket("127.0.0.1",6000);
				
				PrintWriter wt = new PrintWriter(cs.getOutputStream());
				
				if(auto == false)
					str = scan.nextLine();
				
				wt.println(str);
				
				String[] stoken = str.split("/");
				
				if(stoken[0].equals("MOVE_END")){//������ �ٽú����� ��� �ʱ�ȭ 
					path.clear();//����µ� ��ȭ�� ����...
				}
					
				if(str.equals("quit")){
					break;
				}
				
				wt.flush(); 
				
				InputStreamReader sr = new InputStreamReader(cs.getInputStream());
				BufferedReader rd = new BufferedReader(sr);
				String strr = rd.readLine();
				System.out.println(strr);
				
				rd.close();
				
				String[] token = strr.split("/");
				
				switch(token[0]){//��Ŷ ��� �м��ؼ� ����
				case "PATH":
					for(int i = 1;i<token.length;i++){//��θ� ������ �� ��ȣ�鸸 ���� ��� �ڷᱸ���� ����
						path.add(Integer.parseInt(token[i]));
					}
					path_cnt = 0;
					break;
					
				case "OK":
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
							auto = false;//�ѹ� �ڵ����� �����ֱ�����ϸ� �Է¹޴°� �������
						}
						
					} 
					
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					break;
					
				case "LOGOUT_RESP":
					cs.close();
					break;
				}
				
				
				if(token[0].equals("NOPATH") || (token[0].equals("MOVE_RESP") && token[1].equals("NO"))){//��ΰ� ���ų� ���ϋ�  �޽���
					System.out.println("�̵��Ұ� ���Դϴ�");
				} 
			}
			//LOGIN_REQ/test1/1234
			//MOVE_END/������ 
			//MOVE_REQ
			//LOGOUT_REQ
		}
		
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	

	public static void main(String[] args){
		Client cl = new Client();
		cl.go();
	}

}
