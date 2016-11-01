package robertfera.mad.bu.edu.bumad_2016_robertfera;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.AsyncTask;
import android.graphics.drawable.Drawable;

/**
 * Created by rfera213 on 10/26/16.
 */

public class CoursesCollegeListAdapter extends ArrayAdapter<CoursesCollege> {

    private Context context;
    private ArrayList<CoursesCollege> objects;

    public CoursesCollegeListAdapter(Context context, int id, ArrayList<CoursesCollege> objects) {
        super(context, 0, objects);
        this.objects = objects;
        this.context = context;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.courses_college, null,true);

        TextView college = (TextView) rowView.findViewById(R.id.college);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

        CoursesCollege coursesCollege = objects.get(position);

        college.setText(coursesCollege.getName());

        String uri = "@drawable/college_" + coursesCollege.getCode().toLowerCase();
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        imageView.setImageDrawable(res);

        return rowView;
    };

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        private CoursesCollege coursesCollege;
        private ImageView imageView;

        public GetData(CoursesCollege coursesCollege, ImageView imageView) {
            super();
            this.coursesCollege = coursesCollege;
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            String uri = "@drawable/college_" + coursesCollege.getCode().toLowerCase();
            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable res = context.getResources().getDrawable(imageResource);
            this.imageView.setImageDrawable(res);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }
}
