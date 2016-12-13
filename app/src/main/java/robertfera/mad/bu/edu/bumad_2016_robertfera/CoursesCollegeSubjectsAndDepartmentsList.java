package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CoursesCollegeSubjectsAndDepartmentsList extends ListActivity implements DataPasser{

    DataRetriever dataRetriever;

    private String url = "http://www.bu.edu/bumobile/rpc/courses/subjects.json.php";

    // JSON Node names
    private static final String TAG_RESULTSET = "ResultSet";
    private static final String TAG_RESULT = "Result";
    private static final String TAG_SUBJECTNAME = "subject_name";
    private static final String TAG_SUBJECTPREFIXES = "prefixes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_college_subjects_and_departments_list);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CoursesCollegeSubject item = ((ArrayList<CoursesCollegeSubject>)dataRetriever.getData()).get(position);
                String subject_name = item.getSubject_name();
                String subject_prefix = item.getPrefixes();

                Bundle extras = getIntent().getExtras();
                String code = extras.getString("college_code");

                Intent intent = new Intent(getApplicationContext(), CoursesList.class);
                intent.putExtra("subject_name", subject_name);
                intent.putExtra("subject_prefix", subject_prefix);
                intent.putExtra("college_code", code);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        String college_code = extras.getString("college_code");
        url+= "?q=" + college_code;

        dataRetriever = new DataRetriever(this, url);
        dataRetriever.fetch();
    }

    public ArrayList<?> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<CoursesCollegeSubject> data = new ArrayList<CoursesCollegeSubject>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String name = c.getString(TAG_SUBJECTNAME);
                    String prefixes = c.getString(TAG_SUBJECTPREFIXES);

                    CoursesCollegeSubject coursesCollegeSubject = new CoursesCollegeSubject(name, prefixes);
                    data.add(coursesCollegeSubject);
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
            listData.add(((ArrayList<CoursesCollegeSubject>)data).get(i).getSubject_name());
        }

        ArrayAdapter<String> adapter = new SimpleListItemAdapter(CoursesCollegeSubjectsAndDepartmentsList.this, listData, false);
        setListAdapter(adapter);
    }
}
