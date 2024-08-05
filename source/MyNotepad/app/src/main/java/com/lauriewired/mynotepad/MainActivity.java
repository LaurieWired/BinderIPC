package com.lauriewired.mynotepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lauriewired.mynotepad.databinding.ActivityMainBinding;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final TextInputEditText noteInput = binding.noteInput;
        Button shareButton = binding.shareButton;
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    saveTextToFile(noteInput.getText().toString());
                }
            }
        });
    }

    private void saveTextToFile(String text) {
        String filename = "/sdcard/notepad_shared.txt";
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(text.getBytes());
            Toast.makeText(this, "Text saved to " + filename, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error saving file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                final TextInputEditText noteInput = binding.noteInput;
                saveTextToFile(noteInput.getText().toString());
            } else {
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
