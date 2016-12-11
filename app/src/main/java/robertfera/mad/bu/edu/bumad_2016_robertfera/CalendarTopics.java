package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.ListActivity;
import android.content.Intent;
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

public class CalendarTopics extends ListActivity implements DataPasser {

    DataRetriever dataRetriever;

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

        dataRetriever = new DataRetriever(this, url);
        dataRetriever.fetch();

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CalendarTopic item = ((ArrayList<CalendarTopic>)dataRetriever.getData()).get(position);
                String node_id = item.getId();

                Intent intent = new Intent(getApplicationContext(), CalendarEvents.class);
                intent.putExtra("node_id", node_id);
                startActivity(intent);
            }
        });
    }

    public ArrayList<?> ParseJSON(String json) {
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

    public void postFetch(ArrayList<?> data) {
        ArrayList<String> listData = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            listData.add(((ArrayList<CalendarTopic>)data).get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CalendarTopics.this, android.R.layout.simple_list_item_1, listData);
        setListAdapter(adapter);
    }
}
