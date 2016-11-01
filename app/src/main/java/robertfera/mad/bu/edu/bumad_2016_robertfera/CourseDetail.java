package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CourseDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        TextView courseTitle = (TextView) findViewById(R.id.name);
        TextView courseTogether = (TextView) findViewById(R.id.together);
        TextView courseDescription = (TextView) findViewById(R.id.description);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("course_title");
        String together = extras.getString("course_together");
        String description = extras.getString("course_description");

        courseTitle.setText(title);
        courseTogether.setText(together);
        courseDescription.setText(description);

    }
}
