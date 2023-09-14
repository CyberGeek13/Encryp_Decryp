package com.example.encryp_decryp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import android.util.Base64;

public class desActivity extends AppCompatActivity {

    private EditText messageEditText;
    private EditText keyEditText;
    private EditText decryptedTextEditText;
    private Button encryptButton;
    private Button decryptButton;
    private Button copyButton;
    private Button resetButton;
    private Button generateKeyButton;

    private SecretKey secretKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des);

        messageEditText = findViewById(R.id.messageEditText);
        keyEditText = findViewById(R.id.keyEditText);
        decryptedTextEditText = findViewById(R.id.decryptedTextEditText);
        encryptButton = findViewById(R.id.button1);
        decryptButton = findViewById(R.id.button2);
        copyButton = findViewById(R.id.button3);
        resetButton = findViewById(R.id.button4);
        generateKeyButton = findViewById(R.id.generateKeyButton);

        generateKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    generateDESKey();
                    // Update the keyEditText with the generated key
                    keyEditText.setText(Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String message = messageEditText.getText().toString();

                    if (secretKey == null) {
                        Toast.makeText(desActivity.this, "Please generate a key", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    byte[] encryptedBytes = cipher.doFinal(message.getBytes());

                    String encryptedText = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);

                    decryptedTextEditText.setText(encryptedText);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(desActivity.this, "Encryption failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String encryptedText = messageEditText.getText().toString();

                    if (secretKey == null) {
                        Toast.makeText(desActivity.this, "Please generate a key", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);

                    byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
                    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

                    String decryptedText = new String(decryptedBytes);

                    decryptedTextEditText.setText(decryptedText);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(desActivity.this, "Decryption failed", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(desActivity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageEditText.setText("");
                keyEditText.setText("");
                decryptedTextEditText.setText("");
                secretKey = null;
            }
        });
    }

    private void generateDESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        // Key size should be 192 bits for Triple DES
        keyGenerator.init(192);
        secretKey = keyGenerator.generateKey();
    }
}
