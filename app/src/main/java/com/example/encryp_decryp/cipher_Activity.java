package com.example.encryp_decryp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;

public class cipher_Activity extends AppCompatActivity {

    private EditText messageEditText;
    private EditText keyEditText;
    private EditText decryptedTextEditText;
    private Button encryptButton;
    private Button decryptButton;
    private Button copyButton;
    private Button resetButton;
    private Button generateKeyButton; // New button for key generation

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cipher_activity);

        // Initialize views
        messageEditText = findViewById(R.id.messageEditText);
        keyEditText = findViewById(R.id.keyEditText);
        decryptedTextEditText = findViewById(R.id.decryptedTextEditText);
        encryptButton = findViewById(R.id.button1);
        decryptButton = findViewById(R.id.button2);
        copyButton = findViewById(R.id.button3);
        resetButton = findViewById(R.id.button4);
        generateKeyButton = findViewById(R.id.generateKeyButton); // Initialize the button

        // Set click listeners for buttons
        generateKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate a random key and display it in the keyEditText
                int randomKey = generateRandomKey();
                keyEditText.setText(String.valueOf(randomKey));
            }
        });

        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String message = messageEditText.getText().toString();
                    int key = Integer.parseInt(keyEditText.getText().toString());

                    // Encrypt the message using Caesar cipher
                    String encryptedText = caesarCipherEncrypt(message, key);
                    decryptedTextEditText.setText(encryptedText);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(cipher_Activity.this, "Encryption failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String encryptedText = messageEditText.getText().toString();
                    int key = Integer.parseInt(keyEditText.getText().toString());

                    // Decrypt the message using Caesar cipher
                    String decryptedText = caesarCipherDecrypt(encryptedText, key);
                    decryptedTextEditText.setText(decryptedText);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(cipher_Activity.this, "Decryption failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String decryptedText = decryptedTextEditText.getText().toString();
                // Implement your copy logic here
                // For example, you can copy the text to the clipboard
                // and show a toast message
                // Note: You need to add clipboard functionality
                Toast.makeText(cipher_Activity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageEditText.setText("");
                keyEditText.setText("");
                decryptedTextEditText.setText("");
            }
        });
    }

    // Caesar cipher encryption
    private String caesarCipherEncrypt(String message, int key) {
        StringBuilder result = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                result.append((char) ((character - base + key) % 26 + base));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    // Caesar cipher decryption
    private String caesarCipherDecrypt(String message, int key) {
        return caesarCipherEncrypt(message, 26 - key); // Decrypt by shifting in the opposite direction
    }

    // Generate a random key (you can customize the range)
    private int generateRandomKey() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(26); // Generates a random key between 0 and 25
    }
}
