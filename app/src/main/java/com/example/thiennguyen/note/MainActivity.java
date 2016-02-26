package com.example.thiennguyen.note;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.text.util.Linkify;
import android.app.AlertDialog;


public class MainActivity extends ActionBarActivity {
    private static final String DEBUG_TAG = "HttpExample";
    ArrayList<Team> teams = new ArrayList<Team>();
    ListView listview;
    Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        // listview = (ListView) findViewById(R.id.listview);
        btnDownload = (Button) findViewById(R.id.btnDownload);
       */
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

    public void buttonClickHandler(View view) {
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/feeds/list/1JhvuagO_9cC1szr3zRr4_dr2TyGLnxPEm0zhbmOCky8/od6/public/basic?alt=json");

    }

    private void processJson(JSONObject object) {

        try {


            JSONArray rows = object.getJSONArray("entry");


            //RA1
            JSONObject row = rows.getJSONObject(0);
            JSONObject title = (JSONObject) row.get("title");
            String name = title.getString("$t");


            TextView textView = (TextView) findViewById(R.id.RA1);

            textView.setText(name.toString());

            JSONObject content = (JSONObject) row.get("content");
            TextView phoneView = (TextView) findViewById(R.id.phone1);

            String phone = content.getString("$t");


            phoneView.setText(phone.substring(6,11)+"-" + phone.substring(11));
            Linkify.addLinks(phoneView, Linkify.PHONE_NUMBERS);
            phoneView.setLinksClickable(true);



            //  phoneView.setText(phone.substring(6, 11) + "-" + phone.substring(11));



            //RA2
            JSONObject row1 = rows.getJSONObject(1);
            JSONObject title1 = (JSONObject) row1.get("title");
            String name1 = title1.getString("$t");

            TextView textView1 = (TextView) findViewById(R.id.RA2);

                    textView1.setText(name1.toString());

            JSONObject content1 = (JSONObject) row1.get("content");
            TextView phoneView1 = (TextView) findViewById(R.id.phone2);

            String phone1 = content1.getString("$t");
            phoneView1.setText(phone1.substring(6,11)+"-" + phone1.substring(11));


            //RA3
            JSONObject row2 = rows.getJSONObject(2);
            JSONObject title2 = (JSONObject) row2.get("title");
            String name2 = title2.getString("$t");

            TextView textView2 = (TextView) findViewById(R.id.RA3);

            textView2.setText(name2.toString());

            JSONObject content2 = (JSONObject) row2.get("content");
            TextView phoneView2 = (TextView) findViewById(R.id.phone3);

            String phone2 = content2.getString("$t");
            phoneView2.setText(phone2.substring(6,11)+"-" + phone2.substring(11));




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}




