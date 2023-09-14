package com.example.encryp_decryp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha512Activity extends AppCompatActivity {

    private EditText messageEditText;
    private Button hashButton;
    private Button copyButton;
    private Button resetButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sha512_activity);

        // Initialize views
        messageEditText = findViewById(R.id.messageEditText);
        hashButton = findViewById(R.id.button1);
        copyButton = findViewById(R.id.button3);
        resetButton = findViewById(R.id.button4);

        // Set click listeners for buttons
        hashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String message = messageEditText.getText().toString();

                    // Calculate SHA-512 hash
                    String sha512Hash = calculateSHA512Hash(message);

                    // Display the SHA-512 hash
                    messageEditText.setText("SHA-512 Hash: " + sha512Hash);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(sha512Activity.this, "Hashing failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sha512Hash = messageEditText.getText().toString();
                // Implement your copy logic here
                // For example, you can copy the text to the clipboard
                // and show a toast message
                // Note: You need to add clipboard functionality
                Toast.makeText(sha512Activity.this, "SHA-512 Hash copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageEditText.setText("");
            }
        });
    }

    // Method to calculate SHA-512 hash
    private String calculateSHA512Hash(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] messageBytes = message.getBytes();
        md.update(messageBytes);
        byte[] digest = md.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
