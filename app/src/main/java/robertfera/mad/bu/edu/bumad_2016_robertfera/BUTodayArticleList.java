package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BUTodayArticleList extends ListActivity {

    // Data retrieved
    ArrayList<HashMap<String, String>> data;

    // URL to get contacts JSON
    private static String url = "http://www.bu.edu/bumobile/rpc/today/articles.json.php";

    // JSON Node names
    private static final String TAG_RESULTSET = "ResultSet";
    private static final String TAG_RESULT = "Result";
    private static final String TAG_TYPE = "type";
    private static final String TAG_HEADLINE = "headline";
    private static final String TAG_SUBHEAD = "subhead";
    private static final String TAG_LINK = "link";
    private static final String TAG_THUMBNAIL = "thumbnail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butoday_article_list);

        ListView listView = getListView();
        listView.setOnItemClickListener(new OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                                HashMap<String, String> item = data.get(position);
                                                String url = item.get(TAG_LINK);
                                                url = url + "uiwebview/";

                                                Intent intent = new Intent(getApplicationContext(), BUTodayArticleDetail.class);
                                                intent.putExtra("url", url);
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

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BUTodayArticleList.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebRequest webreq = new WebRequest();

            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(url, WebRequest.GET);

            Log.d("Response: ", "> " + jsonStr);

            data = ParseJSON(jsonStr);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(BUTodayArticleList.this, data, R.layout.list_item, new String[]{TAG_HEADLINE, TAG_SUBHEAD}, new int[]{R.id.headline, R.id.subhead});
            setListAdapter(adapter);
        }

    }

    private ArrayList<HashMap<String, String>> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through All Students
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String type = c.getString(TAG_TYPE);
                    String headline = c.getString(TAG_HEADLINE);
                    String subhead = c.getString(TAG_SUBHEAD);
                    String link = c.getString(TAG_LINK);

                    // tmp hashmap for single student
                    HashMap<String, String> article = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    article.put(TAG_TYPE, type);
                    article.put(TAG_HEADLINE, headline);
                    article.put(TAG_SUBHEAD, subhead);
                    article.put(TAG_LINK, link);

                    // adding student to students list
                    data.add(article);
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
