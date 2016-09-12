package com.everydayratings.homework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    static final String TB_NAME = "sunbaby";
    static final String[] FROM = new String[]{"username", "pwd", "name", "phone", "email"};
    EditText etUserName, etPwd, etName, etPhone, etEmail;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView titleText = (TextView) findViewById(R.id.title_name);
        titleText.setText("注册");
        TextView rightText = (TextView) findViewById(R.id.title_btn_right);
        rightText.setVisibility(View.GONE);

        etUserName = (EditText) findViewById(R.id.value_username);
        etPwd = (EditText) findViewById(R.id.value_pwd);
        etName = (EditText) findViewById(R.id.value_name);
        etPhone = (EditText) findViewById(R.id.value_phone);
        etEmail = (EditText) findViewById(R.id.value_address);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(RegisterActivity.this);
                progress.setMessage("注册中，请稍候...");
                progress.setCanceledOnTouchOutside(false); //使Dialog之外的屏幕不能点击
                progress.show();

                etUserName.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doRegister();
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                },2000);

            }
        });

        findViewById(R.id.title_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
            }
        });
    }

    public void doRegister() {
        String usernameStr = etUserName.getText().toString().trim();
        String pwdStr = etPwd.getText().toString().trim();
        String nameStr = etName.getText().toString().trim();
        String phoneStr = etPhone.getText().toString().trim();
        String emailStr = etEmail.getText().toString().trim();
        checkInput(usernameStr, pwdStr, nameStr, phoneStr, emailStr);
        checkUserName(usernameStr, pwdStr, nameStr, phoneStr, emailStr);
        registerSuccess(usernameStr, pwdStr, nameStr, phoneStr, emailStr);
    }


    public void registerSuccess(String usernameStr, String pwdStr, String nameStr, String phoneStr, String emailStr) {
        SQLiteDBService dbService = new SQLiteDBService(this);
        Cursor cur = dbService.query("SELECT * FROM " + TB_NAME + " where username=? and pwd=? ", new String[]{usernameStr, pwdStr});  // 查询数据
        dbService.addData(usernameStr, pwdStr, nameStr, phoneStr, emailStr);

        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, MainActivity.class);
        RegisterActivity.this.startActivity(intent);
    }

    public boolean checkUserName(String usernameStr, String pwdStr, String nameStr, String phoneStr, String emailStr) {
        SQLiteDBService dbService = new SQLiteDBService(this);
        Cursor cur = dbService.query("SELECT * FROM " + TB_NAME + " where username=? ", new String[]{usernameStr});  // 查询数据
        if (cur.getCount() > 0) {
            Toast.makeText(RegisterActivity.this, "此账号已存在，请换账号注册。", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkInput(String usernameStr, String pwdStr, String nameStr, String phoneStr, String emailStr){
        if (TextUtils.isEmpty(usernameStr)) {
            Toast.makeText(RegisterActivity.this, "账号不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(pwdStr)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(nameStr)) {
            Toast.makeText(RegisterActivity.this, "姓名不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
