package robertfera.mad.bu.edu.bumad_2016_robertfera;

/**
 * Created by rfera213 on 12/8/16.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import java.util.ArrayList;

public class DataRetriever extends AsyncTask<Void, Void, Void> {
    private Activity delegate;
    private int tableID;
    private String url;
    private ArrayList<?> data;
    private String jsonStr;

    public DataRetriever(Activity delegate, String url) {
        this.delegate = delegate;
        this.url = url;
    }

    public void fetch() {
        this.execute();
    }

    public ArrayList<?> getData() { return this.data; }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        // Creating service handler class instance
        WebRequest webreq = new WebRequest();

        // Making a request to url and getting response
        Log.d("Hi", url);
        jsonStr = webreq.makeWebServiceCall(url, WebRequest.GET);

        // calling parseJson of delegate to sort data
        if (delegate instanceof DataPasser) {
            data = ((DataPasser) delegate).ParseJSON(jsonStr);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (delegate instanceof DataPasser) {
            ((DataPasser) delegate).postFetch(data);
        }
    }
}
