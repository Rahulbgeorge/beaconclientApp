package com.example.rahulgeorge.beaconclientapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Advertisement extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ImageButton im3;
    private ImageButton im2;
    DatabaseReference myRef;
    private ImageView im;
    private TextView tv;
    private Map<String, Object> td;
    private String Likes;
    private String DisLikes;
   private String Visited;
   private Integer b=1;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        Bundle extras = getIntent().getExtras();
        String imageurl = extras.getString("imageurl");
        String description = extras.getString("description");
        Likes=extras.getString("Like");
        DisLikes=extras.getString("disLike");
        Visited=extras.getString("Visited");
        String blufid=extras.getString("Blufid");
        // String
       // Log.i("hello",name+lname);
        myRef=database.getReference("projects").child(blufid);
        im=findViewById(R.id.projectphoto);
        Picasso.with(Advertisement.this).load(imageurl).into(im);
        tv=findViewById(R.id.LikesValue);
        tv.setText(Likes);
        tv=findViewById(R.id.projectdesc);
        tv.setText(description);
        tv=findViewById(R.id.DisLikesValues);
        tv.setText(DisLikes);
        td= new HashMap<String, Object>();



       im2=findViewById(R.id.likeimg);
       im2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Integer a=parseInt(Likes);
               a=a+1;
               td.put("Like",a.toString());
               td.put("Visited",b.toString());
               myRef.updateChildren(td);
            // Intent intent =new Intent(Advertisement.this,MainActivity.class);
             //startActivity(intent);
               finish();

           }
       });
       im3=findViewById(R.id.dislikeimg);
       im3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Integer a=parseInt(DisLikes);
               a=a+1;
               td.put("Dislike",a.toString());
               td.put("Visited",b.toString());
               myRef.updateChildren(td);
               finish();

               }
       });


    }

}
