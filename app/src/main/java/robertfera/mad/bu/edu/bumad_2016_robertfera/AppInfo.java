package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;
import android.net.Uri;

import java.util.Arrays;
import java.util.List;

public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide toolbar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_app_info);

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        TextView version = (TextView)findViewById(R.id.version);
        version.setText("Version " + versionName + "." + versionCode);

        ListView buttons = (ListView)findViewById(R.id.buttons);
        List<String> data = Arrays.asList("Submit Feedback", "Tell a Friend", "More Information", "Set Major");
        ListAdapter adapter = new AppInfoListAdapter(AppInfo.this, R.layout.app_info, data);
        buttons.setAdapter(adapter);

        final CharSequence majors[] = new CharSequence[] {"Computer Science", "Neuroscience", "Reset"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a major");
        builder.setItems(majors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setMajor(majors[which].toString());
            }
        });

        buttons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;

                switch (position) {
                    // feedback
                    case 0:
                        i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ithelp@bu.edu"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "BU Mobile Feedback");
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(AppInfo.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    // tell friend
                    case 1:
                        i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ithelp@bu.edu"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "BU Mobile Android App");
                        i.putExtra(Intent.EXTRA_TEXT, "Hi,\n" +
                                "\n" +
                                "I thought you might be interested in the BU Mobile Android app.\n" +
                                "\n" +
                                "You can download BU Mobile from:\n" +
                                "\n" +
                                "URL HERE\n");
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(AppInfo.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    // more info
                    case 2:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bu.edu/tech/comm/mobile/bu-mobile/"));
                        startActivity(browserIntent);
                        break;

                    // major
                    case 3:
                        builder.show();
                        break;
                }
            }
        });
    }

    public void setMajor(String major) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (major.equals("Reset")) {
            editor.remove("major");
        } else {
            editor.putString("major", major);
        }
        editor.commit();
    }

    public class AppInfoListAdapter extends ArrayAdapter<String> {

        private Context context;
        private List<String> objects;

        public AppInfoListAdapter(Context context, int id, List<String> objects) {
            super(context, 0, objects);
            this.objects = objects;
            this.context = context;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.app_info, parent, false);

            TextView txt = (TextView) rowView.findViewById(R.id.text);
            txt.setText(objects.get(position));

            return rowView;
        };
    }
}
