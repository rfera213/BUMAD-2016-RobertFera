package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageButton;
import android.content.res.TypedArray;

/**
 * Created by rfera213 on 12/6/16.
 */

public class NavBar extends RelativeLayout implements View.OnClickListener {

    private TextView textView;
    private ImageButton infoButton;
    private String mViewName;

//    public NavBar(Context context) {
//        super(context);
//        init();
//    }

    public NavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NavBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(getContext(), R.layout.navbar, this);
        this.textView = (TextView)findViewById(R.id.textView);
        this.infoButton = (ImageButton)findViewById(R.id.imageButton);
        this.infoButton.setOnClickListener(this);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NavBar, 0, 0);
        try {
            //get the text and colors specified using the names in attrs.xml
            mViewName = a.getString(R.styleable.NavBar_viewName);
        } finally {
            a.recycle();
        }

        this.textView.setText(mViewName);
    }

    @Override
    public void onClick(View view){
        ((MainActivity)getContext()).showInfoActivity();
    }
}
