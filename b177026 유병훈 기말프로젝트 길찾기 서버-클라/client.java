package ybh_chat_client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.lang.*;
/*public class client {
	Scanner scan = new Scanner(System.in);
	String strw;
	
	public void go(){
		try{
			while(true){
				Socket cs= new Socket("223.194.97.2",5000);
				
				PrintWriter wt = new PrintWriter(cs.getOutputStream());
				String str = scan.nextLine();
				wt.println(str);
				//wt.close();
				//System.out.println(wt);
				wt.flush();
				
				InputStreamReader sr = new InputStreamReader(cs.getInputStream());
				BufferedReader rd = new BufferedReader(sr);
				String strr = rd.readLine();
				System.out.println(strr);
				rd.close();
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	

	public static void main(String[] args){
		client cl = new client();
		cl.go();
	}
}*/

public class client{
	Scanner scan = new Scanner(System.in);
	
	public void go(){
		try{
			ServerSocket serverSock = new ServerSocket(5000);
			
			while(true){
				Socket cs = serverSock.accept();
				
				InputStreamReader sr = new InputStreamReader(cs.getInputStream());
				BufferedReader rd = new BufferedReader(sr);
				String strr = rd.readLine();
				System.out.println(strr);
				//rd.close();
				
				
				PrintWriter wt = new PrintWriter(cs.getOutputStream());
				String strw = scan.nextLine();
				wt.println(strw);
				wt.close();
				//System.out.println(wt);
			}
		}
		
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public static void main(String[] args){
		client sv = new client();
		sv.go();
	}
}
