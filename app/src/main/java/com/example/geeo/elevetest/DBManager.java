package com.example.geeo.elevetest;

import android.content.Context;

import com.example.geeo.elevetest.models.Comment;

import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by geeo on 01/04/17.
 */

public class DBManager {
    private static Context ctx;


    private  static DBManager instance;


    public static DBManager getInstance(Context context) {
        //super(context, TABLE_NAME, null, 0);
        if (instance == null) {
            instance = new DBManager();
            Realm.init(context);

        }
        ctx = context;
        return instance;


    }


    public void saveComments(List<Comment> comments) {
        Iterator<Comment> it = comments.iterator();
        Realm.getDefaultInstance().beginTransaction();
        while (it.hasNext()) {
            Comment c = it.next();
            instance.saveCommentWithoutTransaction(c);

        }
        Realm.getDefaultInstance().commitTransaction();

    }

    public void deleteComment(long CommentId) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Comment> result = realm.where(Comment.class).equalTo("id", CommentId).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void deleteAll() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Comment> result = realm.where(Comment.class).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }


    public List<Comment> loadComments() {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Comment> Comments = realm.where(Comment.class).findAll();
        return Comments;
    }


    public Comment getComment(long id) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Comment> Comment = realm.where(Comment.class).equalTo("id", id).findAll();
        return realm.copyFromRealm(Comment.first());

    }


    public void saveComment(Comment comment) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        saveCommentWithoutTransaction(comment);
        realm.commitTransaction();
    }
     void saveCommentWithoutTransaction(Comment Comment) {
        Realm realm = Realm.getDefaultInstance();
       // realm.beginTransaction();
        realm.insertOrUpdate(Comment);
        //realm.commitTransaction();
    }



}
