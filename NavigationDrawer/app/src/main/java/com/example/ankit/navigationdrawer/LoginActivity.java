package com.example.ankit.navigationdrawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Button sendotp,verify;
    LinearLayout verify_otp,login;
    int status=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //*********** Spinner *******
        spinner = (Spinner)findViewById(R.id.welcome_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        sendotp= findViewById(R.id.send_OTP);
        verify = findViewById(R.id.verify);
        verify_otp = findViewById(R.id.verif_otp);
        login = findViewById(R.id.login);
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                login.setVisibility(view.GONE);
                verify_otp.setVisibility(View.VISIBLE);
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //***************************
        switch(text) {
            case "Login As Student":{
                status = 0 ;
                break;
            }
            case "Login As Faculty":{

                status = 1 ;
                break;
            }
            case "Login As Parent":{

                status = 2 ;
                break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
