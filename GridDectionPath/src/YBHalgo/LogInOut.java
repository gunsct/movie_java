package YBHalgo;
import java.util.HashMap;
import java.util.Iterator;

public class LogInOut {
	HashMap<String,String> user_info = new HashMap<String,String>();
	
	LogInOut(){//�����ڿ��� ����
		setLog();
	}
	
	void setLog(){//���� ���� �־��ְ�
		user_info.put("test1", "1234");
		user_info.put("test2", "5678");
		user_info.put("test3", "9012");
		user_info.put("test4", "3456");
		user_info.put("test5", "7890");
		
	}
	
	boolean logOn(String _id, String _pw){
		Iterator<String> ui_iterator = user_info.keySet().iterator();//���� �Լ� ������ ��ߵǳ�..
		
		while(ui_iterator.hasNext()){
			String id = ui_iterator.next();
			String pw = user_info.get(id);
			
			if(_id.equals(id) && _pw.equals(pw)){
				System.out.println(_id + "�� ȯ���մϴ�");
				return true;
			}
		}
		return false;
		
	}
}
