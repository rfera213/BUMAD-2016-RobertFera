package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void loadBUTodayArticleList(View view) {
        Intent intent = new Intent(this, BUTodayArticleList.class);
        startActivity(intent);
    }

    public void loadCoursesCollegeList(View view) {
        Intent intent = new Intent(this, CoursesCollegeList.class);
        startActivity(intent);
    }

    public void loadBusMap(View view) {
        Intent intent = new Intent(this, BusMap.class);
        startActivity(intent);
    }

    public void loadDirectorySearch(View view) {
        Intent intent = new Intent(this, DirectorySearch.class);
        startActivity(intent);
    }
}
