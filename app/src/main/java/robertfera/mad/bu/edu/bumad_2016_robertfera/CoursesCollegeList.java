package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CoursesCollegeList extends ListActivity {

    // Data retrieved
    ArrayList<CoursesCollege> data;

    // URL to get JSON
    private static String url = "http://www.bu.edu/bumobile/rpc/courses/colleges.json.php";

    // JSON Node names
    private static final String TAG_RESULTSET = "ResultSet";
    private static final String TAG_RESULT = "Result";
    private static final String TAG_COLLEGENAME = "college_name";
    private static final String TAG_COLLEGECODE = "college_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_college_list);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CoursesCollege item = data.get(position);
                String college_name = item.getName();
                String college_code = item.getCode();

                Intent intent = new Intent(getApplicationContext(), CoursesCollegeSubjectsAndDepartmentsList.class);
                intent.putExtra("college_name", college_name);
                intent.putExtra("college_code", college_code);
                startActivity(intent);
            }
        });

        // Calling async task to get json
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
            ListAdapter adapter = new CoursesCollegeListAdapter(CoursesCollegeList.this, R.layout.courses_college, data);
            setListAdapter(adapter);
        }

    }

    private ArrayList<CoursesCollege> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<CoursesCollege> data = new ArrayList<CoursesCollege>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String name = c.getString(TAG_COLLEGENAME);
                    String code = c.getString(TAG_COLLEGECODE);

                    CoursesCollege coursesCollege = new CoursesCollege(name, code);
                    data.add(coursesCollege);
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
