package com.everydayratings.homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    static final String TB_NAME = "sunbaby";
    static final String[] FROM = new String[]{"username", "pwd", "name", "phone", "email"};
    EditText etUserName, etPwd, etName, etPhone, etEmail;
    CheckBox checkBox;
    String username;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView titleText = (TextView) findViewById(R.id.title_name);
        titleText.setText("登录");
        ImageView imgBack = (ImageView) findViewById(R.id.title_btn_back);
        imgBack.setVisibility(View.GONE);

        etUserName = (EditText) findViewById(R.id.value_username);
        etPwd = (EditText) findViewById(R.id.value_pwd);
        checkBox = (CheckBox) findViewById(R.id.checkBox_rember_pwd);

        getSharedPreferencesData();
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = etUserName.getText().toString().trim();
                String pwdStr = etPwd.getText().toString().trim();
                if (validateInput(usernameStr, pwdStr) && checkUserName(usernameStr, pwdStr)) {
                    if (checkBox.isChecked()) {
                        saveDataIntoSharedPreferences();
                        loginSuccess();
                    } else {
                        Toast.makeText(LoginActivity.this, "未勾选记住密码", Toast.LENGTH_LONG).show();
                        clearSharedPreferencesData();
                    }
                }
            }
        });

        findViewById(R.id.title_btn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

    }

    private void getSharedPreferencesData() {
        //使用SharePreferences取出保存的数据，并把数据显示在手机屏幕上
        SharedPreferences loginInfo = getSharedPreferences("loginInfo", 0); //初始化数据
        //取出数据，如果取出的数据时空时，只需把getString("","")第二个参数设置成空字符串就行了，不用在判断
        username = loginInfo.getString("username", "");
        pwd = loginInfo.getString("pwd", "");
        boolean checkbox = loginInfo.getBoolean("checkbox", false);//获取勾选的状态
        etUserName.setText(username);
        etPwd.setText(pwd);
        checkBox.setChecked(checkbox);
    }

    private void saveDataIntoSharedPreferences() {
        SharedPreferences.Editor editor = getSharedPreferences("loginInfo", 0).edit();
        editor.putString("username", username);
        editor.putString("pwd", pwd);
        editor.commit();
    }


    private void clearSharedPreferencesData() {
        SharedPreferences.Editor editor = getSharedPreferences("loginInfo", 0).edit();
        editor.clear();
        editor.commit();
    }

    public void loginSuccess() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    public boolean checkUserName(String usernameStr, String pwdStr) {
        SQLiteDBService dbService = new SQLiteDBService(this);
        Cursor cur = dbService.query("SELECT * FROM " + TB_NAME + " where username=? and pwd=? ", new String[]{usernameStr, pwdStr});  // 查询数据
        if (cur.getCount() == 0) {
            Toast.makeText(LoginActivity.this, "此账号不存在，请注册。", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateInput(String usernameStr, String pwdStr) {
        if (TextUtils.isEmpty(usernameStr)) {
            Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(pwdStr)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
