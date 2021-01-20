package com.example.mymap.home_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymap.R;

public class login1 extends AppCompatActivity {
    private EditText user, pass;
    private Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.password);
        but = (Button) findViewById(R.id.loginButton);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String example = "admin";
                if(user.getText().toString().equals(example) && pass.getText().toString().equals(example))
                {
                    Intent intent = new Intent(login1.this, HomeActivity.class);
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Username hoặc password sai, vui lòng nhập lại!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}