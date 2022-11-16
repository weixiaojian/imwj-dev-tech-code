package bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wj
 * @create 2022-11-01 15:26
 */
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();


    public void initDataMethod(){
        System.out.println("UserDao执行：init-method");
        hashMap.put("10001", "小傅哥");
        hashMap.put("10002", "八杯水");
        hashMap.put("10003", "阿毛");
    }

    public void destroyDataMethod(){
        System.out.println("UserDao执行：destroy-method");
        hashMap.clear();
    }
    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}
