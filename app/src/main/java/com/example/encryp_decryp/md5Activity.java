package com.example.encryp_decryp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class md5Activity extends AppCompatActivity {

    private EditText messageEditText;
    private Button encryptButton;
    private Button copyButton;
    private Button resetButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md5_activity);

        // Initialize views
        messageEditText = findViewById(R.id.messageEditText);
        encryptButton = findViewById(R.id.button1);
        copyButton = findViewById(R.id.button3);
        resetButton = findViewById(R.id.button4);

        // Set click listeners for buttons
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String message = messageEditText.getText().toString();

                    // Calculate MD5 hash
                    String md5Hash = calculateMD5Hash(message);

                    // Display the MD5 hash
                    messageEditText.setText("MD5 Hash: " + md5Hash);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(md5Activity.this, "Hashing failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String md5Hash = messageEditText.getText().toString();
                // Implement your copy logic here
                // For example, you can copy the text to the clipboard
                // and show a toast message
                // Note: You need to add clipboard functionality
                Toast.makeText(md5Activity.this, "MD5 Hash copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageEditText.setText("");
            }
        });
    }

    // Method to calculate MD5 hash
    private String calculateMD5Hash(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageBytes = message.getBytes();
        md.update(messageBytes);
        byte[] digest = md.digest();

        BigInteger no = new BigInteger(1, digest);
        String md5Hash = no.toString(16);
        while (md5Hash.length() < 32) {
            md5Hash = "0" + md5Hash;
        }

        return md5Hash;
    }
}
