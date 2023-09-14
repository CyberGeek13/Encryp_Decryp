package com.example.encryp_decryp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import android.util.Base64;
import java.util.Arrays; // Add this import

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class aesActivity extends AppCompatActivity {

    private EditText messageEditText;
    private EditText keyEditText;
    private EditText decryptedTextEditText;
    private Button encryptButton;
    private Button decryptButton;
    private Button copyButton;
    private Button resetButton;
    private Button generateKeyButton;

    private SecretKey secretKey;
    private byte[] iv; // Initialization Vector

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes);

        // Initialize views
        messageEditText = findViewById(R.id.messageEditText);
        keyEditText = findViewById(R.id.keyEditText);
        decryptedTextEditText = findViewById(R.id.decryptedTextEditText);
        encryptButton = findViewById(R.id.button1);
        decryptButton = findViewById(R.id.button2);
        copyButton = findViewById(R.id.button3);
        resetButton = findViewById(R.id.button4);
        generateKeyButton = findViewById(R.id.generateKeyButton); // Assuming you add this button to your layout XML

        // Generate a random key when the "Generate Key" button is clicked
        generateKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                    keyGenerator.init(256, new SecureRandom());
                    secretKey = keyGenerator.generateKey();

                    // Generate a random IV
                    SecureRandom random = new SecureRandom();
                    iv = new byte[12]; // 96 bits IV for GCM mode
                    random.nextBytes(iv);

                    keyEditText.setText(Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        // Set click listeners for buttons
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String message = messageEditText.getText().toString();

                    if (secretKey == null) {
                        Toast.makeText(aesActivity.this, "Please generate a key", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Create an AES cipher and initialize it for encryption
                    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                    GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv); // 128-bit auth tag length
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

                    // Encrypt the message
                    byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

                    // Combine IV, ciphertext, and authentication tag
                    byte[] ciphertextWithTag = new byte[iv.length + encryptedBytes.length];
                    System.arraycopy(iv, 0, ciphertextWithTag, 0, iv.length);
                    System.arraycopy(encryptedBytes, 0, ciphertextWithTag, iv.length, encryptedBytes.length);

                    // Convert the combined bytes to a Base64-encoded string
                    String encryptedText = Base64.encodeToString(ciphertextWithTag, Base64.DEFAULT);

                    // Display the encrypted text
                    decryptedTextEditText.setText(encryptedText);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(aesActivity.this, "Encryption failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String encryptedText = decryptedTextEditText.getText().toString();

                    if (secretKey == null) {
                        Toast.makeText(aesActivity.this, "Please generate a key", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Decode the Base64-encoded encrypted text
                    byte[] ciphertextWithTag = Base64.decode(encryptedText, Base64.DEFAULT);

                    // Extract IV and encrypted bytes
                    iv = Arrays.copyOfRange(ciphertextWithTag, 0, iv.length);
                    byte[] encryptedBytes = Arrays.copyOfRange(ciphertextWithTag, iv.length, ciphertextWithTag.length);

                    // Create an AES cipher and initialize it for decryption
                    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                    GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv); // 128-bit auth tag length
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

                    // Decrypt the message
                    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

                    // Convert the decrypted bytes to a string
                    String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

                    // Display the decrypted text
                    decryptedTextEditText.setText("Decrypted Text: " + decryptedText);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(aesActivity.this, "Decryption failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your copy logic here
                String decryptedText = decryptedTextEditText.getText().toString();
                // Example: Just display a toast message
                Toast.makeText(aesActivity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all EditText fields
                messageEditText.setText("");
                keyEditText.setText("");
                decryptedTextEditText.setText("");
                secretKey = null;
                iv = null;
            }
        });
    }
}
