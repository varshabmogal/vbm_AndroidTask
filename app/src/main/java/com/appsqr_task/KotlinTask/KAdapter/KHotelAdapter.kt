package com.appsqr_task.KotlinTask.KAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appsqr_task.KotlinTask.KPojo.KHotelBean
import com.appsqr_task.R
import com.appsqr_task.pojo.HotelBean
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class KHotelAdapter(
    private val context: Context,
    private val hotelList: List<KHotelBean>
) : RecyclerView.Adapter<KHotelAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_hotel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = hotelList[position]
        holder.tvRowHotelName.text = model.hotel
        holder.tvRowHotelAddress.text = model.hotelAddress
        holder.tvRowHotelInDate.text = model.inDate
        holder.tvRowHotelOutDate.text = model.outTime
        holder.tvRowHotelPrice.text = model.hotelFees

        Glide.with(context)
            .load(model.img)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.ivRowHotelImg)
    }

    override fun getItemCount(): Int = hotelList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivRowHotelImg: ImageView = itemView.findViewById(R.id.ivRowHotelimg)
        val tvRowHotelName: TextView = itemView.findViewById(R.id.tvRowHotelName)
        val tvRowHotelAddress: TextView = itemView.findViewById(R.id.tvRowHoteladdress)
        val tvRowHotelMap: TextView = itemView.findViewById(R.id.tvRowHotelMap)
        val tvRowHotelInDate: TextView = itemView.findViewById(R.id.tvRowHotelInDate)
        val tvRowHotelOutDate: TextView = itemView.findViewById(R.id.tvRowHotelOutDate)
        val tvRowHotelPrice: TextView = itemView.findViewById(R.id.tvRowHotelPrice)
    }
}
