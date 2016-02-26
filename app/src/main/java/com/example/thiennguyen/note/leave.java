package com.example.thiennguyen.note;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.text.SimpleDateFormat;

import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class leave extends AppCompatActivity {
    ListView listview;
    ArrayList<Team> teams = new ArrayList<Team>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview = (ListView) findViewById(R.id.listView);

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        TextView textView = (TextView) findViewById(R.id.textView20);
        textView.setText("Today is: " + date);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask(new AsyncResult() {
                @Override
                public void onResult(JSONObject object) {
                    processJson(object);
                }
            }).execute("https://spreadsheets.google.com/feeds/list/1JhvuagO_9cC1szr3zRr4_dr2TyGLnxPEm0zhbmOCky8/od6/public/basic?alt=json");

        } else {
            new AlertDialog.Builder(this).setTitle("No Internet again?").setMessage("Please connect to internet!").setNeutralButton("Close", null).show();
        }
    }

    public void onDuty(View view){
        startActivity(new Intent(leave.this, MainActivity.class));
    }
    public void emerg(View view){
        startActivity(new Intent(leave.this, Emergency.class));
    }
    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("entry");

            for (int r = 8; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONObject title = (JSONObject) row.get("title");
                String name = title.getString("$t");
                if (name.equals("error")){
                    continue;
                }

                JSONObject content = (JSONObject) row.get("content");
                String wins = content.getString("$t").substring(6);
                int position = 0;
                int draws = 0;
                int losses = 0;
                int points = 0;
                Team team = new Team(position, name, wins, draws, losses, points);
                teams.add(team);
            }

            final TeamsAdapter adapter = new TeamsAdapter(this, R.layout.team, teams);
            listview.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
