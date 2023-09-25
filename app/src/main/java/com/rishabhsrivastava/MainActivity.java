package com.rishabhsrivastava;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView mywebView;
    private AlertDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mywebView = findViewById(R.id.webview);

        // Check for internet connectivity
        if (isNetworkAvailable()) {
            setupWebView();
        } else {
            // No internet connection, display a custom dialog
            showNoInternetDialog();
        }
    }

    private void setupWebView() {
        WebSettings webSettings = mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebView.loadUrl("https://srivastavarishabh.in");

        mywebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Show ProgressBar when page starts loading
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Hide ProgressBar when page finishes loading
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });
    }

    // Check if the device is connected to the internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.no_internet_dialog, null);
        builder.setView(dialogView);
        noInternetDialog = builder.create();

        // Set click listener for the OK button
        Button okButton = dialogView.findViewById(R.id.dialog_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when the OK button is clicked
                noInternetDialog.dismiss();
                finish(); // Close the app or handle as needed
            }
        });

        // Show the dialog
        noInternetDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mywebView.canGoBack()) {
            mywebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
