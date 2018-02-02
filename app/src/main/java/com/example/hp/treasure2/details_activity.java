package com.example.hp.treasure2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;

public class details_activity extends AppCompatActivity {

    private TextView cid;
    private  TextView lname;
    private TextView cdate;
    private TextView edate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_activity);
        cid = (TextView) findViewById(R.id.editText);
        lname = (TextView) findViewById(R.id.editText2);
        cdate=(TextView)findViewById(R.id.editText3);
        edate=(TextView)findViewById(R.id.editText4);
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        String name = bundle.getString("name");
        cid.setText("pizza_"+data);
        lname.setText(name);

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        cdate.setText(mydate);
    }
}
