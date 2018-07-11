package com.prabhudeepsingh.userecosystem.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.prabhudeepsingh.userecosystem.R;
import com.prabhudeepsingh.userecosystem.listener.OnRecyclerItemCLickListener;

/**
 * Created by prabhudeepsingh on 23/03/18.
 */

public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    OnRecyclerItemCLickListener recyclerItemCLickListener = null;

    public void setRecyclerItemCLickListener(OnRecyclerItemCLickListener recyclerItemCLickListener) {
        this.recyclerItemCLickListener = recyclerItemCLickListener;
    }

    public TextView textViewName;
    public TextView textViewGender;
    public TextView textViewEmail;
    public TextView textViewContact;

    public UserHolder(View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.textViewName);
        textViewGender = itemView.findViewById(R.id.textViewGender);
        textViewEmail = itemView.findViewById(R.id.textViewEmail);
        textViewContact = itemView.findViewById(R.id.textViewContact);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recyclerItemCLickListener.OnRecyclerItemClicked();
    }
}
