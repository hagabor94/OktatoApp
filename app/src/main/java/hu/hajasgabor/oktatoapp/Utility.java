package hu.hajasgabor.oktatoapp;

import android.content.Intent;
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



}
