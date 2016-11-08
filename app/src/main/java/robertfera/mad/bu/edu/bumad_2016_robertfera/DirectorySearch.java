package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DirectorySearch extends ListActivity {

    // Data retrieved
    ArrayList<DirectoryEntry> data;

    // URL to get JSON
    private static String url = "http://www.bu.edu/bumobile/rpc/directory/search.json.php";

    // JSON Node names
    private static final String TAG_RESULTSET = "ResultSet";
    private static final String TAG_RESULT = "Result";
    private static final String TAG_DIRECTORYFNAME = "fname";
    private static final String TAG_DIRECTORYLNAME = "lname";
    private static final String TAG_DIRECTORYTITLE = "title";
    private static final String TAG_DIRECTORYDEPARTMENT = "department";
    private static final String TAG_DIRECTORYPHONE = "phone";
    private static final String TAG_DIRECTORYEMAIL = "email";
    private static final String TAG_DIRECTORYADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_search);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                DirectoryEntry directoryEntry = data.get(position);
                String fullname = directoryEntry.getFullname();
                String title = directoryEntry.getTitle();
                String department = directoryEntry.getDepartment();
                String phone = directoryEntry.getPhone();
                String address = directoryEntry.getAddress();
                String email = directoryEntry.getEmail();

                Intent intent = new Intent(getApplicationContext(), DirectoryDetail.class);
                intent.putExtra("fullname", fullname);
                intent.putExtra("title", title);
                intent.putExtra("department", department);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });

        final SearchView sv = (SearchView) findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                url+= "?q=" + s;
                new GetData().execute();
                sv.clearFocus(); // prevents the double fetch
                url = "http://www.bu.edu/bumobile/rpc/directory/search.json.php"; // resets URL
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        int searchCloseButtonId = sv.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView)sv.findViewById(searchCloseButtonId);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setListAdapter(null);
                Log.d("CLOSE", "CLOSE");
            }
        });
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
                listData.add(data.get(i).getFullname());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DirectorySearch.this, android.R.layout.simple_list_item_1, listData);
            setListAdapter(adapter);
        }

    }

    private ArrayList<DirectoryEntry> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<DirectoryEntry> data = new ArrayList<DirectoryEntry>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String fname = c.getString(TAG_DIRECTORYFNAME);
                    String lname= c.getString(TAG_DIRECTORYLNAME);
                    String title = c.getString(TAG_DIRECTORYTITLE);
                    String phone = c.getString(TAG_DIRECTORYPHONE);
                    String address = c.getString(TAG_DIRECTORYADDRESS);
                    String email = c.getString(TAG_DIRECTORYEMAIL);
                    String department;
                    if (c.has(TAG_DIRECTORYDEPARTMENT)) {
                        department = c.getString(TAG_DIRECTORYDEPARTMENT);
                    } else {
                        department = "";
                    }

                    DirectoryEntry directoryEntry = new DirectoryEntry(fname, lname, title, department, phone, address, email);
                    data.add(directoryEntry);
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
