package com.example.kamusbali;

public class DB {
    public static String[] getData(int id){
        if (id == R.id.action_bali_indo){
            return getBaliIndo();
        } else if (id == R.id.action_indo_bali){
            return getIndoBali();
        }
        return new String[0];
    }

    public static String[] getBaliIndo(){
        String[] source = new String[]{
                "Niki",
                "Kruna",
                "Sane",
                "Ngangge",
                "Basa",
                "Bali",
        };
        return source;
    }

    public static String[] getIndoBali(){
        String[] source = new String[]{
                "Ini",
                "Kata",
                "Yang",
                "Menggunakan",
                "Bahasa",
                "Indonesia"
        };
        return source;
    }
}
