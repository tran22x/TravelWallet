package com.example.olive.travelcredit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.olive.travelcredit.MainActivity;
import com.example.olive.travelcredit.R;
import com.example.olive.travelcredit.data.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olive on 5/11/18.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private Context context;
    private List<Trip> tripList;
    private List<String> tripKeys;
    private String uId;
    private int lastPosition = -1;
    public String tripName;


    public TripAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;
        this.tripList = new ArrayList<Trip>();
        this.tripKeys = new ArrayList<String>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_trip, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }







    public void addTrip(Trip newTrip, String key) {
        tripList.add(newTrip);
        tripKeys.add(key);
        notifyDataSetChanged();
    }


    public void removeTrip(int index) {
        FirebaseDatabase.getInstance().getReference("trips").child(
                tripKeys.get(index)).removeValue();

        tripList.remove(index);
        tripKeys.remove(index);
        notifyItemRemoved(index);
    }

    public void removeTripByKey(String key) {
        int index = tripKeys.indexOf(key);
        if (index != -1) {
            tripList.remove(index);
            tripKeys.remove(index);
            notifyItemRemoved(index);
        }
    }


    @Override
    public int getItemCount() {
        return tripList.size();
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvPlace.setText(
                tripList.get(holder.getAdapterPosition()).getPlaceName());
        //setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opens new screen with all the current transaction of that trip
                String tripName = tripList.get(holder.getAdapterPosition()).getPlaceName();
                ((MainActivity)context).startTransactionActivity(tripName);
            }
        });

        if (tripList.get(holder.getAdapterPosition()).getUid().equals(uId)) {
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeTrip(holder.getAdapterPosition());
                }
            });
        } else {
            holder.btnDelete.setVisibility(View.GONE);
        }

    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPlace;
        public ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

    }

}
