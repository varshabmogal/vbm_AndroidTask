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

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {

    Context context;

    private List<HotelBean> hotelList;
    public FlightAdapter(Context context, List<HotelBean> hotelList)
    {
        this.context = context;
        this.hotelList=hotelList;
    }
    @NonNull
    @Override
    public FlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new FlightAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_flight,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull FlightAdapter.ViewHolder holder, int position)
    {
        final HotelBean model = hotelList.get(position);
        holder.tvFlightName.setText(model.getFlighttitle());
        holder.tvRowFStartTime.setText(model.getTm1());
        holder.tvRowFReachTime.setText(model.getTm2());
        holder.tvRowFArrivalTime.setText(model.getTime());
        holder.tvRowActivityPrice.setText(""+model.getHotel_fees());
        holder.tvRowFDate.setText(""+model.getIndate());
        holder.tvRowFReachDate.setText(""+model.getOuttime());

    }
    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFlightName,tvRowFStartTime,tvRowFArrivalTime,tvRowFReachTime,tvRowActivityPrice,
                tvRowFDate,tvRowFReachDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightName=itemView.findViewById(R.id.tvFlightName);
            tvRowFStartTime=itemView.findViewById(R.id.tvRowFStartTime);
            tvRowFArrivalTime=itemView.findViewById(R.id.tvRowFArrivalTime);
            tvRowFReachTime=itemView.findViewById(R.id.tvRowFReachTime);
            tvRowActivityPrice=itemView.findViewById(R.id.tvRowActivityPrice);
            tvRowFDate=itemView.findViewById(R.id.tvRowFDate);
            tvRowFReachDate=itemView.findViewById(R.id.tvRowFReachDate);
        }
    }
}
