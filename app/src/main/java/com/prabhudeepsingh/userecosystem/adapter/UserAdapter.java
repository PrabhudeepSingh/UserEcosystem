package com.prabhudeepsingh.userecosystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.prabhudeepsingh.userecosystem.R;
import com.prabhudeepsingh.userecosystem.holder.UserHolder;
import com.prabhudeepsingh.userecosystem.listener.OnRecyclerItemCLickListener;
import com.prabhudeepsingh.userecosystem.listener.RecyclerAdapterClickListener;
import com.prabhudeepsingh.userecosystem.model.User;

/**
 * Created by prabhudeepsingh on 23/03/18.
 */

public class UserAdapter extends FirestoreRecyclerAdapter<User, UserHolder> implements OnRecyclerItemCLickListener{

    RecyclerAdapterClickListener recyclerAdapterClickListener = null;

    public void setRecyclerAdapterClickListener(RecyclerAdapterClickListener recyclerAdapterClickListener) {
        this.recyclerAdapterClickListener = recyclerAdapterClickListener;
    }

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserAdapter(FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(UserHolder holder, final int position, User model) {
        holder.textViewName.setText(model.name);
        holder.textViewGender.setText(model.gender);
        holder.textViewEmail.setText(model.email);
        holder.textViewContact.setText(model.contact);

        holder.setRecyclerItemCLickListener(this);
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        UserHolder userHolder = new UserHolder(view);
        return userHolder;
    }

    @Override
    public void OnRecyclerItemClicked() {
        recyclerAdapterClickListener.onRecyclerAdapterClickListener(100);
    }
}