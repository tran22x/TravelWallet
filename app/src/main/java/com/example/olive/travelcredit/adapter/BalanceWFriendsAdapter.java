package com.example.olive.travelcredit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olive.travelcredit.R;
import com.example.olive.travelcredit.data.Record;

import java.util.List;

/**
 * Created by minhntran on 5/15/18.
 */

public class BalanceWFriendsAdapter extends RecyclerView.Adapter<BalanceWFriendsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFriend;
        public TextView tvBalance;


        public ViewHolder(View itemView) {
            super(itemView);
            tvFriend = (TextView) itemView.findViewById(R.id.tvFriend);
            tvBalance = (TextView) itemView.findViewById(R.id.tvBalance);
        }
    }

    private List<Record> records;
    private Context context;
    private int lastPosition = -1;

    public BalanceWFriendsAdapter(List<Record> records, Context context) {
        this.records = records;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_balance, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.tvFriend.setText(records.get(viewHolder.getAdapterPosition()).getName());
        viewHolder.tvBalance.setText(records.get(viewHolder.getAdapterPosition()).getAmount() + "$");

    }

    @Override
    public int getItemCount() {return records.size();}



}
