package robertfera.mad.bu.edu.bumad_2016_robertfera;

import java.util.ArrayList;
import java.io.InputStream;

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

public class BUTodayArticleListAdapter extends ArrayAdapter<Article> {

    private ArrayList<Article> objects;

    public BUTodayArticleListAdapter(Context context, int id, ArrayList<Article> objects) {
        super(context, 0, objects);
        this.objects = objects;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, null,true);

        TextView headline = (TextView) rowView.findViewById(R.id.headline);
        TextView subhead = (TextView) rowView.findViewById(R.id.subhead);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.thumbnail);

        Article article = objects.get(position);


        headline.setText(article.getHead());
        subhead.setText(article.getDeck());
        new DownloadImageTask(imageView).execute(article.getThumbnail());
        Log.d("Image: ", article.getThumbnail());

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
}
