package com.example.manoj.votingsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity  {
    Button vote;
    EditText aadhar;
    RadioButton aiadmk,dmk,bjp,congress,radioButton;
    private static final String URL = "http://10.42.0.1/CIT_COS/web/production/index3.php";
    String Aadhar,parties;
    long Aadhar_no;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vote=(Button)findViewById(R.id.vote);
        aadhar=(EditText)findViewById(R.id.aadhar);
        aiadmk=(RadioButton)findViewById(R.id.aiadmk);
        dmk=(RadioButton)findViewById(R.id.dmk);
        bjp=(RadioButton)findViewById(R.id.bjp);
        congress=(RadioButton)findViewById(R.id.congress);
        final RadioGroup radioGroup=findViewById(R.id.RGroup);

        //Validation
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aadhar = aadhar.getText().toString();
                if (!Aadhar.equals(""))
                    Aadhar_no = Long.parseLong(Aadhar);
                    int len =  Aadhar.length();
                int parties_id = radioGroup.getCheckedRadioButtonId();

                if(!Aadhar.equals("")){

                    if (!(parties_id==-1)){
                        radioButton = findViewById(parties_id);
                        parties = radioButton.getText().toString();
                        vote();
                    }else Toast.makeText(MainActivity.this, "Please Select your prefered Candidate", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(MainActivity.this, "Please Enter Your Valid Reg.No", Toast.LENGTH_SHORT).show();
            }
        });

    }
    void vote(){
        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "POLYS", "Loading", true);
        progressDialog.setCancelable(false);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
//
                            JSONObject obj = new JSONObject(response);

                            final String res = obj.getString("status");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(res.equals("false")){
                                        Toast.makeText(MainActivity.this, "Already Voted", Toast.LENGTH_SHORT).show();
                                    }else if(res.equals("true")){
                                        Toast.makeText(MainActivity.this, "Successfully Voted", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(MainActivity.this, Home.class);
//                                        startActivity(intent);
                                    }

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // error
                        Log.d("ErrorResponse", "Error");
                        Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("aadhar", Aadhar);
                params.put("parties", parties);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
}