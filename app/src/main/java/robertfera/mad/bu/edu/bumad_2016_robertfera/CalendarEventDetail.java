package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalendarEventDetail extends AppCompatActivity implements DataPasser {

    DataRetriever dataRetriever;

    private static String url = "http://www.bu.edu/bumobile/rpc/calendar/events.json.php";

    // JSON Node names
    private static final String TAG_RESULTSET = "ResultSet";
    private static final String TAG_RESULT = "Result";
    private static final String TAG_EVENTSUMMARY = "summary";
    private static final String TAG_EVENTID = "id";
    private static final String TAG_EVENTOPENTO = "openTo";
    private static final String TAG_EVENTDESCRIPTION = "description";
    private static final String TAG_EVENTDISPLAYDATE = "displayDate";
    private static final String TAG_EVENTDISPLAYTIME = "displayTime";
    private static final String TAG_EVENTREGISTRATIONURL = "registration_url";
    private static final String TAG_EVENTURL = "url";
    private static final String TAG_EVENTLOCATION = "location";
    private static final String TAG_EVENTCONTACTEMAIL = "contactEmail";
    private static final String TAG_EVENTPHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide toolbar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_calendar_event_detail);

        Bundle extras = getIntent().getExtras();
        String node_id = extras.getString("id");
        url+= "?eid=" + node_id;

        dataRetriever = new DataRetriever(this, url);
        dataRetriever.fetch();
    }

    public ArrayList<?> ParseJSON(String json) {
        if (json != null) {
            try {
                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String summary = "", id = "", displayTime = "", displayDate = "", location = "", email = "", openTo = "", description = "", registration_url = "", url = "", phone = "";

                    if (c.has(TAG_EVENTSUMMARY)) summary = c.getString(TAG_EVENTSUMMARY);
                    if (c.has(TAG_EVENTID)) id = c.getString(TAG_EVENTID);
                    if (c.has(TAG_EVENTDISPLAYTIME)) displayTime = c.getString(TAG_EVENTDISPLAYTIME);
                    if (c.has(TAG_EVENTDISPLAYDATE)) displayDate = c.getString(TAG_EVENTDISPLAYDATE);
                    if (c.has(TAG_EVENTLOCATION)) location = c.getString(TAG_EVENTLOCATION);
                    if (c.has(TAG_EVENTCONTACTEMAIL)) email = c.getString(TAG_EVENTCONTACTEMAIL);
                    if (c.has(TAG_EVENTOPENTO)) openTo = c.getString(TAG_EVENTOPENTO);
                    if (c.has(TAG_EVENTDESCRIPTION)) description = c.getString(TAG_EVENTDESCRIPTION);
                    if (c.has(TAG_EVENTREGISTRATIONURL)) registration_url = c.getString(TAG_EVENTREGISTRATIONURL);
                    if (c.has(TAG_EVENTURL)) url = c.getString(TAG_EVENTURL);
                    if (c.has(TAG_EVENTPHONE)) phone = c.getString(TAG_EVENTPHONE);

                    ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();
                    events.add(new CalendarEvent(id, summary, displayTime, displayDate, description, openTo, phone, email, location, registration_url, url));
                    return events;
                }
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }

    public void postFetch(ArrayList<?> data) {
        TextView title = (TextView) findViewById(R.id.title);
        TextView displayTime = (TextView) findViewById(R.id.dateTime);
        TextView description = (TextView) findViewById(R.id.description);
        TextView openTo = (TextView) findViewById(R.id.openTo);

        if (data != null) {
            CalendarEvent event = ((ArrayList<CalendarEvent>)data).get(0);
            title.setText(event.getSummary());
            displayTime.setText(event.getDisplayDate() + " at " + event.getDisplayTime());
            description.setText(event.getDescription());
            openTo.setText(event.getOpenTo());

            String phone = event.getPhone();
            String email = event.getEmail();
            String location = event.getLocation();
            String register = event.getRegistration_url();
            String info = event.getUrl();

            ArrayList<StringPair> cells = new ArrayList<>();
            if (phone != null && phone.length() > 0) cells.add(new StringPair("Phone", phone));
            if (email != null && email.length() > 0) cells.add(new StringPair("Email", email));
            if (location != null && location.length() > 0) cells.add(new StringPair("Location", location));
            if (register != null && register.length() > 0) cells.add(new StringPair("Register", register));
            if (info != null && info.length() > 0) cells.add(new StringPair("Info", info));

            ListView buttons = (ListView)findViewById(R.id.buttons);
            ListAdapter adapter = new CalendarEventListAdapter(CalendarEventDetail.this, R.layout.detail_cell, cells);
            buttons.setAdapter(adapter);

            // reset url for next fetch
            url = "http://www.bu.edu/bumobile/rpc/calendar/events.json.php";
        }
    }

    public class CalendarEventListAdapter extends ArrayAdapter<StringPair> {

        private Context context;
        private ArrayList<StringPair> objects;

        public CalendarEventListAdapter(Context context, int id, ArrayList<StringPair> objects) {
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
