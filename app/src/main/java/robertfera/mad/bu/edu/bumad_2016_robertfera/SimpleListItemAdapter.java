package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rfera213 on 12/12/16.
 */

public class SimpleListItemAdapter extends ArrayAdapter<String> implements ResultFilter {
    private Context context;
    private List<String> objects;
    private Boolean shouldCheck;

    public SimpleListItemAdapter(Context context, List<String> objects, Boolean shouldCheck) {
        super(context, 0, objects);
        this.objects = objects;
        this.context = context;
        this.shouldCheck = shouldCheck;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        TextView txt = (TextView) rowView.findViewById(android.R.id.text1);
        txt.setTextSize(20);
        txt.setText(objects.get(position));

        if (shouldCheck) {
            // user set major - need to check each article for keywords
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context);
            String major = sharedPref.getString("major", "");
            if (major.length() > 0) {
                Boolean isSpecial = this.isPreferred(major, txt.getText().toString());
                if (isSpecial) {
                    rowView.setBackgroundColor(Color.LTGRAY);
                }
            }
        }

        return rowView;
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
