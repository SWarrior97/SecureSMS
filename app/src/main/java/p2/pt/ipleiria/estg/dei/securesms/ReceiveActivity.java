package p2.pt.ipleiria.estg.dei.securesms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveActivity extends AppCompatActivity {
    private String phoneNumber;
    private String msg;
    private TextView textViewMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        init();

        msg = getIntent().getStringExtra(SmsReceiver.MSG);
        phoneNumber = getIntent().getStringExtra(SmsReceiver.PHONENUMBER);


        if(!msg.isEmpty()){
            if(!phoneNumber.isEmpty()){
                textViewMsg.setText("Message"+msg);
            }
        }
    }

    private void init() {
        textViewMsg = findViewById(R.id.textViewMsg);
    }
}
