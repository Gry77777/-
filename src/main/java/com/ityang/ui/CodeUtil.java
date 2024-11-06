package com.ityang.ui;

import java.util.Random;

public class CodeUtil {
    public static void main(String[] args) {
        System.out.println(getCode());
    }

    public static String getCode() {
        String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(code.length());
            sb.append(code.charAt(index));
        }
        sb.append(random.nextInt(10));
        String code1 = sb.toString();
        char[] codes = code1.toCharArray();
        for (int i = 0; i < code1.length(); i++) {
            int index = random.nextInt(codes.length);
            char temp = codes[i];
            codes[i] = codes[index];
            codes[index] = temp;

        }
        return new String(codes);


    }
}
