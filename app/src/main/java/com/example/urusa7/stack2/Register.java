package com.example.urusa7.stack2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity implements View.OnClickListener {
EditText name,email,password;
    Button mRegisterbtn;
    TextView mLoginPageBack;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    String Name,Email,Password;
    ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText)findViewById(R.id.register_name);
        email = (EditText)findViewById(R.id.register_email);
        password = (EditText)findViewById(R.id.register_password);
        mRegisterbtn = (Button)findViewById(R.id.register_btn);
        mLoginPageBack = (TextView)findViewById(R.id.loginBackbtn);
        //for auth
        mAuth = FirebaseAuth.getInstance();
        mRegisterbtn.setOnClickListener(this);
        mLoginPageBack.setOnClickListener(this);
        mDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick (View v){
        if (v==mRegisterbtn){
            UserRegister();
        }else if (v== mLoginPageBack) {
            startActivity(new Intent(Register.this,Login.class));
        }
    }

    private void UserRegister() {
       Name = name.getText().toString().trim();
       Email = email.getText().toString().trim();
       Password = password.getText().toString().trim();

    if (TextUtils.isEmpty(Name)){
        Toast.makeText(Register.this, "Enter Name", Toast.LENGTH_SHORT).show();
        return;
    }else if (TextUtils.isEmpty(Email)){
        Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
        return;
    }else if (TextUtils.isEmpty(Password)){
        Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
        return;
    }else if (Password.length()<6){
        Toast.makeText(Register.this, "Passwrd must be greater than 6 digit", Toast.LENGTH_SHORT).show();
        return;
    }
    mDialog.setMessage("Creating User please wait...");
    mDialog.setCanceledOnTouchOutside(false);
    mDialog.show();
    mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                mDialog.dismiss();
            }else{
                Toast.makeText(Register.this,"error on creating user",Toast.LENGTH_SHORT).show();
            }
        }

        });
    }
}
