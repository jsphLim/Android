package com.ly.Login;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import db.UserDB;


public class LoginPanel extends AppCompatActivity implements OnClickListener {
    private UserDB userdb;

    private EditText login_name ;
    private  EditText login_password;
    private CheckBox save_password;
    private  Button loginBt;
    private  Button registerBt;
    private  ProgressBar progressBar;
    private  EditText registerUser;
    private  EditText registerPass;
    private  EditText confirmPass;
    private TextView status;
    private  Button confirmRegister;

    public TextView TopBarTitle;
    public Button Top_left;
    public Button Top_right;

    private  boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_panel);
        userdb = new UserDB(this,"UserStore.db",null,1);
        login_name = (EditText) findViewById(R.id.Login_Name);
        login_password = (EditText) findViewById(R.id.Login_Password);
        save_password = (CheckBox) findViewById(R.id.SavePassword);
        loginBt = (Button) findViewById(R.id.loginBt);
        loginBt.setOnClickListener(this);
        registerBt = (Button) findViewById(R.id.registerBt);
        registerBt.setOnClickListener(this);
        registerUser = (EditText) findViewById(R.id.registerUser);
        registerPass = (EditText) findViewById(R.id.registerPass);
        confirmPass = (EditText) findViewById(R.id.confirmpass);
        confirmRegister = (Button) findViewById(R.id.confirmRegister);
        TopBarTitle = (TextView) findViewById(R.id.toolbar_title_tv);
        TopBarTitle.setText("MyDairy");
        Top_left = (Button) findViewById(R.id.toolbar_left_btn);
        Top_left.setOnClickListener(this);
        Top_right = (Button) findViewById(R.id.toolbar_right_btn);
        Top_right.setOnClickListener(this);
        confirmRegister.setOnClickListener(this);
        status = (TextView) findViewById(R.id.status);
        LoginPanel();
        flag=true;
    }

    public JSONObject SetData() throws JSONException {
        JSONObject loginMS = new JSONObject();
        loginMS.put("username",login_name.getText().toString());
        String base64 = Base64.encodeToString(login_password.getText().toString().getBytes(), Base64.DEFAULT);
        loginMS.put("password",base64);
        return loginMS;
    }

    public void RegisterPanel(){
        flag=false;
        Top_left.setVisibility(View.VISIBLE);
        status.setText("注册");
        login_name.setVisibility(View.INVISIBLE);
        login_password.setVisibility(View.INVISIBLE);
        loginBt.setVisibility(View.INVISIBLE);
        registerBt.setVisibility(View.INVISIBLE);
        save_password.setVisibility(View.INVISIBLE);

        registerUser.setVisibility(View.VISIBLE);
        registerPass.setVisibility(View.VISIBLE);
        confirmPass.setVisibility(View.VISIBLE);
        confirmRegister.setVisibility(View.VISIBLE);
    }

    public void LoginPanel(){
        flag=true;
        Top_left.setVisibility(View.INVISIBLE);
        status.setText("登录");
        registerUser.setVisibility(View.INVISIBLE);
        registerPass.setVisibility(View.INVISIBLE);
        confirmPass.setVisibility(View.INVISIBLE);
        confirmRegister.setVisibility(View.INVISIBLE);

        login_name.setVisibility(View.VISIBLE);
        login_password.setVisibility(View.VISIBLE);
        loginBt.setVisibility(View.VISIBLE);
        registerBt.setVisibility(View.VISIBLE);
        save_password.setVisibility(View.VISIBLE);
    }

    public boolean islogin(JSONObject loginMS) throws JSONException {
        String username = loginMS.get("username").toString();
        String base64 = loginMS.get("password").toString();
        String password = new String(Base64.decode(base64.getBytes(),Base64.DEFAULT));
        if(check(username,password)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean check(String username,String password){

        SQLiteDatabase db = userdb.getWritableDatabase();
        String sql = "select * from userData where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    public boolean register(String username,String password){
        SQLiteDatabase db = userdb.getWritableDatabase();
        if (CheckIsDataAlreadyInDBorNot(username)) {
//            Toast.makeText(this,"该用户名已被注册，注册失败",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            ContentValues values=new ContentValues();
            values.put("name",username);
            values.put("password",password);
            db.insert("userData",null,values);
            db.close();
            return true;
        }
    }
    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db= userdb.getWritableDatabase();
        String Query = "Select * from userData where name =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.loginBt:
               boolean isremeber = save_password.isChecked();
            try {
                JSONObject MS = SetData();
                if(MS.get("username").equals("")){
                    Toast.makeText(LoginPanel.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                    login_name.setTextColor(Color.rgb(0,0,0));
                }
                else if(MS.get("password").equals("")){
                    Toast.makeText(LoginPanel.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    login_password.setTextColor(Color.rgb(0,0,0));
                }
                if (islogin(MS)) {
                    if (isremeber) {
                        login_name.setText(MS.get("username").toString());
                        String base64 = MS.get("password").toString();
                        String password = new String(Base64.decode(base64.getBytes(), Base64.DEFAULT));
                        login_password.setText(password);
                    } else {
                        login_name.setText("");
                        login_password.setText("");
                    }
                    Toast.makeText(LoginPanel.this,"登录成功",Toast.LENGTH_SHORT).show();
                    //TODO  Intent intent =
                }
                else{
                    Toast.makeText(LoginPanel.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;
            case R.id.registerBt:
                RegisterPanel();
                break;
            case R.id.confirmRegister:
                if(registerUser.getText().toString().equals("")){
                    Toast.makeText(LoginPanel.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    registerPass.setTextColor(Color.rgb(0,0,0));
                }
                else if(registerPass.getText().toString().equals("")||confirmPass.getText().toString().equals("")){
                    Toast.makeText(LoginPanel.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    registerPass.setTextColor(Color.rgb(0,0,0));
                }
                else if(!confirmPass.getText().toString().equals(registerPass.getText().toString())){
                    Toast.makeText(LoginPanel.this,"两次输入的密码不相同",Toast.LENGTH_SHORT).show();
                    registerPass.setTextColor(Color.rgb(0,0,0));
                    confirmPass.setTextColor(Color.rgb(0,0,0));
                }
                else {
                    String username = registerUser.getText().toString();
                    String password = registerPass.getText().toString();
                    if(register(username,password)){
                        Toast.makeText(LoginPanel.this,"注册成功!",Toast.LENGTH_SHORT).show();
                        registerPass.setText("");
                        registerUser.setText("");
                        confirmPass.setText("");
                        LoginPanel();
                    }
                    else{
                        Toast.makeText(LoginPanel.this,"用户名已被使用",Toast.LENGTH_SHORT).show();
                        registerUser.setTextColor(Color.rgb(0,0,0));
                    }
                }
                break;
            case R.id.toolbar_left_btn:
                    if(!flag){
                        LoginPanel();
                    }
                    break;

            //TODO

        }
    }
}
