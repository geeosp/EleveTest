package com.example.geeo.elevetest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by geeo on 01/04/17.
 */

public class Comment {


    int  id, postId;
    String name, email, body;

    public Comment(int id, int postId, String name, String email, String body) {
        this.id = id;
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Comment (JSONObject json){


        try {
            this.id = json.getInt("id");
        this.postId = json.getInt("postId");
        this.name = json.getString("name");
        this.email = json.getString("email");
        this.body = json.getString("body");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public JSONObject toJason(){
        JSONObject json = new JSONObject();

        try {
        json.put("postId", postId);
            json.put("id", id);
            json.put("name", name);
        json.put("email", email);
        json.put("body", body);

        } catch (JSONException e) {
            e.printStackTrace();
        }
return json;


    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
