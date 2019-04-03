package com.example.denni.contactapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    List <Contact> contacts;
    Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }
    public void fill(List<Contact> listContacts) {
        contacts = listContacts;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Contact contact = contacts.get(position);
        View view;
        if (convertView != null){
            view = convertView;
        } else {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.contact_item,viewGroup,false);
        }
        TextView contactName = view.findViewById(R.id.contactName);
        TextView contactPhone = view.findViewById(R.id.contactPhone);
        contactName.setText(String.valueOf(contact.getContactName()));
        contactPhone.setText(String.valueOf(contact.getContactPhone()));

        return view;
    }
}
