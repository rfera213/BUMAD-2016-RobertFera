package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BUTodayArticleList extends ListActivity {

    // Data retrieved
    ArrayList<Article> data;

    // URL to get article JSON
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


                                                Article item = data.get(position);
                                                String url = item.getLink();
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
            ListAdapter adapter = new BUTodayArticleListAdapter(BUTodayArticleList.this, R.layout.butoday_article, data);
            setListAdapter(adapter);
        }

    }

    private ArrayList<Article> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<Article> betterData = new ArrayList<Article>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through data
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String type = c.getString(TAG_TYPE);
                    String headline = c.getString(TAG_HEADLINE);
                    String subhead = c.getString(TAG_SUBHEAD);
                    String link = c.getString(TAG_LINK);
                    String thumbnail = c.getString(TAG_THUMBNAIL);

                    // adding student to students list
                    Article betterArticle = new Article(type, headline, subhead, thumbnail, link);
                    betterData.add(betterArticle);
                }
                return betterData;
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
