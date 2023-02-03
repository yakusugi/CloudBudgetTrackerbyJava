package com.undeniabledreams.cloudbudgettrackerbyjava.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.undeniabledreams.cloudbudgettrackerbyjava.R;
import com.undeniabledreams.cloudbudgettrackerbyjava.core.BudgetTrackerUserDto;
import com.undeniabledreams.cloudbudgettrackerbyjava.dao.BudgetTrackerUserDao;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private BudgetTrackerUserDao budgetTrackerUserDao;
    private BudgetTrackerUserDto budgetTrackerUserDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText id = (EditText) findViewById(R.id.sign_up_user_id);
        final EditText userPass = (EditText) findViewById(R.id.sign_up_user_pass);
        Button signInBtn = (Button) findViewById(R.id.sign_up_btn1);

        budgetTrackerUserDto = new BudgetTrackerUserDto();
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = id.getText().toString();
                String userPassword = userPass.getText().toString();

                budgetTrackerUserDto = new BudgetTrackerUserDto();
                budgetTrackerUserDto.setId(userId);
                budgetTrackerUserDto.setPassword(userPassword);
                budgetTrackerUserDao = new BudgetTrackerUserDao(SignUpActivity.this);
                try {
                    budgetTrackerUserDao.insertIntoLoginTbl(budgetTrackerUserDto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}