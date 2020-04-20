package hu.hajasgabor.oktatoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Utility {
    private Utility() {
    }

    public static List<Integer> RandomQuizAnswers() {
        List<Integer> temp = Arrays.asList(2, 3, 4, 5);
        Collections.shuffle(temp);
        return temp;
    }

    public static List<Integer> RandomQuizQuestions(int questions) {
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < questions; i++) {
            temp.add(i);
        }
        Collections.shuffle(temp);
        return temp;
    }

    public static List<Integer> RandomMatchQuestions(int questions) {
        int n = questions - questions % 4; //itt jó hogy int, mert négyesével írom ki őket és nem kell maradék
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            temp.add(i);
        }
        Collections.shuffle(temp);
        return temp;
    }

    public static List<Integer> RandomMatchAnswers(List<Integer> list) {
        Collections.shuffle(list);
        return list;
    }

    public static String GenerateSalt() {
        SecureRandom rnd = new SecureRandom();
        byte bytes[] = new byte[16];
        rnd.nextBytes(bytes);
        String s = new String(Base64.encode(bytes, Base64.NO_PADDING));
        return s;
    }

    public static String HashPassword(String password, String salt) throws NoSuchAlgorithmException {
        String text = password + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes(Charset.forName("UTF-8")));
        byte[] digest = md.digest();
        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }

    public static String GetThemeName(Context context) {
        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            int themeResId = pi.applicationInfo.theme;
            return context.getResources().getResourceEntryName(themeResId);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static void SaveUser(Context context, String username, String role) {
        SharedPreferences sharedPref = context.getSharedPreferences("hu.hajasgabor.oktatoapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.putString("role", role);
        editor.apply();
    }

    public static UsernameRoleTheme GetUsernameRoleTheme(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hu.hajasgabor.oktatoapp", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        String role = sharedPref.getString("role", "");
        boolean darktheme = sharedPref.getBoolean("dark_theme", true);
        return new UsernameRoleTheme(username, role, darktheme);
    }

    public static void LogOut(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hu.hajasgabor.oktatoapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("username");
        editor.remove("role");
        editor.apply();
    }
}
