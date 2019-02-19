package p2.pt.ipleiria.estg.dei.securesms;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
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
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    SmsReceiver smsReceiver;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            contactManager.getContactList(this.getContentResolver());

            for(Contact c:contactManager.getContacts()){
                Log.d("Contact",c.getPhoneNumber());
            }
            /*Toast.makeText(getBaseContext(),
                    String.valueOf(contactManager.getContacts().size()),
                    Toast.LENGTH_SHORT).show();*/
        }



    }

    private void init() {
        txtMessage = findViewById(R.id.txtMessage);
        txtPhoneNo = findViewById(R.id.txtPhoneNo);
        btnSendSMS = findViewById(R.id.btnSendSMS);
        enc = new Encryption();
        contactManager = new ContactManager();
        smsReceiver = new SmsReceiver();

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
