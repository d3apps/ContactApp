package com.example.denni.contactapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GLOBAL_CHAT = 1;
    TextView nameText, emailText, phoneText;
    Button showContactsBtn, showGlobalChat;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);

        showContactsBtn = findViewById(R.id.showContactsBtn);
        showGlobalChat = findViewById(R.id.showGlobalChat);
        showContactsBtn.setOnClickListener(this);
        showGlobalChat.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        nameText.setText(String.valueOf(nameText.getText()
                + sharedPreferences.getString("NAME",null)));
        emailText.setText(String.valueOf(emailText.getText()
                + sharedPreferences.getString("EMAIL",null)));
        phoneText.setText(String.valueOf(phoneText.getText()
                + sharedPreferences.getString("PHONE",null)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuEdit: {
                sharedPreferences.edit().putBoolean("IS_LOGGED_IN", false).apply();
                goToFirstScreen();
            }break;
            case R.id.menuLogOut: {
                sharedPreferences.edit().clear().apply();
                goToFirstScreen();
            }break;
            default:{

            }
        }
        return true;
    }

    private void goToFirstScreen(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showContactsBtn:
                startActivity(new Intent(this, ContactListActivity.class));
                finish();
                break;
            case R.id.showGlobalChat:
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("CHAT_TYPE",GLOBAL_CHAT);
                startActivity(intent);
                finish();
                break;
        }

    }
}
