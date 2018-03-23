package com.example.ankit.navigationdrawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //*********** Spinner *******
        Spinner spinner = findViewById(R.id.welcome_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        btn = findViewById(R.id.button_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //***************************
        EditText student_id = (EditText) findViewById(R.id.student_id);
        student_id.setVisibility(View.GONE);
        EditText student_pass = (EditText) findViewById(R.id.student_pass);
        student_pass.setVisibility(View.GONE);
        EditText faculty_id = (EditText) findViewById(R.id.faculty_id);
        faculty_id.setVisibility(View.GONE);
        EditText faculty_pass = (EditText) findViewById(R.id.faculty_pass);
        faculty_pass.setVisibility(View.GONE);
        EditText parent_phone = (EditText) findViewById(R.id.parent_phone);
        parent_phone.setVisibility(View.GONE);
        EditText parent_otp = (EditText) findViewById(R.id.parent_otp);
        parent_otp.setVisibility(View.GONE);
        Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setVisibility(View.GONE);

        switch(text) {
            case "Login As Student":{

                student_id.setVisibility(View.VISIBLE);
                student_pass.setVisibility(View.VISIBLE);
                button_login.setVisibility(View.VISIBLE);
                break;
            }
            case "Login As Faculty":{

                faculty_id.setVisibility(View.VISIBLE);
                faculty_pass.setVisibility(View.VISIBLE);
                button_login.setVisibility(View.VISIBLE);
                break;
            }
            case "Login As Parent":{

                parent_phone.setVisibility(View.VISIBLE);
                parent_otp.setVisibility(View.VISIBLE);
                button_login.setVisibility(View.VISIBLE);
                break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
