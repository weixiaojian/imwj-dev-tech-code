package bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wj
 * @create 2022-11-01 15:26
 */
public class UserDao {

    private String uName;

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "上单");
        hashMap.put("10002", "中单");
        hashMap.put("10003", "打野");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId) + uName;
    }


    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
