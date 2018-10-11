package com.example.olive.travelcredit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olive.travelcredit.R;
import com.example.olive.travelcredit.TransactionsActivity;
import com.example.olive.travelcredit.data.Transaction;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by olive on 5/10/18.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>   {

    private Context context;
    private List<Transaction> transactionList;

    private List<String> transKeys;
    private HashMap<String, Integer> moneyRecord = new HashMap<>();
    public int currentBalance;
    private String uId;
    private int lastPosition = -1;
    private String tripName;

    public TransactionAdapter(Context context, String uId, String tripName) {
        this.context = context;
        this.uId = uId;
        this.transactionList = new ArrayList<Transaction>();
        this.transKeys = new ArrayList<String>();
        this.tripName = tripName;
    }





    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_transaction, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void addTransaction(Transaction newTransaction, String key) {
        transactionList.add(newTransaction);
        transKeys.add(key);
        notifyDataSetChanged();
    }


    public void removeTrans(int index ) {

        FirebaseDatabase.getInstance().getReference("trips").child(tripName).child("transactions").child(
                transKeys.get(index)).removeValue();

        transactionList.remove(index);
        transKeys.remove(index);
        notifyItemRemoved(index);

    }

    public void removeTransByKey(String key) {
        int index = transKeys.indexOf(key);
        if (index != -1) {
            transactionList.remove(index);
            transKeys.remove(index);
            notifyItemRemoved(index);
        }
    }


    // @OVERRIDE
    public int getTransCount() {
        return transactionList.size();
    }

    public void removeTransaction(int index) {
        FirebaseDatabase.getInstance().getReference("trips").child(tripName).child("transactions").child(
                transKeys.get(index)).removeValue();

        Transaction deletedItem = transactionList.get(index);
        transactionList.remove(index);
        transKeys.remove(index);

        if (deletedItem != null) {
            ((TransactionsActivity) context).updateRecord(deletedItem);
        }
        notifyItemRemoved(index);

    }





    //TODO Recolor the text//background so to distinguish between related transactions and non-related transactions
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvName.setText(
                transactionList.get(holder.getAdapterPosition()).getName());
        holder.tvFrom.setText("Requester: " + transactionList.get(holder.getAdapterPosition()).getSenderId());
        holder.tvTo.setText("Payer: " + transactionList.get(holder.getAdapterPosition()).getReceiverId());
        holder.tvAmount.setText("" + transactionList.get(holder.getAdapterPosition()).getAmount() + " $");
        holder.tvDetail.setText(transactionList.get(holder.getAdapterPosition()).getDetail());
        if (transactionList.get(holder.getAdapterPosition()).getAmount() < 0) {
            holder.icon.setImageResource(R.drawable.outcome);
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        else if (transactionList.get(holder.getAdapterPosition()).getAmount() > 0) {
            holder.icon.setImageResource(R.drawable.income);
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.tealMain));
        }

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTransaction(position);
            }
        });
            //setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvFrom;
        public TextView tvTo;
        public TextView tvAmount;
        public TextView tvDetail;
        public ImageView icon;
        public ImageButton btnDel;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvTo = itemView.findViewById(R.id.tvTo);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            icon = itemView.findViewById(R.id.transactionIcon);
            btnDel = itemView.findViewById(R.id.btnDel);
        }
    }
}
