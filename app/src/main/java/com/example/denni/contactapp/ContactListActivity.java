package com.example.denni.contactapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactListActivity extends AppCompatActivity implements RecyclerContactAdapter.Callback {
    private RecyclerView recyclerView;
    private List<Contact> contacts;
    private RecyclerContactAdapter adapter;
    private SharedPreferences preferences;
    private Set<String> chatKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        contacts = new ArrayList<>();
        getContactsFromDb();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        RecyclerView.ItemDecoration decoration =
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        chatKeys = new HashSet<>();

    }

    private void getContactsFromDb() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    Contact contact = dataSnapshot.getValue(Contact.class);
                    if(!preferences.getString("KEY","").matches(contact.getKey())){
                        contacts.add(contact);
                        if (adapter == null){
                            adapter=new RecyclerContactAdapter(contacts,
                                    ContactListActivity.this);
                            recyclerView.setAdapter(adapter);
                        }
                        adapter.notifyDataSetChanged();
                        createHashData(contact.getKey());
                    }

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

    private void createHashData(String key) {
        String myKey = preferences.getString("KEY","");
        if (isNonNullAndNonEmpty(key)){
            String hash = String.valueOf(myKey.hashCode() + key.hashCode());
            chatKeys.add(hash);

            preferences.edit().putStringSet("CHAT_KEYS",chatKeys).apply();
        }
    }

    private boolean isNonNullAndNonEmpty(String string) {
        return string!= null & !string.isEmpty();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,ProfileActivity.class));
        super.onBackPressed();
    }

    @Override
    public void onItemClicked(Contact contact) {
        int chatName = preferences.getString("KEY", "").hashCode()
                + contact.getKey().hashCode();
        Intent intent = new Intent(this,ChatActivity.class);
        intent.putExtra("CHAT_NAME", chatName);
        intent.putExtra("CHAT_TYPE", 2);
        startActivity(intent);
    }
}
