package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.app.Activity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.util.MalformedJsonException;
import android.webkit.WebView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class BUTodayArticleDetail extends Activity {
    private WebView webview;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butoday_article_detail);

        this.webview = (WebView)findViewById(R.id.webview);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(BUTodayArticleDetail.this, "WebView Example", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " +url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(BUTodayArticleDetail.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });


        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        webview.loadUrl(url);
    }
}
