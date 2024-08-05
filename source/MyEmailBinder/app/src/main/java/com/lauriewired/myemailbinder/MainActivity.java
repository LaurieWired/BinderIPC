package com.lauriewired.myemailbinder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lauriewired.myemailbinder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Handle incoming intent with email body
        handleIntent();
    }

    private void handleIntent() {
        if (getIntent() != null && getIntent().hasExtra("email_body")) {
            String text = getIntent().getStringExtra("email_body");
            binding.emailBody.setText(text);
        }
    }
}
