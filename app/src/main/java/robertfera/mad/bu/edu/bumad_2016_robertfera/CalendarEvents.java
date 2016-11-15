package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalendarEvents extends ListActivity {

    ArrayList<CalendarEvent> data;

    // URL to get JSON
    private static String url = "http://www.bu.edu/bumobile/rpc/calendar/events.json.php";

    // JSON Node names
    private static final String TAG_RESULTSET = "ResultSet";
    private static final String TAG_RESULT = "Result";
    private static final String TAG_EVENTID = "id";
    private static final String TAG_EVENTSUMMARY = "summary";
    private static final String TAG_EVENTTIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_events);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CalendarEvent item = data.get(position);
                String node_id = item.getId();

                Intent intent = new Intent(getApplicationContext(), CalendarEventDetail.class);
                intent.putExtra("id", node_id);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        String node_id = extras.getString("node_id");
        url+= "?tid=" + node_id;

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

            // resets URL for next potential fetch
            url = "http://www.bu.edu/bumobile/rpc/calendar/events.json.php";

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            /**
             * Updating parsed JSON data into ListView
             * */

            ArrayList<String> listData = new ArrayList<String>();
            if (data != null) {
                for (int i = 0; i < data.size(); i++) {
                    listData.add(data.get(i).getSummary());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CalendarEvents.this, android.R.layout.simple_list_item_1, listData);
            setListAdapter(adapter);
        }

    }

    private ArrayList<CalendarEvent> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<CalendarEvent> data = new ArrayList<CalendarEvent>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String summary = c.getString(TAG_EVENTSUMMARY);
                    String id = c.getString(TAG_EVENTID);
                    String time = c.getString(TAG_EVENTTIME);

                    CalendarEvent calendarEvent = new CalendarEvent(id, summary, time);
                    data.add(calendarEvent);
                }
                return data;
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
