package p2.pt.ipleiria.estg.dei.securesms;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtPhoneNo;
    private EditText txtMessage;
    private Button btnSendSMS;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Encryption enc;
    private ContactManager contactManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }



    }

    private void init() {
        txtMessage = findViewById(R.id.txtMessage);
        txtPhoneNo = findViewById(R.id.txtPhoneNo);
        btnSendSMS = findViewById(R.id.btnSendSMS);
        enc = new Encryption();
        contactManager = new ContactManager();
        int test = contactManager.getContacts().size();
        Toast.makeText(getBaseContext(),
                String.valueOf(test),
                Toast.LENGTH_SHORT).show();

    }

    public void onClickSend(View view) {
        String phoneNo = txtPhoneNo.getText().toString();
        String message = txtMessage.getText().toString();
        if (phoneNo.length() > 0 && message.length() > 0) {
            sendSMS(phoneNo, message);
        } else {
            Toast.makeText(getBaseContext(),
                    "Please enter both phone number and message.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void sendSMS(String phoneNumber, String message)
    {
        try {
            message = enc.encrypt(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }
}
