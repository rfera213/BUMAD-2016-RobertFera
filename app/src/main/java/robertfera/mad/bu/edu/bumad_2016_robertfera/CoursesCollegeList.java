package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CoursesCollegeList extends ListActivity implements DataPasser {

    DataRetriever dataRetriever;

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

        dataRetriever = new DataRetriever(this, url);
        dataRetriever.fetch();

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CoursesCollege item = ((ArrayList<CoursesCollege>)dataRetriever.getData()).get(position);
                String college_name = item.getName();
                String college_code = item.getCode();

                Intent intent = new Intent(getApplicationContext(), CoursesCollegeSubjectsAndDepartmentsList.class);
                intent.putExtra("college_name", college_name);
                intent.putExtra("college_code", college_code);
                startActivity(intent);
            }
        });

    }

    public ArrayList<?> ParseJSON(String json) {
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

    public void postFetch(ArrayList<?> data) {
        ListAdapter adapter = new CoursesCollegeListAdapter(CoursesCollegeList.this, R.layout.courses_college, (ArrayList<CoursesCollege>) data);
        setListAdapter(adapter);
    }

    public class CoursesCollegeListAdapter extends ArrayAdapter<CoursesCollege> {

        private Context context;
        private ArrayList<CoursesCollege> objects;

        public CoursesCollegeListAdapter(Context context, int id, ArrayList<CoursesCollege> objects) {
            super(context, 0, objects);
            this.objects = objects;
            this.context = context;

        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.courses_college, null,true);

            TextView college = (TextView) rowView.findViewById(R.id.college);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

            CoursesCollege coursesCollege = objects.get(position);

            college.setText(coursesCollege.getName());

            String uri = "@drawable/college_" + coursesCollege.getCode().toLowerCase();
            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            if (imageResource != 0) {
                Drawable res = context.getResources().getDrawable(imageResource);
                imageView.setImageDrawable(res);
            }

            return rowView;
        };
    }
}
