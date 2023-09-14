package com.example.encryp_decryp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class RegistrationActivity extends AppCompatActivity {
    Button registerBtn;
    TextView registerLoginText;
    EditText editTextName,editTextEmail, editTextMobile,editTextPassword;

    DatabaseHelper databaseHelper;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        databaseHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextPassword = findViewById(R.id.editTextPassword);
        registerBtn = findViewById(R.id.registerButton);
        registerLoginText = findViewById(R.id.registerLoginText);

        // Register Btn functionality
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String mobile = editTextMobile.getText().toString();
                String password = editTextPassword.getText().toString();

                if(isValidated()) {

                    if(databaseHelper.isEmailRegistered(email)){
                        Toast.makeText(RegistrationActivity.this, "User is already registered with email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        long result = databaseHelper.insertRegistrationDetails(name,email,mobile,password);
                        if(result != -1){
                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }
                    }
                }
            }
        });

        // for redirecting to login activity
        registerLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
    }
    // For validating the user
    public Boolean isValidated(){
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(name.length() < 5){
            editTextName.setError("Name should be at least 5 characters");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email address");
            return false;
        }

        if (mobile.length() != 10) {
            editTextMobile.setError("Mobile number should be 10 digits");
            return false;
        }
        if (password.length() < 8) {
            editTextPassword.setError("Password should be at least 8 characters");
            return false;
        }

        return true;
    }
}