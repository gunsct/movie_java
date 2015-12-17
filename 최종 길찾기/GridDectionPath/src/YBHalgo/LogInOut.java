package YBHalgo;
import java.util.HashMap;
import java.util.Iterator;

public class LogInOut {
	HashMap<String,String> user_info = new HashMap<String,String>();//아이디를 키로 비밀번호을 벨류로 받기위함
	
	LogInOut(){//생성자에서 셋팅
		setLog();
	}
	
	void setLog(){//유저 정보 넣어주고
		user_info.put("test1", "1234");
		user_info.put("test2", "5678");
		user_info.put("test3", "9012");
		user_info.put("test4", "3456");
		user_info.put("test5", "7890");
		
	}
	
	boolean logOn(String _id, String _pw){//로그인 확인
		Iterator<String> ui_iterator = user_info.keySet().iterator();//같은 함수 내에서 써야되네..가 아니라 매번 선언해야하네
		
		while(ui_iterator.hasNext()){//반복해서 user_info에서 아이디와 비밀번호 확인
			String id = ui_iterator.next();
			String pw = user_info.get(id);
			
			if(_id.equals(id) && _pw.equals(pw)){//맞으면 로그인
				return true;
			}
		}
		return false;
	}
}
