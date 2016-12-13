package robertfera.mad.bu.edu.bumad_2016_robertfera;

import java.util.ArrayList;
import java.io.InputStream;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Created by rfera213 on 10/25/16.
 */

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
