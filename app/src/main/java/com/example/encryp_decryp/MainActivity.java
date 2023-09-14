package com.example.encryp_decryp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button encryptionButton;
    private Button hashingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        encryptionButton = findViewById(R.id.button1);
        hashingButton = findViewById(R.id.button2);

        encryptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),encrypt_menuActivity.class);
                startActivity(i);
            }
        });

        hashingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(getApplicationContext(),hash_menuActivity.class);
                 startActivity(i);
            }
        });
    }
}
