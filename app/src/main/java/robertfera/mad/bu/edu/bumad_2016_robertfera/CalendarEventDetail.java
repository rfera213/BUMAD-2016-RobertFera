package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.widget.TextView;
import android.widget.Button;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CalendarEventDetail extends AppCompatActivity {

    CalendarEvent data;

    // URL to get JSON
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
        setContentView(R.layout.activity_calendar_event_detail);

        Bundle extras = getIntent().getExtras();
        String node_id = extras.getString("id");
        url+= "?eid=" + node_id;

        new GetData().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebRequest webreq = new WebRequest();

            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(url, WebRequest.GET);
            data = ParseJSON(jsonStr);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            TextView title = (TextView) findViewById(R.id.title);
            TextView displayTime = (TextView) findViewById(R.id.dateTime);
            TextView description = (TextView) findViewById(R.id.description);
            TextView openTo = (TextView) findViewById(R.id.openTo);
            Button phone = (Button) findViewById(R.id.phone);
            Button email = (Button) findViewById(R.id.email);
            Button location = (Button) findViewById(R.id.location);
            Button register = (Button) findViewById(R.id.register);
            Button url = (Button) findViewById(R.id.url);

            if (data != null) {
                title.setText(data.getSummary());
                displayTime.setText(data.getDisplayDate() + " at " + data.getDisplayTime());
                description.setText(data.getDescription());
                openTo.setText(data.getOpenTo());
                phone.setText(data.getPhone());
                email.setText(data.getEmail());
                location.setText(data.getLocation());
                register.setText(data.getRegistration_url());
                url.setText(data.getUrl());
            }
        }

    }

    private CalendarEvent ParseJSON(String json) {
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

                    return new CalendarEvent(id, summary, displayTime, displayDate, description, openTo, phone, email, location, registration_url, url);
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
}
