package com.example.denni.contactapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ItemHolder> {

    List<Message> messages;
    public static final String MY_ID = "Denis";

    public MessageAdapter() {

        this.messages = new ArrayList<>();
    }

    public void addMessage(Message msg){
        messages.add(msg);
        notifyItemInserted(messages.size());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.chat_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {

        Message msg = messages.get(i);
        if (msg.getId().matches(MY_ID)){
            itemHolder.itemMyMsg.setVisibility(View.VISIBLE);
            itemHolder.itemIncomingMsg.setVisibility(View.GONE);
            itemHolder.itemMyMsg.setText(msg.getMsg());
        } else {
            itemHolder.itemIncomingMsg.setVisibility(View.VISIBLE);
            itemHolder.itemMyMsg.setVisibility(View.GONE);
            itemHolder.itemIncomingMsg.setText(msg.getMsg());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    class ItemHolder extends RecyclerView.ViewHolder{

        TextView itemIncomingMsg,itemMyMsg;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            itemIncomingMsg = itemView.findViewById(R.id.itemIncomingMsg);
            itemMyMsg = itemView.findViewById(R.id.itemMyMsg);
        }
    }
}
