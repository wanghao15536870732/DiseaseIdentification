package com.example.ywang.diseaseidentification.bean.baseData;

public class UserBean {
    private String userId;
    private String userPassword;
    public static boolean isLogin = false;
    public static String isId = null;
    public static String isPw = null;

    public UserBean(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        UserBean.isLogin = isLogin;
    }

    public static String getIsId() {
        return isId;
    }

    public static void setIsId(String isId) {
        UserBean.isId = isId;
    }

    public static String getIsPw() {
        return isPw;
    }

    public static void setIsPw(String isPw) {
        UserBean.isPw = isPw;
    }
}
