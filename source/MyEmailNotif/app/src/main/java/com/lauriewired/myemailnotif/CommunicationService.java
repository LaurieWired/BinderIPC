package com.lauriewired.myemailnotif;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

public class CommunicationService extends Service {
    static final int MSG_TEXT = 1;
    Messenger mMessenger;

    static class IncomingHandler extends Handler {
        private Context applicationContext;

        IncomingHandler(Context context) {
            applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TEXT:
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        String text = bundle.getString("text");
                        // Start MainActivity with the received text
                        Intent intent = new Intent(applicationContext, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("email_body", text);
                        applicationContext.startActivity(intent);
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        mMessenger = new Messenger(new IncomingHandler(this));

        // Post a delayed task to crash the service
        new Handler().postDelayed(() -> {
            throw new RuntimeException("Deliberately crashing the service for testing");
        }, 4000);

        return mMessenger.getBinder();
    }
}
