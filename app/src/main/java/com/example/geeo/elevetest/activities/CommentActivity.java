package com.example.geeo.elevetest.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.geeo.elevetest.DBManager;
import com.example.geeo.elevetest.R;
import com.example.geeo.elevetest.models.Comment;

public class CommentActivity extends AppCompatActivity {

    TextView tv_id;
    TextView tv_post_id;
    TextView tv_name;
    TextView tv_email;
    TextView tv_body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_id = (TextView) findViewById(R.id.tv_comment_id);
        tv_post_id = (TextView) findViewById(R.id.tv_post_id);
        tv_name = (TextView) findViewById(R.id.tv_comment_name);
        tv_body = (TextView) findViewById(R.id.tv_comment_body);
        tv_email = (TextView) findViewById(R.id.tv_comment_email);

        long commentId = getIntent().getLongExtra("id", -1);
        Comment comment = DBManager.getInstance(this).getComment(commentId);
        if (comment != null) {
            tv_id.setText("Id: " + comment.getId());
            tv_post_id.setText("Post id: " + comment.getPostId());
            tv_body.setText(comment.getBody());
            tv_email.setText(comment.getEmail());
            tv_name.setText(comment.getName());
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Comment id: " + commentId);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
