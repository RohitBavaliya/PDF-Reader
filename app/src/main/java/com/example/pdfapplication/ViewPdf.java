package com.example.pdfapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class ViewPdf extends AppCompatActivity {
    WebView webPdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        webPdfView = findViewById(R.id.webPdfView);
        webPdfView.getSettings().setJavaScriptEnabled(true);

        String fileName = getIntent().getStringExtra("filename");
        String fileurl = getIntent().getStringExtra("fileurl");

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(fileName);
        dialog.setMessage("Opening file...");

        webPdfView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
            }
        });
        String url = "";
        try {
            url = URLEncoder.encode(fileurl,"UTF-8");
        }
        catch (Exception e){

        }
        webPdfView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
    }
}