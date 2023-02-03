package com.undeniabledreams.cloudbudgettrackerbyjava.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.undeniabledreams.cloudbudgettrackerbyjava.R;
import com.undeniabledreams.cloudbudgettrackerbyjava.core.BudgetTrackerUserDto;
import com.undeniabledreams.cloudbudgettrackerbyjava.dao.BudgetTrackerUserDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;

public class LoginActivity extends AppCompatActivity {

    private BudgetTrackerUserDao budgetTrackerUserDao;
    private BudgetTrackerUserDto budgetTrackerUserDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText userId = (EditText) findViewById(R.id.edit_user_id);
        EditText userPass = (EditText) findViewById(R.id.edit_user_pass);
        Button signInBtn = (Button) findViewById(R.id.sign_in_btn);
        TextView signUpText = (TextView) findViewById(R.id.sign_in_text);

        budgetTrackerUserDto = new BudgetTrackerUserDto();

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = userId.getText().toString();
                String userPassword = userPass.getText().toString();

                budgetTrackerUserDto = new BudgetTrackerUserDto();
                budgetTrackerUserDto.setId(id);
                budgetTrackerUserDto.setPassword(userPassword);

                budgetTrackerUserDao = new BudgetTrackerUserDao(LoginActivity.this);
                try {
                    int result = budgetTrackerUserDao.logIn(budgetTrackerUserDto);
                    if (result == 1) {
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        // Start the next activity or do something here
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(LoginActivity.this, "Error in logging in!", Toast.LENGTH_SHORT).show();
                    Log.e("IOException", e.toString());
                }
            }
        });


    }
}