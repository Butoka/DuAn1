package helper;

import entity.NguoiDung;

public class Auth {

    public static NguoiDung user = null;

    public static void clear() {
        Auth.user = null;
    }

    public static boolean isLogin() {
        return Auth.user != null;
    }

    public static String getManager() {

        return user.getPhanQuyen();
    }
    public static String getUsername() {

        return user.getTenND();
    }
    public static String getTrangThai() {

        return user.getTrangThai();
    }
}
