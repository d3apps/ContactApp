package com.example.denni.contactapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Message> messages;
    private RecyclerView recyclerMsgs;
    private EditText editNewMsg;
    private Button sendBtn;
    private MessageAdapter messageAdapter;
    private String mPath = "msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messages = new ArrayList<>();
        recyclerMsgs = findViewById(R.id.recyclerMsgs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerMsgs.setLayoutManager(linearLayoutManager);

        editNewMsg = findViewById(R.id.editNewMsg);
        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this);

        if (getIntent()!=null){
            if (getIntent().getIntExtra("CHAT_TYPE", 0)== 1){
                readDb(mPath);
            } else if (getIntent().getIntExtra("CHAT_TYPE", 0) == 2){
                int key = getIntent().getIntExtra("CHAT_NAME", 0);
                mPath = String.valueOf(key);
                readDb(mPath);
            }
        }

    }

    private void readDb(String path) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    Message msg = dataSnapshot.getValue(Message.class);

                    if (messageAdapter == null) {
                        messageAdapter = new MessageAdapter();
                        recyclerMsgs.setAdapter(messageAdapter);
                    }
                    messageAdapter.addMessage(msg);
                    messages.add(msg);
                    recyclerMsgs.smoothScrollToPosition(messages.size() - 1);

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.sendBtn) {
            if (!String.valueOf(editNewMsg.getText()).isEmpty()) {
                String msg = String.valueOf(editNewMsg.getText());
                editNewMsg.setText("");
                Message message = new Message("Denis",
                        msg,
                        getSharedPreferences(
                                "MY_PREFS", MODE_PRIVATE).getString("KEY",""));
                sendToDb(message);
            }


        }
    }

    private void sendToDb(Message message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(mPath);
        reference.push().setValue(message);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,ProfileActivity.class));
        super.onBackPressed();
    }
}
