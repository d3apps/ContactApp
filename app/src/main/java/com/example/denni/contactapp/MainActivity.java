package com.example.denni.contactapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.denni.contactapp.services.ChatService;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private TextInputLayout tilName, tilEmail, tilPhone;
    private EditText editName, editEmail, editPhone;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), ChatService.class);
        startService(intent);

        sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN",false);

        if (isLoggedIn){
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }

        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPhone = findViewById(R.id.tilPhone);


        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);

        if (sharedPreferences.getString("NAME",null)!=null){
            editName.setText(sharedPreferences.getString("NAME",null));
            editEmail.setText(sharedPreferences.getString("EMAIL",null));
            editPhone.setText(sharedPreferences.getString("PHONE",null));
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveBtn) {
            if (String.valueOf(editName.getText()).isEmpty()){
                tilName.setError("Enter data");
            }else if (String.valueOf(editEmail.getText()).isEmpty()){
                tilEmail.setError("Enter data");
            }else if (String.valueOf(editPhone.getText()).isEmpty()){
                tilPhone.setError("Enter data");
            }else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("NAME",String.valueOf(editName.getText()));
                editor.putString("EMAIL",String.valueOf(editEmail.getText()));
                editor.putString("PHONE",String.valueOf(editPhone.getText()));
                editor.putBoolean("IS_LOGGED_IN",true);
                editor.apply();

                setUserToDb();

                startActivity(new Intent(this,ProfileActivity.class));
                finish();
            }
        }
    }

    private void setUserToDb() {
        Contact myContact = new Contact("",
                String.valueOf(editName.getText()),
                String.valueOf(editEmail.getText()),
                String.valueOf(editPhone.getText()));
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.push().setValue(myContact, new DatabaseReference.CompletionListener(){

            @Override
            public void onComplete(@Nullable DatabaseError databaseError,
                                   @NonNull DatabaseReference databaseReference) {
                databaseReference.child("key").setValue(databaseReference.getKey());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("KEY",databaseReference.getKey());
                editor.apply();
            }
        });
    }
}
