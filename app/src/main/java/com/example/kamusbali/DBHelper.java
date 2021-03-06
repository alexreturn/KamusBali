package com.example.kamusbali;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    public static final String DATABASE_NAME = "db_kamus";
    public static final int DATABASE_VERSION = 1;

    private String DATABASE_LOCATION = "";
    private String DATABASE_FULL_PATH = "";

    private final String TBL_BALI_INDO = "bali_indo";
    private final String TBL_INDO_BALI = "indo_bali";
    private final String TBL_BOOKMARK = "bookmark";

    private final String COL_KEY = "key";
    private final String COL_VALUE = "value";

    public SQLiteDatabase mDB;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        DATABASE_LOCATION = "data/data/"+mContext.getPackageName()+"/database/";
        DATABASE_FULL_PATH = DATABASE_LOCATION + DATABASE_NAME;

        if(!isExistingDB()){
            try {
                File dbLocation = new File(DATABASE_LOCATION);
                dbLocation.mkdirs();

                extractAssetToDatabaseDirectory(DATABASE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_FULL_PATH, null);
    }

    boolean isExistingDB(){
        File file = new File(DATABASE_FULL_PATH);
        return file.exists();
    }

    public void extractAssetToDatabaseDirectory(String fileName) throws IOException {
        int length;
        InputStream sourceDatabase = this.mContext.getAssets().open(fileName);
        File destinationPath = new File(DATABASE_FULL_PATH);
        OutputStream destination = new FileOutputStream(destinationPath);

        byte[] buffer = new byte[4096];
        while ((length = sourceDatabase.read(buffer))>0){
            destination.write(buffer, 0, length);
        }

        sourceDatabase.close();
        destination.flush();
        destination.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getWord(int dicType){
        String tableName = getTableName(dicType);
        String q = "SELECT * FROM "+tableName;
        Cursor result = mDB.rawQuery(q, null);

        ArrayList<String> source = new ArrayList<>();
        while(result.moveToNext()){
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;
    }

    public Word getWordBookmark(String key){
        String q = "SELECT * FROM bookmark WHERE upper([key]) = upper(?)";
        Cursor result = mDB.rawQuery(q,new String[]{key});

        Word word = new Word();
        while(result.moveToNext()){
            word.key = result.getString(result.getColumnIndex(COL_KEY));
            word.value = result.getString(result.getColumnIndex(COL_VALUE));
        }
        return word;
    }

    public Word getWord(String key, int dicType){
        String tableName = getTableName(dicType);
        String q = "SELECT * FROM "+tableName+" WHERE upper([key]) = upper(?)";
        Cursor result = mDB.rawQuery(q,new String[]{key});

        Word word = new Word();
        while(result.moveToNext()){
            word.key = result.getString(result.getColumnIndex(COL_KEY));
            word.value = result.getString(result.getColumnIndex(COL_VALUE));
        }
        return word;
    }

    public void addBookmark(Word word){
        try{
            String q = "INSERT INTO bookmark(["+COL_KEY+"],["+COL_VALUE+"]) VALUES (?,?);";
            mDB.execSQL(q, new Object[]{word.key, word.value});
        } catch (SQLException ex){

        }
    }

    public void removeBookmark(Word word){
        try{
            String q = "DELETE FROM bookmark WHERE upper(["+COL_KEY+"]) = upper(?) AND ["+COL_VALUE+"] = ?";
            mDB.execSQL(q, new Object[]{word.key, word.value});
        } catch (SQLException ex){

        }
    }

    public void removeBookmark(String key){
        try{
            String q = "DELETE FROM bookmark WHERE upper(["+COL_KEY+"]) = upper(?)";
            mDB.execSQL(q, new Object[]{key});
        } catch (SQLException ex){

        }
    }

    public String getTableName(int dicType){
        String tableName="";
        if(dicType == R.id.action_bali_indo){
            tableName = TBL_BALI_INDO;
        } else if (dicType == R.id.action_indo_bali){
            tableName = TBL_INDO_BALI;
        }
        return tableName;
    }

    public ArrayList<String> getAllWordFromBookmark(){
        String q = "SELECT * FROM bookmark ORDER BY [date] DESC;";
        Cursor result = mDB.rawQuery(q,null);

        ArrayList<String> source = new ArrayList<>();
        while(result.moveToNext()){
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;
    }

    public boolean isWordMark(Word word){
        String q = "SELECT * FROM bookmark WHERE upper([key]) = upper(?) AND [value] = ?";
        Cursor result = mDB.rawQuery(q,new String[]{word.key, word.value});

        return result.getCount() > 0;
    }

    public Word getWordFromBookmark(String key){
        String q = "SELECT * FROM bookmark WHERE upper([key]) = upper(?)";
        Cursor result = mDB.rawQuery(q,new String[]{key});
        Word word = null;
        while(result.moveToNext()){
            word = new Word();
            word.key = result.getString(result.getColumnIndex(COL_KEY));
            word.value = result.getString(result.getColumnIndex(COL_VALUE));
        }
        return word;
    }

    public void clearBookmark() {
        try{
            String q = "DELETE FROM bookmark;";
            mDB.execSQL(q);
        } catch (SQLException ex){

        }
    }
}

