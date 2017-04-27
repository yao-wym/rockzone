package com.rock.database;

public class DataBaseFiledParams {

    /**
     * 用户表
     */
    public static final String TABLE_USER = "user";

    public static class TableUser {

        public static String ID = "_id";
        public static String ROW_ID = "userid";
        public static String ROW_NICKNAME = "nickname";// 昵称
        public static String ROW_SEX = "sex"; // 性别 1:男 2:女
        public static String ROW_THECITY = "thecity"; // 所在城市
        public static String ROW_HEADIMG = "headimg"; // 头像
        public static String ROW_INTEGRANUM = "Integranum";// 积分
        public static String ROW_CREATEDATE = "createdate";// 注册时间
        public static String ROW_MAIL = "mail";// 绑定邮箱
    }

}
