package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.telephony.SmsManager;

public class Emergency extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide toolbar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_emergency);
    }

    public void callBUDP(View view) {
        this.callNumber("6173532121");
    }

    public void textBUDP(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("847411", null, "sms message", null, null);
        }
    }

    public void callHealth(View view) {
        this.callNumber("6173533575");
    }

    public void callSARPC(View view) {
        this.callNumber("6173537277");
    }

    public void callSafewalk(View view) {
        this.callNumber("6173534877");
    }

    public void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel: " +number));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) { startActivity(intent); }
    }

}
