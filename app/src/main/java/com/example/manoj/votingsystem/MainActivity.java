package com.example.manoj.votingsystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends Activity  {
    Button vote;
    EditText name,aadhar;
    RadioButton aiadmk,dmk,bjp,congress;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vote=(Button)findViewById(R.id.vote);
        name=(EditText)findViewById(R.id.name);
        aadhar=(EditText)findViewById(R.id.aadhar);
        aiadmk=(RadioButton)findViewById(R.id.aiadmk);
        dmk=(RadioButton)findViewById(R.id.dmk);
        bjp=(RadioButton)findViewById(R.id.bjp);
        congress=(RadioButton)findViewById(R.id.congress);

        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }
}