package com.example.encryp_decryp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
public class encrypt_menuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encrypt_menu);
    }

    public void onButton1Click(View view) {
        Intent intent = new Intent(this, aesActivity.class);
        startActivity(intent);
        // This method will be called when Button 1 is clicked
        Toast.makeText(this, "Advanced Encryption Standard Selected", Toast.LENGTH_SHORT).show();
    }

    public void onButton2Click(View view) {
        Intent intent = new Intent(this, desActivity.class);
        startActivity(intent);
        // This method will be called when Button 2 is clicked
        Toast.makeText(this, "Triple Data Encryption Standard Selected", Toast.LENGTH_SHORT).show();
    }

    public void onButton3Click(View view) {
        Intent intent = new Intent(this, cipher_Activity.class);
        startActivity(intent);
        // This method will be called when Button 3 is clicked
        Toast.makeText(this, "Caesar CipherText Encryption Selected", Toast.LENGTH_SHORT).show();
    }
    public void onBackButtonClick(View view) {
        // Handle Back button click (navigate back)
        onBackPressed();
    }
}
