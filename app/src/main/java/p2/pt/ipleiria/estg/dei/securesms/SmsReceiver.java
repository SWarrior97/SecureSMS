package p2.pt.ipleiria.estg.dei.securesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;

public class SmsReceiver extends BroadcastReceiver
{
    public static String MSG = "MESSAGE";
    public static String PHONENUMBER = "MESSAGENUMBER";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();

        String str = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage message = SmsMessage.createFromPdu((byte[])pdus[0]);
            String phoneNumber = message.getOriginatingAddress();
            String msg = message.getMessageBody();
            Intent i = new Intent(context,ReceiveActivity.class);
            i.putExtra(MSG,msg);
            i.putExtra(PHONENUMBER,phoneNumber);
            context.startActivity(i);
        }
    }

}
