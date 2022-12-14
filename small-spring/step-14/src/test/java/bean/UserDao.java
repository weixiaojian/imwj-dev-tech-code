package bean;

import com.imwj.springframework.context.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wj
 * @create 2022-11-01 15:26
 */
@Component
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "imwj，北京，亦庄");
        hashMap.put("10002", "001，上海，尖沙咀");
        hashMap.put("10003", "002，天津，东丽区");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}
