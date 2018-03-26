package com.example.ankit.navigationdrawer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    EditText editTextPhone,editTextCode;
    Button sendotp,verify;
    LinearLayout verify_otp,login;
    TextInputEditText number , otp;
    int status=0;
    boolean ans = false;
    long pend;
    FirebaseAuth mAuth;

    String codeSent;


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
        mAuth = FirebaseAuth.getInstance();
        verify = findViewById(R.id.verify);
        editTextPhone = findViewById(R.id.mobile_number);
        editTextCode = findViewById(R.id.otp);
        verify_otp = findViewById(R.id.verif_otp);
        login = findViewById(R.id.login);
        number = findViewById(R.id.mobile_number);
        otp = findViewById(R.id.otp);
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //check_number();
                sendVerificationCode();
                verify_otp.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
//                if(ans==true)
//                {
//                    Toast.makeText(LoginActivity.this, "successfully found", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(LoginActivity.this, "NOT found", Toast.LENGTH_SHORT).show();
//                }

//                login.setVisibility(view.GONE);
//                verify_otp.setVisibility(View.VISIBLE);
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
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
    public  void check_number()
    {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Login");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {   pend = dataSnapshot.getChildrenCount();
                for(DataSnapshot credentials:dataSnapshot.getChildren())
                {
                    pend = pend -1;
                    if (status == 0) {
                        String Number = (String) credentials.child("Mobile_number").getValue();
                        if (Number.equals(number.getText().toString())) {
                            Toast.makeText(LoginActivity.this,"success beta",Toast.LENGTH_LONG).show();
<<<<<<< HEAD
                        }
                    } else if (status == 2) {
                        String Number = (String) credentials.child("F_Mobile_number").getValue();
                        if (Number.equals(number.getText().toString())) {
                            Toast.makeText(LoginActivity.this,"success papa",Toast.LENGTH_LONG).show();
                        }
=======
                        }
                    } else if (status == 2) {
                        String Number = (String) credentials.child("F_Mobile_number").getValue();
                        if (Number.equals(number.getText().toString())) {
                            Toast.makeText(LoginActivity.this,"success papa",Toast.LENGTH_LONG).show();
                        }
>>>>>>> 77676bc67b45356c0572e59dca2b6f92891302d3
                    }

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendVerificationCode(){

        String phone = editTextPhone.getText().toString();

        if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return;
        }

        if(phone.length() < 10 ){
            editTextPhone.setError("Please enter a valid phone");
            editTextPhone.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private void verifySignInCode(){
        String code = editTextCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //here you can open new activity
                            Toast.makeText(getApplicationContext(),
                                    "Login Successfull", Toast.LENGTH_LONG).show();
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent = s;
        }
    };


}
