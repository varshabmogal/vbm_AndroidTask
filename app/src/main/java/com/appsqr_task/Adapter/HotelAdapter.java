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

import com.appsqr_task.Helper.OnPositionClickListener;
import com.appsqr_task.R;
import com.appsqr_task.pojo.HotelBean;
import com.appsqr_task.pojo.PlanedTripBean;
import com.appsqr_task.pojo.SelectCityBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    Context context;
    private List<HotelBean> hotelList;
    public HotelAdapter(Context context, List<HotelBean> hotelList)
    {
        this.context = context;
        this.hotelList=hotelList;
    }
    @NonNull
    @Override
    public HotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new HotelAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_hotel,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.ViewHolder holder, int position)
    {
        final HotelBean model = hotelList.get(position);
        holder.tvRowHotelName.setText(model.getHotel());
        holder.tvRowHoteladdress.setText(model.getHotel_address());
        holder.tvRowHotelInDate.setText(model.getIndate());
        holder.tvRowHotelOutDate.setText(model.getOuttime());
        holder.tvRowHotelPrice.setText(""+model.getHotel_fees());

        Glide.with(context).load(model.getImg())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.ivRowHotelimg);


    }
    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRowHotelimg;
        TextView tvRowHotelMap,tvRowHotelName,tvRowHoteladdress,tvRowHotelInDate,
                tvRowHotelOutDate,tvRowHotelPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRowHotelimg=itemView.findViewById(R.id.ivRowHotelimg);
            tvRowHotelName=itemView.findViewById(R.id.tvRowHotelName);
            tvRowHoteladdress=itemView.findViewById(R.id.tvRowHoteladdress);
            tvRowHotelMap=itemView.findViewById(R.id.tvRowHotelMap);
            tvRowHotelInDate=itemView.findViewById(R.id.tvRowHotelInDate);
            tvRowHotelPrice=itemView.findViewById(R.id.tvRowHotelPrice);
            tvRowHotelOutDate=itemView.findViewById(R.id.tvRowHotelOutDate);
        }
    }
}
