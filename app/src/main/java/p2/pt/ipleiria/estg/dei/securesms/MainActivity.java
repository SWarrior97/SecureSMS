package p2.pt.ipleiria.estg.dei.securesms;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText txtPhoneNo;
    private EditText txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        txtMessage = findViewById(R.id.txtMessage);
        txtPhoneNo = findViewById(R.id.txtPhoneNo);
    }

    public void onClickSend(View view) {
        String phoneNo = txtPhoneNo.getText().toString();
        String message = txtMessage.getText().toString();

        if (phoneNo.length()>0 && message.length()>0){
            sendSMS(phoneNo, message);
        }else{

        }

    }

    private void sendSMS(String phoneNumber, String message)
    {
        String smsNumber = String.format("smsto: %s",
                txtPhoneNo.getText().toString());

        String sms = txtMessage.getText().toString();

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);

        smsIntent.setData(Uri.parse(smsNumber));
        smsIntent.putExtra("sms_body", sms);

        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        } else {
           //Log.e(TAG, "Can't resolve app for ACTION_SENDTO Intent");
        }
    }
}
