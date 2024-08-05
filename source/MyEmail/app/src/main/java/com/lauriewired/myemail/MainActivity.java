package com.lauriewired.myemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.lauriewired.myemail.databinding.ActivityMainBinding;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_STORAGE = 2;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_STORAGE);
        } else {
            readFileAndDisplay();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFileAndDisplay();
            } else {
                Toast.makeText(this, "Permission Denied to read storage", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void readFileAndDisplay() {
        TextView emailBody = binding.emailBody;
        String filePath = "/sdcard/notepad_shared.txt";
        String content = readFile(filePath);
        if (content != null) {
            emailBody.setText(content);
        } else {
            Toast.makeText(this, "Failed to read file", Toast.LENGTH_LONG).show();
            emailBody.setText("Error loading content...");
        }
    }

    private String readFile(String filePath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
