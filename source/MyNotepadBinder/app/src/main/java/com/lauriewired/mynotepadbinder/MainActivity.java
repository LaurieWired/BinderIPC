package com.lauriewired.mynotepadbinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.lauriewired.mynotepadbinder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    Messenger mService = null;
    boolean bound;
    private static final int MSG_TEXT = 1;

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
                sendMessageToService(noteInput.getText().toString());
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            bound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
            bound = false;
        }
    };

    private void sendMessageToService(String text) {
        if (!bound) return;
        // Obtain a new Message instance
        Message msg = Message.obtain(null, MSG_TEXT);

        // Create a Bundle to hold data
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        msg.setData(bundle);

        try {
            // Send the Message to the service
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.lauriewired.myemailbinder", "com.lauriewired.myemailbinder.CommunicationService"));
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(mConnection);
            bound = false;
        }
    }
}
