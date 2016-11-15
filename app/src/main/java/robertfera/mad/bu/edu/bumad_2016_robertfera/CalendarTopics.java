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

public class CalendarTopics extends ListActivity {

    ArrayList<CalendarTopic> data;

    // URL to get JSON
    private static String url = "http://www.bu.edu/bumobile/rpc/calendar/topics.json.php";

    // JSON Node names
    private static final String TAG_RESULTSET = "ResultSet";
    private static final String TAG_RESULT = "Result";
    private static final String TAG_TOPICNAME = "name";
    private static final String TAG_TOPICID = "node_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_topics);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CalendarTopic item = data.get(position);
                String node_id = item.getId();

                Intent intent = new Intent(getApplicationContext(), CalendarEvents.class);
                intent.putExtra("node_id", node_id);
                startActivity(intent);
            }
        });

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

            /**
             * Updating parsed JSON data into ListView
             * */

            ArrayList<String> listData = new ArrayList<String>();
            for (int i = 0; i < data.size(); i++) {
                listData.add(data.get(i).getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CalendarTopics.this, android.R.layout.simple_list_item_1, listData);
            setListAdapter(adapter);
        }

    }

    private ArrayList<CalendarTopic> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<CalendarTopic> data = new ArrayList<CalendarTopic>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String name = c.getString(TAG_TOPICNAME);
                    String id = c.getString(TAG_TOPICID);

                    CalendarTopic calendarTopic = new CalendarTopic(name, id);
                    data.add(calendarTopic);
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
