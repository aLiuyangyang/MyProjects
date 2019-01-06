package com.example.dell.myprojects.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myprojects.R;
import com.example.dell.myprojects.bean.LoginBean;
import com.example.dell.myprojects.bean.RegisterBean;
import com.example.dell.myprojects.presenter.PresenterImpl;
import com.example.dell.myprojects.util.Apis;
import com.example.dell.myprojects.view.IView;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity implements IView, View.OnClickListener {
    private PresenterImpl presenter;
    private EditText log_phone;
    private EditText log_pass;
    private CheckBox log_remember;
    private TextView log_register;
    private Button log_login;
    private ImageView image;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String phone;
    private String pass;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        log_phone = (EditText) findViewById(R.id.log_phone);
        log_pass = (EditText) findViewById(R.id.log_pass);
        log_remember = (CheckBox) findViewById(R.id.log_remember);
        log_register = (TextView) findViewById(R.id.log_register);
        log_register.setOnClickListener(this);
        log_login = (Button) findViewById(R.id.log_login);
        log_login.setOnClickListener(this);
        presenter=new PresenterImpl(this);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean j_check = sharedPreferences.getBoolean("j_check", false);
        if (j_check){
            String username = sharedPreferences.getString("phone", null);
            String password = sharedPreferences.getString("pass", null);
            log_phone.setText(username);
            log_pass.setText(password);
            log_remember.setChecked(true);
        }
        findViewById(R.id.image).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        log_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        break;

                    case MotionEvent.ACTION_DOWN:
                        log_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_register:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.log_login:
                phone = log_phone.getText().toString();
                pass = log_pass.getText().toString();
                if (log_remember.isChecked()){
                    editor.putString("phone", phone);
                    editor.putString("pass", pass);
                    editor.putBoolean("j_check",true);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }
                boolean mobile = isMobile(phone);
                if (mobile==true){
                    Map<String,String> map=new HashMap<>();
                    map.put("phone", phone);
                    map.put("pwd", pass);
                    presenter.setRequestData(Apis.LOGIN_URL,map,LoginBean.class);
                }else if (phone.equals("")||pass.equals("")){
                    Toast.makeText(this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();;
                }else {
                    Toast.makeText(this,"手机号不合法！",Toast.LENGTH_SHORT).show();;
                }
                break;
                default: break;
        }
    }

    //成功
    @Override
    public void setDataSuccess(Object data) {
           if (data instanceof LoginBean){
               LoginBean loginBean= (LoginBean) data;
               sharedPreferences.edit().putString("userId",loginBean.getResult().getUserId()+"").putString("sessionId",loginBean.getResult().getSessionId()).commit();
               if (loginBean.getStatus().equals("0000")){
                   Toast.makeText(this,loginBean.getMessage()+"",Toast.LENGTH_SHORT).show();
                   Intent intent11=new Intent(LoginActivity.this,ShowActivity.class);
                   editor.putString("sessionId",loginBean.getResult().getSessionId());
                   editor.putString("userId",loginBean.getResult().getUserId()+"");
                   editor.commit();
                   startActivity(intent11);
                   finish();
               }else {
                   editor.remove("sessionId");
                   editor.remove("userId");
                   editor.commit();
                   Toast.makeText(this,loginBean.getMessage(),Toast.LENGTH_SHORT).show();
               }
           }
    }

    //失败
    @Override
    public void setDataFail(String ex) {
        Log.i("TAG",ex);
    }

    public static boolean isMobile(String number) {
        String num = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            return number.matches(num);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.seDestroy();
        }
    }
}
