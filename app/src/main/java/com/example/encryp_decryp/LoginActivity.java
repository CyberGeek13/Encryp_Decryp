package com.example.encryp_decryp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    TextView loginRegisterText;
    EditText editTextEmail, editTextPassword;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        loginRegisterText = findViewById(R.id.loginRegisterText);
        loginRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });

        loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(dbHelper.isValidLogin(email,password)){
                    Toast.makeText(LoginActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
