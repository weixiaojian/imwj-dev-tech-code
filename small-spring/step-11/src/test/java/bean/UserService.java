package bean;


import java.util.Random;

/**
 * @author wj
 * @create 2022-10-11 17:28
 */
public class UserService implements IUserService{


    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("service查询操作！");
        return "imwj，100001，深圳";
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }


}
