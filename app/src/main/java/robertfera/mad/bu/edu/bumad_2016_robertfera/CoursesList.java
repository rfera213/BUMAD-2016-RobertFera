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

public class CoursesList extends ListActivity {

    // Data retrieved
    ArrayList<Course> data;

    // URL to get contacts JSON
    private String url = "http://www.bu.edu/bumobile/rpc/courses/courses.json.php";

    // JSON Node names
    private static final String TAG_RESULTSET = "ResultSet";
    private static final String TAG_RESULT = "Result";
    private static final String TAG_COURSETITLE = "title";
    private static final String TAG_COURSECOLLEGE = "college";
    private static final String TAG_COURSEDEPARTMENT = "department";
    private static final String TAG_COURSENUM = "course_num";
    private static final String TAG_COURSEDESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Course course = data.get(position);
                String course_title = course.getTitle();
                String course_together = course.getTogether();
                String course_description = course.getDescription();

                Intent intent = new Intent(getApplicationContext(), CourseDetail.class);
                intent.putExtra("course_title", course_title);
                intent.putExtra("course_together", course_together);
                intent.putExtra("course_description", course_description);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        String college_code = extras.getString("college_code");
        url+= "?college=" + college_code;
        String subject_prefix = extras.getString("subject_prefix");
        url+= "&departments=" +subject_prefix;
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
                listData.add(data.get(i).getTitle());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CoursesList.this, android.R.layout.simple_list_item_1, listData);
            setListAdapter(adapter);
        }

    }

    private ArrayList<Course> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<Course> data = new ArrayList<Course>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String title = c.getString(TAG_COURSETITLE);
                    String college = c.getString(TAG_COURSECOLLEGE);
                    String department = c.getString(TAG_COURSEDEPARTMENT);
                    String course_num = c.getString(TAG_COURSENUM);
                    String description = c.getString(TAG_COURSEDESCRIPTION);

                    Course course = new Course(title, college, department, course_num, description);
                    data.add(course);
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
