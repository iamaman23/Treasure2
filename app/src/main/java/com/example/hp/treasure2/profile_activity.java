package com.example.hp.treasure2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile_activity extends AppCompatActivity{

    private FirebaseAuth firebaseAuth;
    private EditText firstname;
    private EditText lastname;
    //private Button gameplay;
    //    private Firebase f;
    //private  Button getdetails;
    //private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);
        firebaseAuth = FirebaseAuth.getInstance();
       // gameplay = (Button) findViewById(R.id.gameplay);
        //  f=new Firebase("https://treasurehunt-457a1.firebaseio.com/");
      //  logout = (Button) findViewById(R.id.logout);

        //gameplay.setOnClickListener(this);
        //logout.setOnClickListener(this);
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();//know current user logged in as we have to log him out


    }


    public void butlogout(View v)
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
    public void butgameplay(View v)
    {
        finish();
        startActivity(new Intent(this,gameplay_activity.class));

    }
    public void butgetdetails(View v)
    {
        //finish();
        //startActivity(new Intent(this,gameplay_activity.class));

        Intent i=new Intent(this,Main2Activity.class);
        startActivity(i);
    }
}
