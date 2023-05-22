package com.imwj.design;

/**
 * 投注用户
 * @author wj
 * @create 2023-05-22 15:32
 */
public class BetUser {

    private String userName;  // 用户姓名
    private int userWeight;   // 用户权重

    public BetUser() {
    }

    public BetUser(String userName, int userWeight) {
        this.userName = userName;
        this.userWeight = userWeight;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

}
