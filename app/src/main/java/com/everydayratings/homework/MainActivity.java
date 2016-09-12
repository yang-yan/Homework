package com.everydayratings.homework;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final String TB_NAME = "sunbaby";
    static final String[] FROM = new String[]{"username", "pwd", "name", "phone", "email"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView rightText = (TextView) findViewById(R.id.title_btn_right);
        rightText.setVisibility(View.GONE);

        SQLiteDBService dbService = new SQLiteDBService(this);
        Cursor cur = dbService.query("SELECT * FROM " + TB_NAME, null);  // 查询数据
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item, cur, FROM,
                new int[]{R.id.show_username, R.id.show_pwd, R.id.show_reallyname, R.id.show_phone, R.id.show_email}, 0);
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);           // 设置 Adapter
        dbService.requeryAllData(adapter); // 调用自定义方法, 重新查询及设置按钮状态
    }

}
