package com.everydayratings.homework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by surface on 2016/9/5.
 */
public class SQLiteDBService extends SQLiteOpenHelper {
    private final static int DB_VERSION = 1;
    static final String DB_NAME = "sunbabyDB";
    static final String TB_NAME = "sunbaby";
    static final String[] FROM = new String[]{"username", "pwd", "name", "phone", "email"};

    public SQLiteDBService(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// 创建数据表
        String createTable = "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + //索引字段
                "username VARCHAR(32), " +
                "pwd VARCHAR(32), " +
                "name VARCHAR(32), " +
                "phone VARCHAR(16), " +
                "email VARCHAR(64))";
        db.execSQL(createTable);
    }

    public Cursor query(String sql, String[] args) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        return cursor;
    }

    public void requeryAllData(SimpleCursorAdapter adapter) {   // 重新查询的自定义方法
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TB_NAME, null);
        adapter.changeCursor(cur); //更改 Adapter的Cursor
    }

    public void addData(String username, String pwd, String name, String phone, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues(5); // 创建含 5 个字段的 ContentValues对象
        cv.put(FROM[0], username);
        cv.put(FROM[1], pwd);
        cv.put(FROM[2], name);
        cv.put(FROM[3], phone);
        cv.put(FROM[4], email);
        db.insert(TB_NAME, null, cv);  // 新增1个记录
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
