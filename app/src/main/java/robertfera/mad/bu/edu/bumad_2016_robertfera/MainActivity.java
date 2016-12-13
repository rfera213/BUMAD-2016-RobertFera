package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide toolbar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
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

    public void loadCalendarTopics(View view) {
        Intent intent = new Intent(this, CalendarTopics.class);
        startActivity(intent);
    }

    public void loadMap(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    public void loadEmergency(View view) {
        Intent intent = new Intent(this, Emergency.class);
        startActivity(intent);
    }

    public void showInfoActivity() {
        Intent intent = new Intent(this, AppInfo.class);
        startActivity(intent);
    }
}
