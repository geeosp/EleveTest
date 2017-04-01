package com.example.geeo.elevetest;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {


    ProgressBar progressBar ;
    EditText et_postId;
    private RecyclerView rvComments;
    private RecyclerView.Adapter commentsRvAdaptar;
    private RecyclerView.LayoutManager commentsRvLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_postId = (EditText) findViewById(R.id.et_id);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }


    public void requestPost(View v) {
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String id = et_postId.getText().toString();
        params.put("postId", 1);
        String server = getResources().getString(R.string.main_server);
        progressBar.setIndeterminate(true);
        client.get(server, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("ELEVE", response.toString());
                progressBar.setIndeterminate(false);
                ArrayList<Comment> comments = new ArrayList<Comment>();
                for(int c =0;c< response.length();c++){
                    try {
                        comments.add(new Comment((JSONObject) response.get(c)));
                    }catch (JSONException jsone){
                        jsone.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                 Log.d("ELEVE", throwable.toString());
                progressBar .setIndeterminate(false);
                //show dialog to user
                final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(MainActivity.this)
                        .setTextTitle(getResources().getString(R.string.oops))
                        .setTextSubTitle(getResources().getString(R.string.something_went_wrong))
                        .setBody(getResources().getString(R.string.something_went_wrong_hint))
                        .setNegativeButtonText(getResources().getString(R.string.ok))
                        .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
dialog.dismiss();;
                            }
                        })
                .build();
                alert.show();



            }
        });


    }


}
