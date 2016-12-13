package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class BUTodayArticleList extends ListActivity implements DataPasser {

    DataRetriever dataRetriever;

    private String url = "http://www.bu.edu/bumobile/rpc/today/articles.json.php";

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

        dataRetriever = new DataRetriever(this, url);
        dataRetriever.fetch();

        ListView listView = getListView();
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Article item = ((ArrayList<Article>) dataRetriever.getData()).get(position);
                String url = item.getLink();
                url = url + "uiwebview/";

                Intent intent = new Intent(getApplicationContext(), BUTodayArticleDetail.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

    public ArrayList<?> ParseJSON(String json) {
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

                    String type = (c.has(TAG_TYPE)) ? c.getString(TAG_TYPE) : "";
                    String headline = (c.has(TAG_HEADLINE)) ? c.getString(TAG_HEADLINE) : "";
                    String subhead = (c.has(TAG_SUBHEAD)) ? c.getString(TAG_SUBHEAD) : "";
                    String link = (c.has(TAG_LINK)) ? c.getString(TAG_LINK) : "";
                    String thumbnail = (c.has(TAG_THUMBNAIL)) ? c.getString(TAG_THUMBNAIL) : "";

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

    public void postFetch(ArrayList<?> data) {
        ListAdapter adapter = new BUTodayArticleListAdapter(BUTodayArticleList.this, R.layout.butoday_article, (ArrayList<Article>) data);
        setListAdapter(adapter);
    }

    public class BUTodayArticleListAdapter extends ArrayAdapter<Article> implements ResultFilter {

        private ArrayList<Article> objects;
        private Context context;

        public BUTodayArticleListAdapter(Context context, int id, ArrayList<Article> objects) {
            super(context, 0, objects);
            this.objects = objects;
            this.context = context;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.butoday_article, parent, false);

            TextView headline = (TextView) rowView.findViewById(R.id.headline);
            TextView subhead = (TextView) rowView.findViewById(R.id.subhead);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.thumbnail);

            Article article = objects.get(position);
            headline.setText(article.getHead());
            subhead.setText(article.getDeck());
            new DownloadImageTask(imageView).execute(article.getThumbnail());

            // user set major - need to check each article for keywords
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context);
            String major = sharedPref.getString("major", "");
            String strToCheck = headline.getText() + " " + subhead.getText();
            if (major.length() > 0) {
                Boolean isSpecial = this.isPreferred(major, strToCheck);
                if (isSpecial) {
                    rowView.setBackgroundColor(Color.LTGRAY);
                }
            }

            return rowView;
        };

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }

        public Boolean isPreferred(String major, String strToCheck) {

            String[] keywords;
            if (major.equals("Computer Science")) {
                keywords = this.context.getResources().getStringArray(R.array.computer_science);
            } else if (major.equals("Neuroscience")) {
                keywords = this.context.getResources().getStringArray(R.array.neuroscience);
            } else {
                keywords = new String[0];
            }

            for (String keyword: keywords) {
                if (strToCheck.toLowerCase().contains(keyword)) return true;
            }
            return false;
        }
    }
}
