package com.example.denni.contactapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ItemHolder> {

    List<Contact> contacts;
    private Callback mCallback;

    public RecyclerContactAdapter(List<Contact> contacts, Callback callback) {
        this.contacts = contacts;
        mCallback = callback;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item,viewGroup,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int position) {
        final Contact contact = contacts.get(position);
        itemHolder.contactName.setText(contact.getContactName());
        itemHolder.contactPhone.setText(contact.getContactPhone());
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onItemClicked(contact);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        TextView contactName, contactPhone;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.contactName);
            contactPhone = itemView.findViewById(R.id.contactPhone);

        }
    }

    public interface Callback {
        void onItemClicked(Contact contact);
    }
}
