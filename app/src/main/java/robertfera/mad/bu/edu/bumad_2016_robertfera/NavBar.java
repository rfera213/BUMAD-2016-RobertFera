package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by rfera213 on 12/6/16.
 */

public class NavBar extends RelativeLayout {

    private TextView textView;
    private String mViewName;

    public NavBar(Context context) {
        super(context);
        init();
    }

    public NavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.navbar, this);
        this.textView = (TextView)findViewById(R.id.textView);
    }

    public String getmViewName() { return this.mViewName; }

    public void setViewName(String viewName) {
        mViewName = viewName;
        invalidate();
        requestLayout();
    }
}
