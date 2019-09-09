package com.example.ywang.diseaseidentification.bean;

public class User {
    private String userId;
    private String userPassword;
    public static boolean isLogin = false;
    public static String isId = null;
    public static String isPw = null;

    public User(String userId, String userPassword) {
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
        User.isLogin = isLogin;
    }

    public static String getIsId() {
        return isId;
    }

    public static void setIsId(String isId) {
        User.isId = isId;
    }

    public static String getIsPw() {
        return isPw;
    }

    public static void setIsPw(String isPw) {
        User.isPw = isPw;
    }
}
