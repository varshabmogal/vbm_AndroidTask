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
import com.appsqr_task.pojo.HotelBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    Context context;
    private List<HotelBean> hotelList;
    public ActivityAdapter(Context context, List<HotelBean> hotelList)
    {
        this.context = context;
        this.hotelList=hotelList;
    }
    @NonNull
    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ActivityAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_activity,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ActivityAdapter.ViewHolder holder, int position)
    {
        final HotelBean model = hotelList.get(position);
        holder.tvRowActivity.setText(model.getActivity());
        holder.tvRowActivityAddress.setText(model.getActivty_address());
        holder.tvRowActivityDateTime.setText(model.getTime()+"on "+model.getIndate());
        holder.tvRowActivityPrice.setText(""+model.getActvity_price());

        Glide.with(context).load(model.getImg())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.ivRowActivityImg);


    }
    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRowActivityImg;
        TextView tvRowActivityDateTime,tvRowActivity,tvRowActivityAddress,
        tvRowActivityPrice,tvRowActivityMap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRowActivityImg=itemView.findViewById(R.id.ivRowActivityImg);
            tvRowActivityDateTime=itemView.findViewById(R.id.tvRowActivityDateTime);
            tvRowActivity=itemView.findViewById(R.id.tvRowActivity);
            tvRowActivityAddress=itemView.findViewById(R.id.tvRowActivityAddress);
            tvRowActivityPrice=itemView.findViewById(R.id.tvRowActivityPrice);
            tvRowActivityMap=itemView.findViewById(R.id.tvRowActivityMap);

        }
    }
}
