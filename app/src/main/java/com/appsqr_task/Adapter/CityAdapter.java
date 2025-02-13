package com.appsqr_task.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.appsqr_task.Helper.OnPositionClickListener;
import com.appsqr_task.R;
import com.appsqr_task.pojo.PlanedTripBean;
import com.appsqr_task.pojo.SelectCityBean;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<SelectCityBean> cityList;
    Context context;
    OnPositionClickListener onPositionClickListener;
    public CityAdapter(Context context, List<SelectCityBean> cityList,OnPositionClickListener onPositionClickListener) {
        this.cityList=cityList;
        this.context = context;
        this.onPositionClickListener = onPositionClickListener;
    }
    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_city,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        holder.tvRowCity.setText(cityList.get(position).getCity());
        holder.tvRowCityAddress.setText(cityList.get(position).getCountry());

        holder.llRowMainCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPositionClickListener.onPositionClick(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       AppCompatTextView tvRowCityAddress,tvRowCity;
       LinearLayout llRowMainCity;
       AppCompatImageView ivRowCountryIMG;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRowCityAddress=itemView.findViewById(R.id.tvRowCityAddress);
            tvRowCity=itemView.findViewById(R.id.tvRowCity);
            ivRowCountryIMG=itemView.findViewById(R.id.ivRowCountryIMG);
            llRowMainCity=itemView.findViewById(R.id.llRowMainCity);
        }
    }
}
