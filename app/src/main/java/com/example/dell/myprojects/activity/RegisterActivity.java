package com.example.dell.myprojects.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myprojects.R;
import com.example.dell.myprojects.bean.RegisterBean;
import com.example.dell.myprojects.presenter.PresenterImpl;
import com.example.dell.myprojects.util.Apis;
import com.example.dell.myprojects.view.IView;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, IView {
    private PresenterImpl presenter;
    private EditText reg_phone;
    private EditText reg_verify;
    private EditText reg_pass;
    private TextView reg_login;
    private Button reg_register;
    private String phone;
    private String pass;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void initData() {
        presenter = new PresenterImpl(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        reg_phone = (EditText) findViewById(R.id.reg_phone);
        reg_verify = (EditText) findViewById(R.id.reg_verify);
        reg_pass = (EditText) findViewById(R.id.reg_pass);
        reg_login = (TextView) findViewById(R.id.reg_login);
        reg_login.setOnClickListener(this);
        reg_register = (Button) findViewById(R.id.reg_register);
        reg_register.setOnClickListener(this);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_register:
                phone = reg_phone.getText().toString();
                pass = reg_pass.getText().toString();

                boolean mobile = isMobile(phone);
                if (mobile==true){
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", phone);
                    map.put("pwd", pass);
                    presenter.setRequestData(Apis.REGISTER_URL, map, RegisterBean.class);
                }else if (phone.equals("")||pass.equals("")){
                    Toast.makeText(this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();;
                }else {
                    Toast.makeText(this,"手机号不合法！",Toast.LENGTH_SHORT).show();;
                }
                break;
            case R.id.reg_login:
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
                default:break;
        }
    }

    @Override
    public void setDataSuccess(Object data) {
        if (data instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) data;
            if (registerBean.getStatus().equals("0000")) {
                Toast.makeText(this,registerBean.getMessage()+"",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        }
    }

    @Override
    public void setDataFail(String ex) {

    }

    //判断手机号
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
