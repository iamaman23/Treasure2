package com.example.hp.treasure2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rating extends AppCompatActivity {

    RatingBar ratingBar;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        addListenerOnButtonClick();
    }

    private void addListenerOnButtonClick() {

        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        button3=(Button)findViewById(R.id.button3);
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getApplicationContext(),"Thank You for rating us",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }
}
