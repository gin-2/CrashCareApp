package me.relex.circleindicator.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by YoungJung on 2016-08-08.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    String tag = "SQLite";
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 user이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE user (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, tel TEXT, carnum TEXT);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String sql= "drop table user;";
        db.execSQL(sql);
        onCreate(db);
    }

    void delete() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from user where _id=(SELECT MAX(_id) FROM user);");
        Log.d(tag, "delete 완료");
        db.close();

    }

    void update() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update user set name='Park' where _id=(SELECT MAX(_id) FROM user);");
        Log.d(tag, "update 완료");
        db.close();

    }

    public String getResult() {
        SQLiteDatabase db = getReadableDatabase();
        String result= "";
        Cursor c = db.rawQuery("SELECT * FROM user WHERE _id = (SELECT MAX(_id) FROM user);",null);
        while(c.moveToNext()) {
            result += c.getString(1)
                    + "\n";
        }
        return result;
    }

    public String getResult1() {
        SQLiteDatabase db = getReadableDatabase();
        String result= "";
        Cursor c = db.rawQuery("SELECT * FROM user WHERE _id = (SELECT MAX(_id) FROM user);",null);
        while(c.moveToNext()) {
            result += c.getString(2)
                    + "\n";
        }
        return result;
    }

    public String getResult2() {
        SQLiteDatabase db = getReadableDatabase();
        String result= "";
        Cursor c = db.rawQuery("SELECT * FROM user WHERE _id = (SELECT MAX(_id) FROM user);",null);
        while(c.moveToNext()) {
            result += c.getString(3)
                    + "\n";
        }
        return result;
    }


    public void insert (String name, String tel, String carnum) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("tel",tel);
        values.put("carnum",carnum);
        db.insert("user", null, values);
        db.close();
    }
}