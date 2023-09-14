package com.example.encryp_decryp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
public class hash_menuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hash_menu);
    }

    public void onButton1Click(View view) {
        Intent intent = new Intent(this, md5Activity.class);
        startActivity(intent);
        // This method will be called when Button 1 is clicked
        Toast.makeText(this, "MD5 Hashing Technique Selected", Toast.LENGTH_SHORT).show();
    }

    public void onButton2Click(View view) {
        Intent intent = new Intent(this, sha256Activity.class);
        startActivity(intent);
        // This method will be called when Button 2 is clicked
        Toast.makeText(this, "SHA-256 Algorithm Selected", Toast.LENGTH_SHORT).show();
    }

    public void onButton3Click(View view) {
        Intent intent = new Intent(this, sha512Activity.class);
        startActivity(intent);
        // This method will be called when Button 3 is clicked
        Toast.makeText(this, "SHA-512 Algorithm Selected", Toast.LENGTH_SHORT).show();
    }
    public void onBackButtonClick(View view) {
        // Handle Back button click (navigate back)
        onBackPressed();
    }
}
