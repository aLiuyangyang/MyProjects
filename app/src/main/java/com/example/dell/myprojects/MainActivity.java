package com.example.dell.myprojects;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dell.myprojects.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_text_time)
    TextView main_text_time;
    private int i=3;
    private SharedPreferences preferences;
    protected SharedPreferences.Editor editor;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int t=msg.what;
            main_text_time.setText(t+"s");
            if (t==0){
                //记忆
                editor.putBoolean("c", true);
                editor.commit();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //记忆
        preferences = getSharedPreferences("User", MODE_PRIVATE);
        editor = preferences.edit();
        //记录状态
        boolean b = preferences.getBoolean("c", false);
        if (b) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            //线程
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (i > 0) {
                        i--;
                        try {
                            sleep(1000);
                            handler.sendEmptyMessage(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
}
