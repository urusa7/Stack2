package com.example.urusa7.stack2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
EditText mEmail, mPassword;
    TextView mforgotpassword;
    Button mLogin, mRegister, MGooglelogin;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    String Email,Password;
    public static final String TAG = "Login";
    ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = (EditText) findViewById(R.id.login_emailId);
        mPassword = (EditText) findViewById(R.id.login_password);
        mforgotpassword = (TextView) findViewById(R.id.forgotPassword);
        mLogin = (Button) findViewById(R.id.login_here);
        mRegister = (Button) findViewById(R.id.register_here);
        MGooglelogin = (Button) findViewById(R.id.google_login);
        mDialog = new ProgressDialog(this);
         mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser!=null) {
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Log.d(TAG, "AuthStateChange:LogOut");
                }
            }
        };
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        MGooglelogin.setOnClickListener(this);
        mforgotpassword.setOnClickListener(this);
   }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mAuthListner!=null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v==mLogin){
            userSign();
        }
        else if (v==mRegister){
            startActivity(new Intent(Login.this,Register.class));
        }
    }

    //test
    private void userSign() {
        Email = mEmail.getText().toString().trim();
        Password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(Password)){
            Toast.makeText(Login.this,"Enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setMessage("Login please wait...");
        mDialog.setIndeterminate(true);
        mDialog.show();
        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    mDialog.dismiss();
                    Toast.makeText(Login.this, "Login not successful", Toast.LENGTH_LONG).show();
                } else {
                    mDialog.dismiss();
                    startActivity(new Intent(Login.this, Home.class));
                }
            }
        });

    }

}
