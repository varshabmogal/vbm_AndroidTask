package com.appsqr_task.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


import com.appsqr_task.R;
import com.appsqr_task.TripActivity;
import com.appsqr_task.pojo.PlanedTripBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder>{
    private List<PlanedTripBean> planedTripList;
    Context mCtx;
    public TripAdapter(Context mCtx, List<PlanedTripBean> planedTripList) {
        this.planedTripList=planedTripList;
        this.mCtx = mCtx;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_planed_trip,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PlanedTripBean hero = planedTripList.get(position);
        holder.tvRowLocation.setText(hero.getcity());

        Glide.with(mCtx).load(hero.getImg())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.ivRowtripImage);

        holder.tvRowtripTitle.setText(hero.getTitle());
        holder.tvRowtripDate.setText(hero.getdate());
        holder.tvRowtripDuration.setText(hero.getDuration());
        holder.btnRowViewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mCtx, TripActivity.class);
                intent.putExtra("city",hero.getcity());
                intent.putExtra("date",hero.getdate());
                intent.putExtra("img",hero.getImg());
                intent.putExtra("duration",hero.getDuration());
                intent.putExtra("title",hero.getTitle());
                mCtx.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return planedTripList.size();
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
