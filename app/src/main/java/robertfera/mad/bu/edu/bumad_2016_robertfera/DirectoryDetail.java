package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class DirectoryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide toolbar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_directory_detail);

        Bundle extras = getIntent().getExtras();
        String directoryFullname = extras.getString("fullname");
        String directoryTitle = extras.getString("title");
        String directoryDepartment = extras.getString("department");
        String directoryPhone = extras.getString("phone");
        String directoryEmail = extras.getString("email").toLowerCase();
        String directoryAddress = extras.getString("address").toLowerCase();

        TextView fullname = (TextView) findViewById(R.id.fullname);
        TextView title = (TextView) findViewById(R.id.title);
        TextView department = (TextView) findViewById(R.id.department);

        fullname.setText(directoryFullname);
        title.setText(directoryTitle);
        department.setText(directoryDepartment);

        ArrayList<StringPair> cells = new ArrayList<>();
        if (directoryPhone != null && directoryPhone.length() > 0) cells.add(new StringPair("Phone", directoryPhone));
        if (directoryEmail != null && directoryEmail.length() > 0) cells.add(new StringPair("Email", directoryEmail));
        if (directoryAddress != null && directoryAddress.length() > 0) cells.add(new StringPair("Address", directoryAddress));

        ListView buttons = (ListView)findViewById(R.id.buttons);
        ListAdapter adapter = new DirectoryEntryListAdapter(DirectoryDetail.this, R.layout.detail_cell, cells);
        buttons.setAdapter(adapter);

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

    public class DirectoryEntryListAdapter extends ArrayAdapter<StringPair> {

        private Context context;
        private ArrayList<StringPair> objects;

        public DirectoryEntryListAdapter(Context context, int id, ArrayList<StringPair> objects) {
            super(context, 0, objects);
            this.objects = objects;
            this.context = context;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.detail_cell, parent, false);

            TextView label = (TextView) rowView.findViewById(R.id.label);
            TextView info = (TextView) rowView.findViewById(R.id.info);

            label.setText(objects.get(position).getKey());
            info.setText(objects.get(position).getValue());

            return rowView;
        };
    }
}
