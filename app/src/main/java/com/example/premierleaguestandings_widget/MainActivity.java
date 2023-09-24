package com.example.premierleaguestandings_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            // You have internet connectivity, proceed with making network requests
            Toast.makeText(this, "Yes internet connection", Toast.LENGTH_SHORT).show();
        } else {
            // You don't have internet connectivity, show a message to the user
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

    }
}