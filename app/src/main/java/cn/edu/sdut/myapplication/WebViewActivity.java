package cn.edu.sdut.myapplication;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView=findViewById(R.id.web_view);
        progressBar=findViewById(R.id.progress_horizontal);

        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        progressBar.setMax(100);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress<100){
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.loadUrl("https://www.sdut.edu.cn");
    }
}
