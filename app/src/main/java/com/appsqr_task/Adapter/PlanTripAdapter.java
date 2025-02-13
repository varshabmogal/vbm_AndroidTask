package com.appsqr_task.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.appsqr_task.R;

public class PlanTripAdapter extends RecyclerView.Adapter<PlanTripAdapter.ViewHolder> {

    Context context;

    @NonNull
    @Override
    public PlanTripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new PlanTripAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_planed_trip,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull PlanTripAdapter.ViewHolder holder, int position)
    {

    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      ImageView ivRowtripImage;
      TextView tvRowtripTitle,tvRowtripDate,tvRowtripDuration,tvRowLocation;
      AppCompatButton btnRowViewTrip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRowtripImage=itemView.findViewById(R.id.ivRowtripImage);
            tvRowtripTitle=itemView.findViewById(R.id.tvRowtripTitle);
            tvRowtripDate=itemView.findViewById(R.id.tvRowtripDate);
            btnRowViewTrip=itemView.findViewById(R.id.btnRowViewTrip);
            tvRowtripDuration=itemView.findViewById(R.id.tvRowtripDuration);
            tvRowLocation=itemView.findViewById(R.id.tvRowLocation);
        }
    }
}
