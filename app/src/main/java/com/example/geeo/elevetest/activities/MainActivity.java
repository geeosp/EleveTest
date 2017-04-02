package com.example.geeo.elevetest.activities;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.geeo.elevetest.models.Comment;
import com.example.geeo.elevetest.DBManager;
import com.example.geeo.elevetest.R;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;


public class MainActivity extends AppCompatActivity {


    ProgressBar progressBar;
    EditText et_postId;
    private RecyclerView rvComments;
    private CommentAdapter commentsAdaptar;
    private RecyclerView.LayoutManager commentsRvLayout;
    List<Comment> comments;


    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comments = DBManager.getInstance(this).loadComments();
        et_postId = (EditText) findViewById(R.id.et_id);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rvComments = (RecyclerView) findViewById(R.id.rv_comments);
        commentsAdaptar = new CommentAdapter();
        rvComments.setAdapter(commentsAdaptar)
        ;
        commentsRvLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvComments.setLayoutManager(commentsRvLayout);


        Log.d("eleve", "" + comments.size());
    }


    public void requestPost(View v) {
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final String id = et_postId.getText().toString();
        params.put("postId", id);
        String server = getResources().getString(R.string.main_server);
        progressBar.setIndeterminate(true);
/*
        client.get(server, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("ELEVE", response.toString());
                progressBar.setIndeterminate(false);
                comments = new ArrayList<Comment>();
                for (int c = 0; c < response.length(); c++) {
                    try {
                        comments.add(new Comment((JSONObject) response.get(c)));
                    } catch (JSONException jsone) {
                        jsone.printStackTrace();
                    }

                }
                DBManager db = DBManager.getInstance(MainActivity.this);
                db.deleteAll();
                db.saveComments(comments);
                updatViews();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();
                progressBar.setIndeterminate(false);
                //show dialog to user
                final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(MainActivity.this)
                        .setTextTitle(getResources().getString(R.string.oops))
                        .setTextSubTitle(getResources().getString(R.string.something_went_wrong))
                        .setBody(getResources().getString(R.string.something_went_wrong_hint))
                        .setNegativeButtonText(getResources().getString(R.string.ok))
                        .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                                ;
                            }
                        })
                        .build();
                alert.show();


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressBar.setIndeterminate(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                progressBar.setIndeterminate(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressBar.setIndeterminate(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    progressBar.setIndeterminate(false);
            }
        });
*/
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("postId", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, server, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Eleve", response);
                try {
                    final JSONArray jsonArray = new JSONArray(response);

                    progressBar.setIndeterminate(false);
                    comments = new ArrayList<Comment>();
                    for (int c = 0; c < response.length(); c++) {

                        comments.add(new Comment((JSONObject) jsonArray.get(c)));


                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                }
                DBManager db = DBManager.getInstance(MainActivity.this);
                db.deleteAll();
                db.saveComments(comments);
                updatViews();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setIndeterminate(false);
                //show dialog to user
                final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(MainActivity.this)
                        .setTextTitle(getResources().getString(R.string.oops))
                        .setTextSubTitle(getResources().getString(R.string.something_went_wrong))
                        .setBody(getResources().getString(R.string.something_went_wrong_hint))
                        .setNegativeButtonText(getResources().getString(R.string.ok))
                        .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                                ;
                            }
                        })
                        .build();
                alert.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("postId", id);
                return params;


            }
        };

        getRequestQueue().add(jsObjRequest);

    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    void updatViews() {
        commentsAdaptar = new CommentAdapter();
        rvComments.setAdapter(commentsAdaptar);
    }

    class CommentListHolder extends RecyclerView.ViewHolder {
        TextView tv_comment_name;
        TextView tv_comment_email;
        View view;

        public CommentListHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.tv_comment_name = (TextView) itemView.findViewById(R.id.tv_comment_name);
            this.tv_comment_email = (TextView) itemView.findViewById(R.id.tv_comment_email);
        }
    }


    class CommentAdapter extends RecyclerView.Adapter<CommentListHolder> {


        @Override
        public CommentListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.comment_list_item, parent, false);
            CommentListHolder holder = new CommentListHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(CommentListHolder holder, final int position) {
            final Comment comment = comments.get(position);
            holder.tv_comment_name.setText(comment.getName());
            holder.tv_comment_email.setText(comment.getEmail());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                    intent.putExtra("id", comment.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }
    }
}
