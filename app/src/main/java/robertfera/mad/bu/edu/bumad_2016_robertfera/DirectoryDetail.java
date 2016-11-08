package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DirectoryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_detail);

        TextView fullname = (TextView) findViewById(R.id.fullname);
        TextView title = (TextView) findViewById(R.id.title);
        TextView department = (TextView) findViewById(R.id.department);
        Button phone = (Button) findViewById(R.id.phone);
        Button email = (Button) findViewById(R.id.email);
        Button address = (Button) findViewById(R.id.address);

        Bundle extras = getIntent().getExtras();
        String directoryFullname = extras.getString("fullname");
        String directoryTitle = extras.getString("title");
        String directoryDepartment = extras.getString("department");
        String directoryPhone = extras.getString("phone");
        String directoryEmail = extras.getString("email").toLowerCase();
        String directoryAddress = extras.getString("address").toLowerCase();

        fullname.setText(directoryFullname);
        title.setText(directoryTitle);
        department.setText(directoryDepartment);
        phone.setText(directoryPhone);
        email.setText(directoryEmail);
        address.setText(directoryAddress);
    }

    public void phone(View view) {
        Button phone = (Button) findViewById(view.getId());
        String phoneNumber = phone.getText().toString();

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel: " +phoneNumber));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) { startActivity(intent); }
    }

    public void email(View view) {
        Button email = (Button) findViewById(view.getId());
        String emailAddress = email.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        startActivity(Intent.createChooser(intent, "Choose an email client: "));
    }

    public void address(View view) {
        Button address = (Button) findViewById(view.getId());
        String addr = address.getText().toString();

        // open maps module with search

    }
}
